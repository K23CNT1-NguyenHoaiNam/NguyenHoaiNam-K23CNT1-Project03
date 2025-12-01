package com.project3.chelamthachxa.service;

import com.project3.chelamthachxa.dto.UserUpdateRequest;
import com.project3.chelamthachxa.entity.User;
import com.project3.chelamthachxa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả người dùng
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Lấy người dùng theo ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // Cập nhật thông tin người dùng (Admin)
    public User updateUser(Long id, UserUpdateRequest updateRequest) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setHoten(updateRequest.getHoten());
                    user.setEmail(updateRequest.getEmail());
                    user.setDiachi(updateRequest.getDiachi());
                    user.setSdt(updateRequest.getSdt());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Người dùng với ID: " + id));
    }

    // Xóa người dùng (Admin)
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy Người dùng với ID: " + id);
        }
    }
}