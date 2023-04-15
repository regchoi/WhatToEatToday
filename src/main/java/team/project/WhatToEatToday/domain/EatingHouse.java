package team.project.WhatToEatToday.domain;

import lombok.Getter;
import lombok.Setter;
import team.project.WhatToEatToday.domain.member.Manager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eating_houses")
@Getter
@Setter
public class EatingHouse {

    @Id
    @GeneratedValue
    @Column(name = "eating_house_id")
    private Long id;

    private String Name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "eatingHouse")
    private List<Menu> menus = new ArrayList<>();

    @Column(length=2000)
    private String address;

    private String addressDetail;

    private String uploadEatingHouseFileName;
    private String storeEatingHouseFileName;

    public void setManager(Manager manager) {
        this.manager = manager;
        manager.getEatingHouses().add(this);
    }

    public void addMenus(Menu item) {
        this.menus.add(item);
        item.setEatingHouse(this);
    }




}
