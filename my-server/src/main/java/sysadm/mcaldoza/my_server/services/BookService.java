package sysadm.mcaldoza.my_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sysadm.mcaldoza.my_server.entities.Book;
import sysadm.mcaldoza.my_server.repos.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Optional<Book> getBookById(Long book_id) {
        try {
            return bookRepository.findById(book_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Book addBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            StringBuilder sbFail = new StringBuilder()
                    .append("Failed to add book: ")
                    .append(e.getMessage());
            throw new RuntimeException(sbFail.toString());
        }
    }

    public Optional<Book> updateBook(Long book_id, Book updatedBook) {
        try {
            Optional<Book> checkBook = bookRepository.findById(book_id);
            if (checkBook.isPresent()) {
                Book existingBook = checkBook.get();
                existingBook.setBook_author(updatedBook.getBook_author());
                existingBook.setBook_title(updatedBook.getBook_title());
                existingBook.setBook_price(updatedBook.getBook_price());
                Book modifiedBook = bookRepository.save(existingBook);
                return Optional.of(modifiedBook);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            StringBuilder sbFail = new StringBuilder()
                    .append("Failed to update book: ")
                    .append(e.getMessage());
            throw new RuntimeException(sbFail.toString());
        }
    }

    public boolean deleteBook(Long book_id) {
        try {
            bookRepository.deleteById(book_id);
            return true;
        } catch (Exception e) {
            StringBuilder sbFail = new StringBuilder()
                    .append("Failed to delete book: ")
                    .append(e.getMessage());
            throw new RuntimeException(sbFail.toString());
        }
    }
}
