package org.guy.rpg.dwg.models.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classes")
public class Class implements Serializable {

	private static Map<Long, String> valueMap = new HashMap<Long, String>() {{
		put(1L, "Barbarian");
		put(2L, "Bard");
		put(3L, "Cleric");
		put(4L, "Druid");
		put(5L, "Fighter");
		put(6L, "Monk");
		put(7L, "Paladin");
		put(8L, "Ranger");
		put(9L, "Rogue");
		put(10L, "Sorceror");
		put(11L, "Wizard");
	}};
	
	@Id
	private Long id;

	@Column(name = "name")
	private String name;

	public Class() {

	}

	public Class(int id) {
		this.id = (long) id;
		this.name = valueMap.get(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}