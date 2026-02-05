package com.dhivakar.Library_Management_System.service.impl;

import com.dhivakar.Library_Management_System.domain.BookLoanStatus;
import com.dhivakar.Library_Management_System.mapper.BookReviewMapper;
import com.dhivakar.Library_Management_System.modal.Book;
import com.dhivakar.Library_Management_System.modal.BookLoan;
import com.dhivakar.Library_Management_System.modal.BookReview;
import com.dhivakar.Library_Management_System.modal.User;
import com.dhivakar.Library_Management_System.payload.dto.BookReviewDTO;
import com.dhivakar.Library_Management_System.payload.request.CreateReviewRequest;
import com.dhivakar.Library_Management_System.payload.request.UpdateReviewRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;
import com.dhivakar.Library_Management_System.repository.BookLoanRepository;
import com.dhivakar.Library_Management_System.repository.BookRepository;
import com.dhivakar.Library_Management_System.repository.BookReviewRepository;
import com.dhivakar.Library_Management_System.service.BookReviewService;
import com.dhivakar.Library_Management_System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final UserService userService;
    private final BookRepository bookRepository;
    private final BookReviewMapper bookReviewMapper;
    private final BookLoanRepository bookLoanRepository;


    @Override
    public BookReviewDTO createReview(CreateReviewRequest request) throws Exception {

        // Step 1: Fetch the currently logged-in user
        User user = userService.getCurrentUser();

        // Step 2: Validate that the book exists
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new Exception("book not found!"));

        //3 Check if the user has already reviewed the book
        if(bookReviewRepository.existsByUserIdAndBookId(user.getId(), book.getId())){
            throw new Exception("you have already reviewed this book!");
        }

        //4 Check if the user has read the book
        boolean hasReadBook = hasUserReadBook(user.getId(), book.getId());
        if(!hasReadBook){
            throw new Exception("you have not read this book!");
        }

        // 5. create review
        BookReview bookReview = new BookReview();
        bookReview.setUser(user);
        bookReview.setBook(book);
        bookReview.setRating(request.getRating());
        bookReview.setReviewText(request.getReviewText());
        bookReview.setTitle(request.getTitle());

        BookReview savedBookReview = bookReviewRepository.save(bookReview);

        return bookReviewMapper.toDTO(savedBookReview);
    }



    @Override
    public BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) throws Exception {

        // 1. fetch logged user
        User user = userService.getCurrentUser();

        // 2. find the review
        BookReview bookReview = bookReviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("review not found!"));

        // 2. check if logged user is the owner of the review
        if (!bookReview.getUser().getId().equals(user.getId())) {
            throw new Exception("you have not reviewed this book!");
        }


        // 3. update review
        bookReview.setReviewText(request.getReviewText());
        bookReview.setTitle(request.getTitle());
        bookReview.setRating(request.getRating());

        BookReview savedBookReview = bookReviewRepository.save(bookReview);
        return bookReviewMapper.toDTO(savedBookReview);
    }

    @Override
    public void deleteReview(Long reviewId) throws Exception {

        User currentUser = userService.getCurrentUser();

        // Step 1: Find the review
        BookReview bookReview = bookReviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("Review not found with id: " + reviewId));

        // Step 2: Check if the current user is the owner of the review
        if (!bookReview.getUser().getId().equals(currentUser.getId())) {
            throw new Exception("You can only delete your own reviews");
        }

        bookReviewRepository.delete(bookReview);

    }

    @Override
    public PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) throws Exception {


        Book book = bookRepository.findById(id).orElseThrow(
                () -> new Exception("book not found by id!")
        );
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("createdAt").descending());
        Page<BookReview> reviewPage = bookReviewRepository.findByBook(book, pageable);
        return convertToPageResponse(reviewPage);
    }

    private boolean hasUserReadBook(Long userId, Long bookId) {
        List<BookLoan> bookLoans = bookLoanRepository.findByBookId(bookId);

        return bookLoans.stream()
                .anyMatch(loan -> loan.getUser().getId().equals(userId) &&
                        loan.getStatus() == BookLoanStatus.RETURNED);
    }

    private PageResponse<BookReviewDTO> convertToPageResponse(Page<BookReview> reviewPage) {
        List<BookReviewDTO> reviewDTOs = reviewPage.getContent()
                .stream()
                .map(bookReviewMapper::toDTO)
                .collect(Collectors.toList());

        return new PageResponse<>(
                reviewDTOs,
                reviewPage.getNumber(),
                reviewPage.getSize(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.isLast(),
                reviewPage.isFirst(),
                reviewPage.isEmpty()
        );
    }
}
