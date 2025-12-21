package com.project3.chelamthachxa.nhndto;

import lombok.Data;
import java.util.List;

@Data
public class NhnUserDTO {
    private Long id;
    private String username;
    private String email;
    private String hoten;
    private String sdt;
    private String diachi;
    private List<String> roleNames; // Chỉ cần tên vai trò
}
