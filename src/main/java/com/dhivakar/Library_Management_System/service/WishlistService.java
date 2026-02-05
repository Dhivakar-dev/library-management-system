package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.payload.dto.WishlistDTO;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;

public interface WishlistService {

    WishlistDTO addToWishlist(Long bookId, String notes) throws Exception;

    void removeFromWishlist(Long bookId) throws Exception;

    PageResponse<WishlistDTO> getMyWishlist(int page, int size) throws Exception;
}
