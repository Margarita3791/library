package com.library.service;

import com.library.model.BookLoan;
import com.library.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;

    @Autowired
    public BookLoanService(BookLoanRepository bookLoanRepository) {
        this.bookLoanRepository = bookLoanRepository;
    }

    public List<BookLoan> getAllBookLoans() {
        return bookLoanRepository.findAll();
    }

    public Optional<BookLoan> getBookLoanById(Long id) {
        return bookLoanRepository.findById(id);
    }

    public BookLoan createBookLoan(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }

    public void deleteBookLoan(Long id) {
        bookLoanRepository.deleteById(id);
    }
}
