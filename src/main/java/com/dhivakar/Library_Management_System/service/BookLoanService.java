package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.domain.BookLoanStatus;
import com.dhivakar.Library_Management_System.payload.dto.BookLoanDTO;
import com.dhivakar.Library_Management_System.payload.request.BookLoanSearchRequest;
import com.dhivakar.Library_Management_System.payload.request.CheckinRequest;
import com.dhivakar.Library_Management_System.payload.request.CheckoutRequest;
import com.dhivakar.Library_Management_System.payload.request.RenewalRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;

public interface BookLoanService {

    BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest) throws Exception;

    BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest) throws Exception;

    BookLoanDTO checkinBook(CheckinRequest checkinRequest) throws Exception;

    BookLoanDTO renewCheckout(RenewalRequest renewalRequest) throws Exception;

    PageResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) throws Exception;

    PageResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request);

    int updateOverdueBookLoans();
}
