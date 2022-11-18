package team.project.WhatToEatToday.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class ConditionMenu {

    @Id
    @Column(name = "condition_menu_id")
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id")
    private Condition condition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_menu_id")
    private CrossMenu crossMenus;

    public void setCrossMenu(CrossMenu crossMenu) {
        this.crossMenus = crossMenu;
        crossMenu.getConditionMenu().add(this);
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
        condition.getConditionMenu().add(this);
    }
	
}
