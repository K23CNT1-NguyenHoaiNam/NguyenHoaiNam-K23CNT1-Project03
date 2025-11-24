package com.devmaster.lesson08.controller;

import com.devmaster.lesson08.entity.Author;
import com.devmaster.lesson08.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_PATH = "images/authors/";

    /** ----------------------------
     * 1. Danh sách tác giả
     * ---------------------------- */
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors/author-list";
    }

    /** ----------------------------
     * 2. Form tạo tác giả mới
     * ---------------------------- */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/author-form";
    }

    /** ----------------------------
     * 3. Lưu tác giả (tạo mới + upload ảnh)
     * ---------------------------- */
    @PostMapping("/new")
    public String saveAuthor(
            @ModelAttribute Author author,
            @RequestParam("imageAuthor") MultipartFile imageFile
    ) {

        if (!imageFile.isEmpty()) {
            try {
                // Tạo thư mục nếu chưa có
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PATH);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Lấy phần mở rộng
                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                // Tạo tên file mới
                String newFileName = author.getCode() + fileExtension;

                Path filePath = uploadPath.resolve(newFileName);
                Files.copy(imageFile.getInputStream(), filePath);

                author.setImgUrl("/" + UPLOAD_PATH + newFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    /** ----------------------------
     * 4. Hiển thị form sửa
     * ---------------------------- */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);

        return "authors/author-form";
    }

    /** ----------------------------
     * 5. Xóa tác giả
     * ---------------------------- */
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
