package team.project.WhatToEatToday.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import team.project.WhatToEatToday.domain.EatingHouse;

@Entity
@DiscriminatorValue("M")
@Table(name = "managers")
@Getter
@Setter
public class Manager extends Member {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "manager")
    private List<EatingHouse> EatingHouses  = new ArrayList<>();

    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getManagers().add(this);
    }
}
