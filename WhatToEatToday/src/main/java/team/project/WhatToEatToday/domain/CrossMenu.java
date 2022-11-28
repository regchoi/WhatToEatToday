package team.project.WhatToEatToday.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class CrossMenu {

    @Id
    @Column(name = "cross_menu_id")
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "crossMenu")
    private List<Menu> menu = new ArrayList<>();

    @OneToMany(mappedBy = "crossMenus")
    private List<ConditionMenu> conditionMenu = new ArrayList<>();

}
