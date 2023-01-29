package com.abcyb101.notetakerprojectv2.utility;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.abcyb101.notetakerprojectv2.models.Note;
import com.abcyb101.notetakerprojectv2.models.Tag;
import com.abcyb101.notetakerprojectv2.repositories.TagRepository;

public class TagSynchronizer {
	
	@Autowired
	TagRepository tagRepository;
/*	
	public void addTagToNote(String tagname, Note note) {
		Tag t = tagRepository.findByName(tagname).orElse(null);
		if (t == null) {
			Set<Note> noteSet = new HashSet<>();
		    Tag newTag = new Tag();
		    newTag.setName(tagname);
		    newTag.setNotes(noteSet);
		    tagRepository.save(newTag);
		} else {
			t.getNotes().add(note);
			tagRepository.save(t);
		}	
	}
	
	public void removeTagFromNote(String tagname, Note note) { //originally I asked for Tag, instead of String. However it is safer to search for it. The request could be from an old source!
		Tag t = tagRepository.findByName(tagname).orElse(null);
		if (t != null) {
			t.getNotes().remove(note);
			tagRepository.save(t);
		}	
	}
*/
}
