package com.kot.reactive.controller;

import com.kot.reactive.dao.BookRepository;
import com.kot.reactive.dto.Book;
import com.kot.reactive.exception.BookAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Flux<Book> getBooks(){
        return bookRepository.getBooks();
    }

    @GetMapping("/{id}")
    public Mono<Book> getBook(@PathVariable int id){
        return bookRepository.getBooks()
                .filter(book-> book.getBookId() == id)
                .next()
                .switchIfEmpty(Mono.error(new BookAPIException("Book not found with id "+ id)));
    }

}
