package com.abcyb101.notetakerprojectv2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abcyb101.notetakerprojectv2.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{
	Optional<Note> findByTitle(String title);
	List<Note> findByTitleContaining(String title);
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
}
