package team.project.WhatToEatToday.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ConditionCategory {
	
	
	@Id
	@Column(name = "condition_category_id")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "concate")
	private List<Condition> condition = new ArrayList<>();
	

}
