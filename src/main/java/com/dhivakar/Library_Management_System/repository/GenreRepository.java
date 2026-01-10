package com.dhivakar.Library_Management_System.repository;

import com.dhivakar.Library_Management_System.modal.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
