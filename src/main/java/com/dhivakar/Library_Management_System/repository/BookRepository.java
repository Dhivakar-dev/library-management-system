package com.dhivakar.Library_Management_System.repository;

import com.dhivakar.Library_Management_System.modal.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    @Query("""
    SELECT b
    FROM Book b
    WHERE b.active = TRUE
      AND (
            :searchTerm IS NULL
            OR LOWER(b.title)  LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            OR LOWER(b.isbn)   LIKE LOWER(CONCAT('%', :searchTerm, '%'))
          )
      AND (:genreId IS NULL OR b.genre.id = :genreId)
      AND (:availableOnly = FALSE OR b.availableCopies > 0)
""")

    Page<Book> searchBooksWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("genreId") Long genreId,
            @Param("availableOnly") boolean availableOnly,
            Pageable pageable
    );

    long countByActiveTrue();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.availableCopies > 0 AND b.active = true")
    long countAvailableBooks();



}
