package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.exception.BookException;
import com.dhivakar.Library_Management_System.payload.dto.BookDTO;
import com.dhivakar.Library_Management_System.payload.request.BookSearchRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;

import java.util.List;

public interface BookService {

    BookDTO createBook(BookDTO bookDTO) throws BookException;
    List<BookDTO> createBooksBulk(List<BookDTO> bookDTOList) throws BookException;
    BookDTO getBookById(Long bookId) throws BookException;
    BookDTO getBookByISBN(String isbn) throws BookException;
    BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException;
    void deleteBook(Long bookId) throws BookException;
    void hardDeleteBook(Long bookId) throws BookException;

    PageResponse<BookDTO> searchBooksWithFilters(
            BookSearchRequest searchRequest
    );

    long getTotalActiveBooks();

    long getTotalAvailableBooks();
}
