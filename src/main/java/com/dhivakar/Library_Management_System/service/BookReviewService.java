package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.payload.dto.BookReviewDTO;
import com.dhivakar.Library_Management_System.payload.request.CreateReviewRequest;
import com.dhivakar.Library_Management_System.payload.request.UpdateReviewRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;

public interface BookReviewService {

    BookReviewDTO createReview(CreateReviewRequest request) throws Exception;

    BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) throws Exception;

    void deleteReview(Long reviewId) throws Exception;

    PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) throws Exception;


}
