package com.example.entrevuespringboot.it;

import com.example.entrevuespringboot.dto.RequeteCreationFilm;
import com.example.entrevuespringboot.entity.Acteur;
import com.example.entrevuespringboot.entity.Film;
import com.example.entrevuespringboot.repository.FilmRepository;
import com.example.entrevuespringboot.service.FilmService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvcBuilder;

import javax.transaction.Transactional;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class FilmITTest  {

    @Autowired
    protected MockMvcBuilder mockMvcBuilder;

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmService filmService;


    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(mockMvcBuilder);
    }


    @Test
    @Transactional
    public void shouldAddFilm() {
        // given:
        MockMvcRequestSpecification request = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // when:
        ResponseOptions response = given().spec(request)
                .body(getRequeteCreationFilm())
                .post("api/films");

        // then:
        Optional<Film> film
                = filmRepository.findByTitre("SQUAD GAME");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(film.get().getId()).isNotNull();
    }


    @Test
    @Transactional
    public void shouldReturn400WhenNoFilmFound() {
        Film film = filmService.ajouter(getFilm());

        // given:
        MockMvcRequestSpecification request = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // when:
        ResponseOptions response = given().spec(request)
                .body(getRequeteCreationFilm())
                .get("api/films/"+2323);

        // then:
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @Transactional
    public void shouldGetOne() {
        Film film = filmService.ajouter(getFilm());

        // given:
        MockMvcRequestSpecification request = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // when:
        ResponseOptions response = given().spec(request)
                .body(getRequeteCreationFilm())
                .get("api/films/"+film.getId());

        // then:
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }



    private RequeteCreationFilm getRequeteCreationFilm() {
        RequeteCreationFilm requeteCreationFilm = new RequeteCreationFilm();
        requeteCreationFilm.setTitre("SQUAD GAME");
        return requeteCreationFilm;
    }


    private Film getFilm() {
        Film film = new Film();
        film.setTitre("SQUAD GAME");
        film.setDescription("SQUAD GAME Saison 2");

        Acteur acteur = new Acteur();
        acteur.setNom("Yao");
        acteur.setPrenom("Didier");
        film.getActeurs().add(acteur);
        return film;
    }
}
