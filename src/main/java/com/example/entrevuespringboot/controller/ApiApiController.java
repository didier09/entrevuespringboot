package com.example.entrevuespringboot.controller;

import com.example.entrevuespringboot.api.ApiApi;
import com.example.entrevuespringboot.dto.ReponseFilm;
import com.example.entrevuespringboot.dto.RequeteCreationFilm;
import com.example.entrevuespringboot.entity.Film;
import com.example.entrevuespringboot.mapper.FilmMapper;
import com.example.entrevuespringboot.repository.FilmRepository;
import com.example.entrevuespringboot.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class ApiApiController implements ApiApi {

    private FilmService filmService;
    private FilmRepository filmRepository;
    private FilmMapper filmMapper;


    @Override
    public ResponseEntity<ReponseFilm> creeFilm(@Valid RequeteCreationFilm requeteCreationFilm) {

        Film film = filmService.ajouter(filmMapper
                .creationFilmDtoToEntity(requeteCreationFilm));

        UriComponentsBuilder path = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/films/");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(filmMapper.entityToReponseFilmDto(film));

    }

    @Override
    public ResponseEntity<ReponseFilm> obtenirFilm(Integer id) {
        ReponseFilm reponseFilm = filmRepository.findById(id.longValue())
                .map(e -> filmMapper.entityToReponseFilmDto(e))
                .orElseThrow(() -> new IllegalArgumentException("Film non trouvé"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(reponseFilm);
    }
}
