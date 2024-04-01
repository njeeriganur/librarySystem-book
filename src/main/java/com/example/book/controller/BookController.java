package com.example.book.controller;

import com.example.book.dto.UpdateBookDto;
import com.example.book.model.Book;
import com.example.book.repository.BookRepository;


import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/books")

public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    //POST request to add book
    @PostMapping("")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            Optional<Book> bookData = bookRepository.findByIsbn(book.getIsbn());
            if (bookData.isEmpty()) {
                bookService.saveBook(book);
                return new ResponseEntity<>("Book added successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Book already exists", HttpStatus.CONFLICT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GET request to fetch details of all books
    @GetMapping("")
    public ResponseEntity<List<Book>> getBooks() {
        try {
            List<Book> books = new ArrayList<Book>();
            bookRepository.findAll().forEach(books::add);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GET request to fetch details of a book using ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable("isbn") String isbn) {
        Optional<Book> bookData = bookRepository.findByIsbn(isbn);
        if (bookData.isPresent()) {
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Get request to check whether the book exists or not using ISBN
    @GetMapping("/check/{isbn}")
    public ResponseEntity<Boolean> checkBook(@PathVariable("isbn") String isbn) {
        try {
            return new ResponseEntity<>(bookRepository.existsByIsbn(isbn), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //PUT request to update the details of a book
    @PutMapping("/{isbn}")
    public ResponseEntity<String> updateDetailsByIsbn(@PathVariable("isbn") String isbn, @RequestBody Book book) {
        Optional<Book> bookData = bookRepository.findByIsbn(isbn);
        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setIsbn(book.getIsbn());
            _book.setBookName(book.getBookName());
            _book.setAuthor(book.getAuthor());
            _book.setCategory(book.getAuthor());
            _book.setPublisher(book.getPublisher());
            _book.setCopies(book.getCopies());
            bookRepository.save(_book);
            return new ResponseEntity<>("Book details updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //PUT request to add copies of the book
    @PutMapping("/add-copy")
    public ResponseEntity<String> addCopy(@RequestBody UpdateBookDto updateBookDto) {
        Optional<Book> bookData = bookRepository.findByIsbn(updateBookDto.getIsbn());
        if (bookData.isPresent()) {
            Book book = bookData.get();
            book.setCopies(updateBookDto.getCopies() + book.getCopies());
            bookRepository.save(book);
            return new ResponseEntity<>("Copies added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book was not found", HttpStatus.NOT_FOUND);
        }
    }

    //PUT request to remove copies of the book
    @PutMapping("/remove-copy")
    public ResponseEntity<String> removeCopy(@RequestBody UpdateBookDto updateBookDto) {
        Optional<Book> bookData = bookRepository.findByIsbn(updateBookDto.getIsbn());
        if (bookData.isPresent()) {
            Book book = bookData.get();
            if (book.getCopies() - updateBookDto.getCopies() < 0) {
                return new ResponseEntity<>(String.format("Insufficient copies\nCopies left:%d", book.getCopies() - updateBookDto.getCopies()), HttpStatus.BAD_REQUEST);
            } else {
                book.setCopies(book.getCopies() - updateBookDto.getCopies());
                bookRepository.save(book);
                return new ResponseEntity<>("copies removed Successfully", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE request to delete book details
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> removeBook(@PathVariable("isbn") String isbn) {
        try {
            bookRepository.deleteByIsbn(isbn);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
