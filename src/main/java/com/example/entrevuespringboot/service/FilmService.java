package com.example.entrevuespringboot.service;

import com.example.entrevuespringboot.entity.Film;
import com.example.entrevuespringboot.repository.ActeurRepository;
import com.example.entrevuespringboot.repository.FilmRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {

    private FilmRepository filmRepository;
    private ActeurRepository acteurRepository;


    @Transactional
    public Film ajouter(Film film) {
        Optional.ofNullable(film.getActeurs()).orElse(Collections.emptySet()).forEach(e->{
             acteurRepository.saveAndFlush(e);
        });

        log.debug("Ajout d'un nouveau film {}", film.getTitre());
        return filmRepository.save(film);
    }
}
