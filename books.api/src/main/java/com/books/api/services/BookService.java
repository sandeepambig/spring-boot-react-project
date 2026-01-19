package com.books.api.services;

import com.books.api.dtos.BookDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    BookDto addBook(BookDto bookDto, MultipartFile file);
    BookDto getBook(Long isbn);
    List<BookDto> getAllBooks();
    BookDto updatBook(Long isbn,BookDto bookDto, MultipartFile file);
    String deleteBook(Long isbn);
}
