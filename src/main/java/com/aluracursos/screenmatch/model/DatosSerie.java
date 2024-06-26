package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DatosSerie(
    @JsonAlias ("Title") String titulo,

    @JsonAlias ("totalSeasons") Integer totalDeTemporadas,

    @JsonAlias ("imdbRating") String evaluacion){

    }
