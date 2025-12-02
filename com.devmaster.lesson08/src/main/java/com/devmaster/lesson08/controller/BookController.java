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

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/book-list";
    }

    @GetMapping("/new")
    public String showCreateBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/book-form";
    }

    @PostMapping("/new")
    public String saveBook(
            @ModelAttribute Book book,
            @RequestParam List<Long> authorIds,
            @RequestParam("imageBook") MultipartFile imageBook
    ) {
        if (!imageBook.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PathFile);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = StringUtils.cleanPath(imageBook.getOriginalFilename());
                String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = book.getCode() + fileExt;

                Path path = uploadPath.resolve(newFileName);
                Files.copy(imageBook.getInputStream(), path);

                book.setImgUrl("/" + UPLOAD_PathFile + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Author> authors = new ArrayList<>(authorService.findAllById(authorIds));
        book.setAuthors(authors);

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/book-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
