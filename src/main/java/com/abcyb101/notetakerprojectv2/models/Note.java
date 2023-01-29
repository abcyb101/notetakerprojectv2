package com.abcyb101.notetakerprojectv2.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.abcyb101.notetakerprojectv2.utility.TagSynchronizer;

@Entity
@Table(	name = "notes")
public class Note {
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)  
	private Integer noteID;
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "title", nullable = false)
	@NotBlank(message = "note must have a title")
	@Size(max = 128)
	private String title;
	
	@Column(name = "date_created", nullable = false)
	private LocalDateTime dateCreated;
	
	/*
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "note_tags",
		joinColumns = @JoinColumn(name = "note_id"),
		inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>(); //this only serves for performant tag-display. The actual tracking/filtering happens based on the Set of the Tag
	*/
	
	@Column(name= "text")
	private String noteText;
	
	public Note(String title, String noteText) {
		this.title = title;
		this.noteText = noteText;
		/*
		for (Tag t : tags) {
			addTag(t.getName());
		}
		*/
		this.dateCreated = LocalDateTime.now();
	}

	/*
	public void addTag(String tagname) {
		TagSynchronizer tagSync = new TagSynchronizer();
		tagSync.addTagToNote(tagname, this);
		tagSync = null; // JVM doesn't let the programmer destroy an object manually, even though methods such as "Runtime.getRuntime.gc();" exist (this however only informs JVM that you wnat things destroyed - it still decides itself whether to do that). The garbage collector decides whether to delete something itself. Setting this object null, however, makes it easily eligible for the gc to remove.
	}

	public void removeTag(String tagname) {
		TagSynchronizer tagSync = new TagSynchronizer();
		tagSync.removeTagFromNote(tagname, this);
		tagSync = null;
	}
*/	
	
	/////////////////////////////////////////////////Getters, Setters

	public long getNoteID() {
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
/*
	public Set<Tag> getTags() {
		return tags;
	}
*/
}
