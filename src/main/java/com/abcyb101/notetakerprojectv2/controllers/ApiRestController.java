package com.abcyb101.notetakerprojectv2.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.abcyb101.notetakerprojectv2.models.Tag;
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
	
	//Notes
	
	@GetMapping("/notes")
	public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false) String title) {
		try {
			List<Note> notes = new ArrayList<Note>();
			if (title == null) {
				notes.addAll(noteRepository.findAll()); 
			} else {
				noteRepository.findByTitleContaining(title).forEach(notes::add);
			}

			if (notes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(notes, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/notes/{id}")
	public ResponseEntity<Note> getNoteslById(@PathVariable("id") long id) {
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
	
	
	//Tags
	@GetMapping("/tags")
	public ResponseEntity<List<Tag>> getAlltags(@RequestParam(required = false) String name) {
		try {
			List<Tag> tags = new ArrayList<Tag>();

			if (name == null) {
				tags.addAll(tagRepository.findAll()); 
			} else {
				tagRepository.findByNameContaining(name).forEach(tags::add);
			}

			if (tags.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("5");
			return new ResponseEntity<>(tags, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/tags/{id}")
	public ResponseEntity<Tag> getTagById(@PathVariable("id") long id) {
		Optional<Tag> tagData = tagRepository.findById(id);

		if (tagData.isPresent()) {
			return new ResponseEntity<>(tagData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/tags")
	public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
		try {
			Tag newTag = tagRepository.save(new Tag(tag.getName()));
			return new ResponseEntity<>(newTag, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/tags/{id}")
	public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tag) {
		Optional<Tag> tagData = tagRepository.findById(id);

		if (tagData.isPresent()) {
			Tag tagUpdated = tagData.get();
			tagUpdated.setName(tag.getName());
			tagUpdated.setNotes(tag.getNotes());

			return new ResponseEntity<>(tagRepository.save(tagUpdated), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/tags/notelist/{id}")
	public ResponseEntity<Tag> updateTagNoteList(@PathVariable("id") long id, @RequestBody List<String> noteNames) {
		Optional<Tag> tagData = tagRepository.findById(id);

		if (tagData.isPresent()) {
			Tag tagUpdated = tagData.get();
			Set<Note>noteSet = new HashSet<>();
			for(String reqNoteName : noteNames) {
				noteSet.add(noteRepository.findByTitle(reqNoteName).orElse(null));
			}
			
			tagUpdated.setNotes(noteSet);

			return new ResponseEntity<>(tagRepository.save(tagUpdated), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tags/{id}")
	public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
		try {
			tagRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/tags")
	public ResponseEntity<HttpStatus> deleteAllTags() {
		try {
			tagRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	
}