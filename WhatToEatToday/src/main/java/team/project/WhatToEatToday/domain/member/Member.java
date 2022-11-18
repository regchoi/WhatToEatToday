package team.project.WhatToEatToday.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Member {

    @Id @NotEmpty
    @Column(name = "member_id")
    private String id;

    private String password;
    private String name;
    private String email;
    private String tel;

    @Column(length=2000)
    private String address;

    private String addressDetail;

}
