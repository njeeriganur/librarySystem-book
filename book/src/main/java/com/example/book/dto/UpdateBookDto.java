package com.example.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Data Transfer Object used to update the copies of Book
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDto {

    private String isbn;

    private int copies;

}
