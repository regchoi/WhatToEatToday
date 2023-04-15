package team.project.WhatToEatToday.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import team.project.WhatToEatToday.domain.member.Manager;

@Entity
@Getter
@Setter
@Table(name = "\"condition\"")
public class Condition {

	
	@Id 
	@Column(name = "condition_id")
	private Long id;

	private String name;
	

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conditioncategory_id")
	private ConditionCategory concate;

	@OneToMany(mappedBy = "condition")
	private List<ConditionMenu> conditionMenu = new ArrayList<>();
	
	
    public void setConditionCategory(ConditionCategory concate) {
        this.concate = concate;
        concate.getCondition().add(this);
    }
	
   
	
}
