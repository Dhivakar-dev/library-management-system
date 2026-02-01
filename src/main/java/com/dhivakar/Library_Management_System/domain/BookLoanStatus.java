package com.dhivakar.Library_Management_System.domain;

public enum BookLoanStatus {

    CHECKED_OUT, // Book is currently checked out by a user
    RETURNED,    // Book has been returned and loan is complete
    OVERDUE,     // Loan is overdue (past due date and not returned)
    LOST,        // Book was lost by the user
    DAMAGED      // Book was damaged during loan period

}
