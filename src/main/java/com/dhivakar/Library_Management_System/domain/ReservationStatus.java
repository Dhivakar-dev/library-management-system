package com.dhivakar.Library_Management_System.domain;

public enum ReservationStatus {

    /**
     * Reservation is active and waiting for book to become available
     */
    PENDING,

    /**
     * Book is now available and user has been notified
     */
    AVAILABLE,

    /**
     * User has checked out the reserved book
     */
    FULFILLED,

    /**
     * User cancelled the reservation
     */
    CANCELLED,

    /**
     * Reservation expired because user did not act within the allowed time
     */
    EXPIRED
}
