package com.example.entrevuespringboot.mapper;

import com.example.entrevuespringboot.dto.ReponseActeur;
import com.example.entrevuespringboot.dto.ReponseFilm;
import com.example.entrevuespringboot.dto.RequeteCreationAuteur;
import com.example.entrevuespringboot.dto.RequeteCreationFilm;
import com.example.entrevuespringboot.entity.Acteur;
import com.example.entrevuespringboot.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FilmMapper {

    @Mapping(target = "titre", source = "titre")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "acteurs", source = "acteurs")
    Film creationFilmDtoToEntity(RequeteCreationFilm requeteCreationFilm);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "titre", source = "titre")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "acteurs", source = "acteurs")
    ReponseFilm entityToReponseFilmDto(Film film);


    default Acteur map(RequeteCreationAuteur requeteCreationAuteur) {
        Acteur acteur = new Acteur();
        acteur.setNom(requeteCreationAuteur.getNom());
        acteur.setPrenom(requeteCreationAuteur.getPrenom());
        return acteur;

    }


    default ReponseActeur map(Acteur acteur) {
        ReponseActeur reponseActeur = new ReponseActeur();
        reponseActeur.setId(BigDecimal.valueOf(acteur.getId()));
        reponseActeur.setNom(acteur.getNom());
        reponseActeur.setPrenom(acteur.getPrenom());
        return reponseActeur;

    }


}
