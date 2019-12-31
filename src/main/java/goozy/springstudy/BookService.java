package goozy.springstudy;

import org.springframework.beans.factory.annotation.Autowired;

public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookRepository getBookRepository() {
        return bookRepository;
    }
}
