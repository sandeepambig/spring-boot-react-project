package com.books.api.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class BookDto {

    @Id
    private  Long isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    private String bookCover;
    private String bookCoverUrl;
}
