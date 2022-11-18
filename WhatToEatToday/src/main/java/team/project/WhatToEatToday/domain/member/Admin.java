package team.project.WhatToEatToday.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
public class Admin extends Member {

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Manager> managers = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL )
    private List<Customer> customers = new ArrayList<>();

}
