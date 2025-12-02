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
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_PathFile = "images/products/";

    // ---------------------------
    // Hiển thị toàn bộ sách
    // ---------------------------
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/book-list";
    }

    // ---------------------------
    // Form thêm mới
    // ---------------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/book-form";
    }

    // ---------------------------
    // Lưu sách mới
    // ---------------------------
    @PostMapping("/new")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam List<Long> authorIds,
                           @RequestParam("imageBook") MultipartFile imageFile) {

        handleImageUpload(book, imageFile);

        List<Author> authors = new ArrayList<>(authorService.findAllById(authorIds));
        book.setAuthors(authors);

        bookService.saveBook(book);
        return "redirect:/books";
    }

    // ---------------------------
    // Form sửa sách
    // ---------------------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/book-form";
    }

    // ---------------------------
    // Lưu dữ liệu update
    // ---------------------------
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id,
                             @ModelAttribute Book book,
                             @RequestParam List<Long> authorIds,
                             @RequestParam("imageBook") MultipartFile imageFile) {

        Book oldBook = bookService.getBookById(id);

        if (!imageFile.isEmpty()) {
            handleImageUpload(book, imageFile);
        } else {
            book.setImgUrl(oldBook.getImgUrl());
        }

        book.setId(id);
        book.setAuthors(new ArrayList<>(authorService.findAllById(authorIds)));

        bookService.saveBook(book);
        return "redirect:/books";
    }

    // ---------------------------
    // Xóa sách
    // ---------------------------
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    // ---------------------------
    // Hàm xử lý upload ảnh (dùng chung)
    // ---------------------------
    private void handleImageUpload(Book book, MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PathFile);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = book.getCode() + fileExtension;

                Path filePath = uploadPath.resolve(newFileName);

                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                book.setImgUrl("/" + UPLOAD_PathFile + newFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
