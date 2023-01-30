package com.abcyb101.notetakerprojectv2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abcyb101.notetakerprojectv2.models.Note;
import com.abcyb101.notetakerprojectv2.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
	Optional<Tag> findByName(Tag name);
	Optional<Tag> findByName(String name);
	List<Tag> findByNameContaining(String name);
}
