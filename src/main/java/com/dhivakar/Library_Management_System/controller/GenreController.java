package com.dhivakar.Library_Management_System.controller;

import com.dhivakar.Library_Management_System.exception.GenreException;
import com.dhivakar.Library_Management_System.modal.Genre;
import com.dhivakar.Library_Management_System.payload.dto.GenreDTO;
import com.dhivakar.Library_Management_System.payload.response.ApiResponse;
import com.dhivakar.Library_Management_System.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genreDTO) {
        GenreDTO createdGenre = genreService.createGenre(genreDTO);
        return ResponseEntity.ok(createdGenre);


    }

    @GetMapping()
    public ResponseEntity<?> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

//    @RequestParam("genreId") used in case of pathVariable fails

    @GetMapping("/{genreId}")
    public ResponseEntity<?> getGenreById(@PathVariable Long genreId) throws GenreException {
        GenreDTO genres = genreService.getGenreById(genreId);
        return ResponseEntity.ok(genres);
    }
    @PutMapping("/{genreId}")
    public ResponseEntity<?> updateGenre(@PathVariable Long genreId, @RequestBody GenreDTO genre) throws GenreException {
        GenreDTO genres = genreService.updateGenre(genreId, genre);
        return ResponseEntity.ok(genres);
    }
    @DeleteMapping("/{genreId}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long genreId) throws GenreException {
        genreService.deleteGenre(genreId);
        ApiResponse response = new ApiResponse("Genre deleted successfully", true);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{genreId}/hard")
    public ResponseEntity<?> hardDeleteGenre(@PathVariable Long genreId) throws GenreException {
        genreService.hardDeleteGenre(genreId);
        ApiResponse response = new ApiResponse("Genre deleted using hard delete successfully", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-level")
    public ResponseEntity<?> getTopLevelGenres() {
        List<GenreDTO> genres = genreService.getTopLevelGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getTotalActiveGenres() {
        Long genres = genreService.getTotalActiveGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}/count-books")
    public ResponseEntity<?> getBookCountByGenre(@PathVariable Long id) {
        Long bookCount = genreService.getBookCountByGenre(id);
        return ResponseEntity.ok(bookCount);
    }




}
