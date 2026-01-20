package com.books.api.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {


    private  Long isbn;


    private String title;


    private String author;

    private String description;


    private String category;


    private Double price ;

    private Integer quantity;

    private String bookCover;

    private String bookCoverUrl;
}
