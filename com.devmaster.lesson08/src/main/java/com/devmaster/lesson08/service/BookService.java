package com.devmaster.lesson08.service;
import com.devmaster.lesson08.entity.Book;
import com.devmaster.lesson08.repository.BookRepository;
import
        org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BookService {
    @Autowired
    private BookRepository BookRepository;
    public List<Book> getAllBooks() {
        return BookRepository.findAll();
    }
    public Book saveBook(Book book) {
        return BookRepository.save(book);
    }
    public Book getBookById(Long id) {
        return BookRepository.findById(id).orElse(null);
    }
    public void deleteBook(Long id) {
        BookRepository.deleteById(id);
    }
}