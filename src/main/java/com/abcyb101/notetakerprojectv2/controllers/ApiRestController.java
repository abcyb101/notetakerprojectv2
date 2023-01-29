package com.abcyb101.notetakerprojectv2.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abcyb101.notetakerprojectv2.models.Note;
import com.abcyb101.notetakerprojectv2.repositories.NoteRepository;
import com.abcyb101.notetakerprojectv2.repositories.TagRepository;

@CrossOrigin(origins = "http://localhost:8089") //dev, not prod!
@RestController
@RequestMapping("/api")
public class ApiRestController {
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	TagRepository tagRepository;
	
	@GetMapping("/notes")
	public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false) String title) {
		try {
			System.out.println("1");
			List<Note> notes = new ArrayList<Note>();
			System.out.println("2");

			if (title == null) {
				System.out.println("A");
				noteRepository.findAll();
				System.out.println("critical");
				//noteRepository.findAll().forEach(e -> System.out.println("hit!"));
				noteRepository.findAll().forEach(notes::add);
				System.out.println("B");
			} else {
				System.out.println("3");
				noteRepository.findByTitleContaining(title).forEach(notes::add);
				System.out.println("4");
			}

			if (notes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("5");
			return new ResponseEntity<>(notes, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/notes/{id}")
	public ResponseEntity<Note> getTutorialById(@PathVariable("id") long id) {
		Optional<Note> noteData = noteRepository.findById(id);

		if (noteData.isPresent()) {
			return new ResponseEntity<>(noteData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/notes")
	public ResponseEntity<Note> createNote(@RequestBody Note note) {
		try {
			Note newNote = noteRepository.save(new Note(note.getTitle(), note.getNoteText()));
			return new ResponseEntity<>(newNote, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/notes/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable("id") long id, @RequestBody Note note) {
		Optional<Note> noteData = noteRepository.findById(id);

		if (noteData.isPresent()) {
			Note noteUpdated = noteData.get();
			noteUpdated.setTitle(note.getTitle());
			noteUpdated.setNoteText(note.getNoteText());
			/*
			for(Tag tag:note.getTags()) {
				noteUpdated.addTag(tag.getName());
			}
			for(Tag tag:note.getTags()) {
				note.removeTag(tag.getName());
			}
			*/
			return new ResponseEntity<>(noteRepository.save(noteUpdated), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/notes/{id}")
	public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") long id) {
		try {
			noteRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/notes")
	public ResponseEntity<HttpStatus> deleteAllNotes() {
		try {
			noteRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
}