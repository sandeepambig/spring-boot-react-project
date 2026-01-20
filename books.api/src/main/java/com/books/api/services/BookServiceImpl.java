package com.books.api.services;

import com.books.api.dtos.BookDto;
import com.books.api.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto addBook(BookDto bookDto, MultipartFile file) {
        return null;
    }

    @Override
    public BookDto getBook(Long isbn) {
        return null;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return List.of();
    }

    @Override
    public BookDto updatBook(Long isbn, BookDto bookDto, MultipartFile file) {
        return null;
    }

    @Override
    public String deleteBook(Long isbn) {
        return "";
    }
}
