package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhnentity.NhnDonhang;
import com.project3.chelamthachxa.nhnrepository.NhnDonhangRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class NhnPaymentCronJob {

    private static final Logger logger = LoggerFactory.getLogger(NhnPaymentCronJob.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private NhnDonhangRepository nhnDonhangRepository;

    @Autowired
    private NhnDonhangService nhnDonhangService;

    // Chạy mỗi 30 giây để test cho nhanh
    @Scheduled(fixedRate = 10000)
    public void checkTransactions() {
        logger.info("==> [CronJob] Bắt đầu kiểm tra giao dịch...");

        List<NhnDonhang> pendingOrders = nhnDonhangRepository.findAll().stream()
                .filter(o -> o.getTrangThai() == NhnDonhang.TrangThaiDonHang.PENDING)
                .filter(o -> "TRANSFER".equals(o.getPhuongThucThanhToan()))
                .toList();

        if (pendingOrders.isEmpty()) {
            logger.info("==> [CronJob] Không có đơn hàng nào đang chờ thanh toán chuyển khoản.");
            return;
        }

        logger.info("==> [CronJob] Tìm thấy {} đơn hàng đang chờ thanh toán.", pendingOrders.size());

        try {
            String apiUrl = "https://nqtam.loca.lt/api/transactions?days=7";
            
            // Thêm header để bypass trang cảnh báo của localtunnel (loca.lt)
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("bypass-tunnel-reminder", "true");
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

            org.springframework.http.ResponseEntity<TransactionResponse> responseEntity = 
                restTemplate.exchange(apiUrl, org.springframework.http.HttpMethod.GET, entity, TransactionResponse.class);
            
            TransactionResponse response = responseEntity.getBody();

            if (response != null && response.isSuccess() && response.getTransactions() != null) {
                logger.info("==> [CronJob] Lấy được {} giao dịch từ API.", response.getTransactions().size());
                
                for (NhnDonhang order : pendingOrders) {
                    String matchPattern = "NHNPROJECT3" + order.getId();
                    logger.info("==> [CronJob] Đang tìm kiếm mã: {} cho đơn hàng ID: {}", matchPattern, order.getId());
                    
                    for (TransactionData tx : response.getTransactions()) {
                        String desc = tx.getDescription() != null ? tx.getDescription() : "";
                        String addDesc = tx.getAdd_description() != null ? tx.getAdd_description() : "";
                        
                        if (desc.contains(matchPattern) || addDesc.contains(matchPattern)) {
                            BigDecimal txAmount = new BigDecimal(tx.getAmount());
                            // Chế độ TEST: Chấp nhận cả khi số tiền là 0 (để bạn dễ test)
                            boolean ignoreAmountCheck = true; 
                            
                            if (ignoreAmountCheck || txAmount.compareTo(order.getTongTien()) >= 0) {
                                logger.info("==> [CronJob] XÁC NHẬN THANH TOÁN cho đơn hàng: {}", order.getId());
                                nhnDonhangService.updateOrderStatus(order.getId(), NhnDonhang.TrangThaiDonHang.PROCESSING);
                                break; 
                            } else {
                                logger.warn("==> [CronJob] Khớp mã nhưng SỐ TIỀN KHÔNG ĐỦ! (Cần: {}, Có: {})", order.getTongTien(), txAmount);
                            }
                        }
                    }
                }
            } else {
                logger.warn("==> [CronJob] API trả về kết quả không thành công hoặc không có dữ liệu.");
            }
        } catch (Exception e) {
            logger.error("==> [CronJob] LỖI khi gọi API: {}", e.getMessage());
        }
    }

    @Data
    public static class TransactionResponse {
        private boolean success;
        private List<TransactionData> transactions;
    }

    @Data
    public static class TransactionData {
        private String amount;
        private String description;
        private String add_description;
        private String type;
        private String reference_no;
        private String transaction_date;
    }
}
