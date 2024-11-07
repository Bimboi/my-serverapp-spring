package sysadm.mcaldoza.my_server.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sysadm.mcaldoza.my_server.entities.Book;
import sysadm.mcaldoza.my_server.services.BookService;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{book_id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long book_id) {
        Optional<Book> bookOptional = bookService.getBookById(book_id);
        return bookOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/book")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        return ResponseEntity.ok(addedBook);
    }

    @PutMapping(path = "/book/{book_id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long book_id, @RequestBody Book book) {
        Optional<Book> modifiedBook = bookService.updateBook(book_id, book);
        return modifiedBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/book/{book_id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long book_id) {
        boolean isDeleted = bookService.deleteBook(book_id);
        if (isDeleted) {
            StringBuilder sbOk = new StringBuilder()
            .append("Book with ID ")
            .append(book_id)
            .append(" has been deleted successfully");
            return ResponseEntity.ok(sbOk.toString());
        } else {
            StringBuilder sbFail = new StringBuilder()
            .append("Failed to delete book with ID ")
            .append(book_id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(sbFail.toString());
        }
    }
}
