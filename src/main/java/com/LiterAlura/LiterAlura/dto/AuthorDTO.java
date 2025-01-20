package com.LiterAlura.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorDTO {
    private String name;
    private Integer birth_year;
    @JsonProperty("death_year")
    private Integer deathYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "name='" + name + '\'' +
                ", birth_year=" + birth_year +
                ", deathYear=" + deathYear +
                '}';
    }
}
