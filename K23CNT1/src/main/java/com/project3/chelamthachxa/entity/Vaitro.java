package com.project3.chelamthachxa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vaitro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaitro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên vai trò, ví dụ: ROLE_ADMIN, ROLE_USER
    @Column(nullable = false, unique = true)
    private String ten;
}