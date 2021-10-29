package com.example.entrevuespringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode
public class Film implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;
    @NotNull
    private String titre;
    private String description;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @ElementCollection
    @JoinTable(
            name = "FILM_ACTEURS",
            joinColumns = {
                    @JoinColumn(name = "ID_FILM", referencedColumnName = "ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "ID_ACTEUR", referencedColumnName = "ID")})
    private Set<Acteur> acteurs = new HashSet<>();


}
