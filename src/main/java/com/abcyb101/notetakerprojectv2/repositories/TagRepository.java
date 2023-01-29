package com.abcyb101.notetakerprojectv2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abcyb101.notetakerprojectv2.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
	Optional<Tag> findByName(Tag title);
	Optional<Tag> findByName(String title);
	
}
