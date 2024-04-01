package com.example.book.model;

import jakarta.persistence.*;
import lombok.*;

//book data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "author")
    private String author;

    @Column(name = "category")
    private String category;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "copies")
    private int copies;

}
