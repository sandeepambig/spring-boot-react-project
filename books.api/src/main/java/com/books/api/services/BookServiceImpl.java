package com.books.api.services;

import com.books.api.dtos.BookDto;
import com.books.api.entity.Book;
import com.books.api.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Value("${project.images}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;
    private final BookRepository bookRepository;
    private final FileService fileService;

    public BookServiceImpl(BookRepository bookRepository,FileService fileService) {
        this.bookRepository = bookRepository;
        this.fileService = fileService;
    }

    @Override
    public BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException {

        if(Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename()))){
            throw new FileAlreadyExistsException("File already exists");
        }

        String uploadedFileName =fileService.uploadFile(path,file);
        String bookCoverUrl = baseUrl+"/api/v1/file/" +uploadedFileName;
        bookDto.setBookCover(uploadedFileName);
        bookDto.setBookCoverUrl(bookCoverUrl);

        Book book = converToBook(bookDto);
        bookRepository.save(book);
        BookDto bookDto1 = converToBookDto(book);
        return bookDto1;
    }

    @Override
    public BookDto getBook(Long isbn) {

       Book book = bookRepository.findById(isbn).orElseThrow(()-> new RuntimeException("Book not found"));
        return converToBookDto(book);
    }

    @Override
    public List<BookDto> getAllBooks() {

       List<Book>  books= bookRepository.findAll();

        return books.stream().map(this::converToBookDto)
                              .toList();
    }

    @Override
    public BookDto updatBook(Long isbn, BookDto bookDto, MultipartFile file) throws IOException {

        Book book = bookRepository.findById(isbn).orElseThrow(()->new RuntimeException("book not found with this is"));
        String bookCover = book.getBookCover();
        String bookCoverUrl = book.getBookCoverUrl();
        if(file != null){
            Files.deleteIfExists(Paths.get(path+File.separator+bookCover));
            bookCover = fileService.uploadFile(path,file);
            bookCoverUrl = baseUrl +"/api/v1/file/"+bookCover;

        }
        bookDto.setBookCover(bookCover);
        bookDto.setBookCoverUrl(bookCoverUrl);

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        book.setDescription(bookDto.getDescription());
        book.setCategory(bookDto.getCategory());
        book.setQuantity(bookDto.getQuantity());

        Book updatedBook = bookRepository.save(book);

        return converToBookDto(updatedBook);
    }

    @Override
    public String deleteBook(Long isbn) throws IOException {

        Book book = bookRepository.findById(isbn).orElseThrow(()->new RuntimeException("book not found with this is"));
        Files.deleteIfExists(Paths.get(path+File.separator+book.getBookCover()));
        bookRepository.delete(book);
        return "Book deleted successfully";
    }

    private BookDto converToBookDto(Book book){
        return BookDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .description(book.getDescription())
                .category(book.getCategory())
                .quantity(book.getQuantity())
                .bookCover(book.getBookCover())
                .bookCoverUrl(book.getBookCoverUrl())
                .build();
    }

    private Book converToBook(BookDto bookDto){
        return Book.builder()
                .isbn(bookDto.getIsbn())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .price(bookDto.getPrice())
                .description(bookDto.getDescription())
                .category(bookDto.getCategory())
                .quantity(bookDto.getQuantity())
                .bookCover(bookDto.getBookCover())
                .bookCoverUrl(bookDto.getBookCoverUrl())
                .build();
    }
}
