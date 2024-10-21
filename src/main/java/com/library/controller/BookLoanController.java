package com.library.controller;

import com.library.model.BookLoan;
import com.library.service.BookLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookLoans")
public class BookLoanController {

    private final BookLoanService bookLoanService;

    @Autowired
    public BookLoanController(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }

    @GetMapping
    public List<BookLoan> getAllBookLoans() {
        return bookLoanService.getAllBookLoans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookLoan> getBookLoanById(@PathVariable Long id) {
        return bookLoanService.getBookLoanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BookLoan createBookLoan(@RequestBody BookLoan bookLoan) {
        return bookLoanService.createBookLoan(bookLoan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookLoan> updateBookLoan(@PathVariable Long id, @RequestBody BookLoan bookLoan) {
        return bookLoanService.getBookLoanById(id)
                .map(existingBookLoan -> {
                    bookLoan.setId(existingBookLoan.getId()); // Устанавливаем ID существующего займа книги
                    return ResponseEntity.ok(bookLoanService.createBookLoan(bookLoan)); // Используем createBookLoan, чтобы сохранить обновленный объект
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookLoan(@PathVariable Long id) {
        bookLoanService.deleteBookLoan(id);
        return ResponseEntity.noContent().build();
    }
}
