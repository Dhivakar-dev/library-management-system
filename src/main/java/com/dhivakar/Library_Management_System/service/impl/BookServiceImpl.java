package com.dhivakar.Library_Management_System.service.impl;

import com.dhivakar.Library_Management_System.exception.BookException;
import com.dhivakar.Library_Management_System.mapper.BookMapper;
import com.dhivakar.Library_Management_System.modal.Book;
import com.dhivakar.Library_Management_System.payload.dto.BookDTO;
import com.dhivakar.Library_Management_System.payload.request.BookSearchRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;
import com.dhivakar.Library_Management_System.repository.BookRepository;
import com.dhivakar.Library_Management_System.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    @Override
    public BookDTO createBook(BookDTO bookDTO) throws BookException {



        if(bookRepository.existsByIsbn(bookDTO.getIsbn())){
            throw new BookException("book with isbn " + bookDTO.getIsbn() + " already exists");
        }
        Book book = bookMapper.toEntity(bookDTO);

        book.isAvailableCopiesValid();
        Book savedBook = bookRepository.save(book);

        return bookMapper.toDTO(savedBook);
    }

    @Override
    public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOList) throws BookException {
        List<BookDTO> createdBooks = new ArrayList<>();
        for (BookDTO bookDTO : bookDTOList) {
            BookDTO book = createBook(bookDTO);
            createdBooks.add(book);
        }
        return createdBooks;
    }

    @Override
    public BookDTO getBookById(Long bookId) throws BookException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("book not found!"));
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO getBookByISBN(String isbn) throws BookException {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookException("book not found!"));
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("book not found!")
        );
        bookMapper.updateEntityFromDTO(bookDTO, existingBook);
        existingBook.isAvailableCopiesValid();
        Book savedBook = bookRepository.save(existingBook);
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) throws BookException {

        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("book not found!")
        );
        existingBook.setActive(false);
        bookRepository.save(existingBook);

    }

    @Override
    public void hardDeleteBook(Long bookId) throws BookException {

        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("book not found!")
        );
        bookRepository.delete(existingBook);

    }

    @Override
    public PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {
        Pageable pageable = createPageable(searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection());

        Page<Book> bookPage = bookRepository.searchBooksWithFilters(
                searchRequest.getSearchTerm(),
                searchRequest.getGenreId(),
                searchRequest.getAvailableOnly(),
                pageable
        );
        return convertToPageResponse(bookPage);
    }

    @Override
    public long getTotalActiveBooks() {
        return bookRepository.countByActiveTrue();
    }

    @Override
    public long getTotalAvailableBooks() {
        return bookRepository.countAvailableBooks();
    }

    private Pageable createPageable(int page, int size, String sortBy, String sortDirection){
        size = Math.min(size, 10); // maximum page size of 10
        size = Math.max(size, 1); // minimum page size of 1

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }

    private PageResponse<BookDTO> convertToPageResponse(Page<Book> books) {
        List<BookDTO> content = books.getContent()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isLast(),
                books.isFirst(),
                books.isEmpty()
        );
    }
}
