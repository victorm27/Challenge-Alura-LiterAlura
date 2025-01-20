package com.LiterAlura.LiterAlura.repositories;



import com.LiterAlura.LiterAlura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear > :year)")
    List<Author> findAuthorsAliveInYear(@Param("year") int year);
    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int year, int yearEnd);
}

