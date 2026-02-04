package com.dhivakar.Library_Management_System.domain;

public enum FineType {

    OVERDUE,

    /**
     * Fine for damaged books
     */
    DAMAGE,

    /**
     * Fine for lost books (replacement cost)
     */
    LOSS,

    /**
     * Processing or administrative fees
     */
    PROCESSING
}
