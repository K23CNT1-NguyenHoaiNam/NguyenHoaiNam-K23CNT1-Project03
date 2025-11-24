package com.devmaster.lesson08.controller;
import com.devmaster.lesson08.entity.Author;
import com.devmaster.lesson08.entity.Book;
import com.devmaster.lesson08.service.AuthorService;
import com.devmaster.lesson08.service.BookService;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    private static final String UPLOAD_DIR =
            "src/main/resources/static/";
    private static final String
            UPLOAD_PathFile="images/products/";
    // Hiển thị toàn bộ sách
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/book-list";
    }
    // Thêm mới sách
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors",
                authorService.getAllAuthors());
        return "books/book-form";
    }
    @PostMapping("/new")
    public String saveBook(@ModelAttribute Book book
            , @RequestParam List<Long> authorIds
            , @RequestParam("imageBook") MultipartFile
                                   imageFile) {
        if(!imageFile.isEmpty()) {
            try {
// Tạo thư mục nếu chưa tồn tại
                Path uploadPath =
                        Paths.get(UPLOAD_DIR+UPLOAD_PathFile);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
// Lấy phần mở rộng của file ảnh
                String originalFilename =
                        StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension =
                        originalFilename.substring(originalFilename.lastIndexOf("."));
// Lưu file lên server
//String fileName =
                imageFile.getOriginalFilename();
//Path filePath = uploadPath.resolve(fileName);
// Tạo tên file mới + phần mở rộng gốc
                String newFileName = book.getCode() +
                        fileExtension;
                Path filePath = uploadPath.resolve(newFileName);
                Files.copy(imageFile.getInputStream(),
                        filePath);
                book.setImgUrl("/" + UPLOAD_PathFile +
                        newFileName);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Author> authors = new
                ArrayList<>(authorService.findAllById(authorIds));
        book.setAuthors(authors);
        bookService.saveBook(book);
        return "redirect:/books";
    }
    // Form sửa thông tin sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model
            model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors",
                authorService.getAllAuthors());
        return "books/book-form";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}