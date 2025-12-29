package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhndto.NhnProduct;
import com.project3.chelamthachxa.nhndto.NhnProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NhnProductService {

    @Autowired
    private NhnProductRepository productRepository;

    /**
     * Retrieve a product by its ID and map it to a DTO.
     */
    public NhnProductDTO getProductById(Long id) {
        NhnProduct product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        NhnProductDTO dto = new NhnProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }
}
