package com.kot.reactive.handler;

import com.kot.reactive.dao.BookRepository;
import com.kot.reactive.dto.Book;
import com.kot.reactive.exception.BookAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BookHandler {

    @Autowired
    private BookRepository bookRepository;

    public Mono<ServerResponse> getBooks(ServerRequest serverRequest){
        return ServerResponse.ok().body(bookRepository.getBooks(),Book.class);
    }

    public Mono<ServerResponse> getBook(ServerRequest serverRequest){
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        Mono<Book> bookMono = bookRepository.getBooks()
                                    .filter(book -> book.getBookId() == id)
                                    .next()
                                    .switchIfEmpty(Mono.error(new BookAPIException("Book not found with id "+ id)));

        return ServerResponse.ok().body(bookMono,Book.class);

    }
}
