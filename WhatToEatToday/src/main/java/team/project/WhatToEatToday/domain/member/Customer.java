package team.project.WhatToEatToday.domain.member;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("C")
@Table(name = "customers")
@Getter
@Setter
public class Customer extends Member {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;


    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getCustomers().add(this);
    }
}
