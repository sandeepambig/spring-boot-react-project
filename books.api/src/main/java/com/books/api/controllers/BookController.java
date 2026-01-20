package com.books.api.controllers;

import com.books.api.dtos.BookDto;
import com.books.api.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add-book")
    public ResponseEntity<BookDto> addBook(@RequestPart String bookDtoJson,
                                           @RequestPart(required = false)MultipartFile file) throws IOException{

        if(file == null || file.isEmpty()) file=null;
        BookDto bookDto = getBookDto(bookDtoJson);
       BookDto bookDto1 = bookService.addBook(bookDto,file);

       return new ResponseEntity<BookDto>(bookDto1,HttpStatus.CREATED);
    }

    @GetMapping("/all-books")
    public ResponseEntity<List<BookDto>> getAllBooks(){

        List<BookDto> list = bookService.getAllBooks();

        return new ResponseEntity<List<BookDto>>(list,HttpStatus.OK);
    }

    @GetMapping("/get-book/{isbn}")
    public ResponseEntity<BookDto> getBook (@PathVariable Long isbn){

       BookDto bookDto = bookService.getBook(isbn);

       return ResponseEntity.ok(bookDto);
    }


    @PutMapping("/update-book/{isbn}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long isbn,@RequestPart BookDto bookDto, @RequestPart(required = false) MultipartFile file) throws IOException{

        if (file == null || file.isEmpty())  file = null;
        BookDto updatedBookDto = bookService.updatBook(isbn,bookDto,file);
        return ResponseEntity.ok(updatedBookDto);
    }
    @DeleteMapping("/delete-book/{isbn}")
    public ResponseEntity<String> deleteBook (@PathVariable Long isbn) throws IOException{

        return ResponseEntity.ok(bookService.deleteBook(isbn));
    }
    private BookDto getBookDto(String bookDtoJson){
        BookDto bookDto = new BookDto();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            bookDto = objectMapper.readValue(bookDtoJson,BookDto.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return bookDto;
    }

}
