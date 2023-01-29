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
	//List<Note> findAll(); //why do I have to add this? Shouldn't it be available on default?
	//Supported methods by JpaRepository: save(), findOne(), findById(), findAll(), count(), delete(), deleteById().
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

}
