package com.example.book.service;

import com.example.book.model.Book;
import com.example.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    //Saves the details of book in database
    @Override
    public void saveBook(Book book) {
        Book _book = new Book();
        _book.setIsbn(book.getIsbn());
        _book.setBookName(book.getBookName());
        _book.setAuthor(book.getAuthor());
        _book.setCategory(book.getCategory());
        _book.setPublisher(book.getPublisher());
        _book.setCopies(book.getCopies());
        bookRepository.save(_book);
    }
}
