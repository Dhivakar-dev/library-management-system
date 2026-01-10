package com.dhivakar.Library_Management_System.service.impl;


import com.dhivakar.Library_Management_System.modal.Genre;
import com.dhivakar.Library_Management_System.repository.GenreRepository;
import com.dhivakar.Library_Management_System.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre) ;
    }
}
