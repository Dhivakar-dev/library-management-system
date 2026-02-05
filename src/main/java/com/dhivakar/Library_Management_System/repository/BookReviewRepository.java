package com.dhivakar.Library_Management_System.repository;

import com.dhivakar.Library_Management_System.modal.Book;
import com.dhivakar.Library_Management_System.modal.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    Page<BookReview> findByBook(Book book, Pageable pageable);
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
