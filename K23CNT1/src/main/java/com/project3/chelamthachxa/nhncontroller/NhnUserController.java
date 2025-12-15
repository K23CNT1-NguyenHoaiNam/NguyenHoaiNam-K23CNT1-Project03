package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhndto.NhnUserUpdateRequest;
import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnservice.NhnUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users") // Chỉ Admin mới được quản lý người dùng
public class NhnUserController {

    @Autowired
    private NhnUserService nhnUserService;

    // READ: Lấy tất cả người dùng
    @GetMapping
    public ResponseEntity<List<NhnUser>> getAllUsers() {
        List<NhnUser> nhnUsers = nhnUserService.findAllUsers();
        return ResponseEntity.ok(nhnUsers);
    }

    // READ: Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NhnUser> getUserById(@PathVariable Long id) {
        return nhnUserService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE: Cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<NhnUser> updateUser(@PathVariable Long id, @RequestBody NhnUserUpdateRequest updateRequest) {
        try {
            NhnUser updatedNhnUser = nhnUserService.updateUser(id, updateRequest);
            return ResponseEntity.ok(updatedNhnUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        try {
            nhnUserService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}