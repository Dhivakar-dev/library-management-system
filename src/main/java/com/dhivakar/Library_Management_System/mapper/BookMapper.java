package com.dhivakar.Library_Management_System.mapper;

import com.dhivakar.Library_Management_System.exception.BookException;
import com.dhivakar.Library_Management_System.modal.Book;
import com.dhivakar.Library_Management_System.modal.Genre;
import com.dhivakar.Library_Management_System.payload.dto.BookDTO;
import com.dhivakar.Library_Management_System.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {

    private final GenreRepository genreRepository;

    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .genreId(book.getGenre().getId())
                .genreName(book.getGenre().getName())
                .genreCode(book.getGenre().getCode())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublishedDate())
                .language(book.getLanguage())
                .pages(book.getPages())
                .description(book.getDescription())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .price(book.getPrice())
                .coverImageUrl(book.getCoverImageUrl())
                .active(book.getActive())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    public Book toEntity(BookDTO dto) throws BookException {
        if (dto == null) {
            return null;
        }

        Genre genre = null;
        if (dto.getGenreId() != null) {
            genre = genreRepository.findById(dto.getGenreId())
                    .orElseThrow(() -> new BookException(
                            "Genre with ID " + dto.getGenreId() + " not found"));
        }

        return Book.builder()
                .id(dto.getId())
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .genre(genre)
                .publisher(dto.getPublisher())
                .publishedDate(dto.getPublicationDate())
                .language(dto.getLanguage())
                .pages(dto.getPages())
                .description(dto.getDescription())
                .totalCopies(dto.getTotalCopies())
                .availableCopies(dto.getAvailableCopies())
                .price(dto.getPrice())
                .coverImageUrl(dto.getCoverImageUrl())
                .active(true) // Default to active
                .build();
    }

    public void updateEntityFromDTO(BookDTO dto, Book book) throws BookException {
        if (dto == null || book == null) {
            return;
        }

        // ISBN should not be updated
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());

        // Update genre if provided
        if (dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElseThrow(() -> new BookException("Genre with ID " + dto.getGenreId() + " not found"));
            book.setGenre(genre);
        }

        book.setPublisher(dto.getPublisher());
        book.setPublishedDate(dto.getPublicationDate());
        book.setLanguage(dto.getLanguage());
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setPrice(dto.getPrice());
        book.setCoverImageUrl(dto.getCoverImageUrl());

        if (dto.getActive() != null) {
            book.setActive(dto.getActive());
        }
    }
}

