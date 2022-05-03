package com.self.roomescape.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "theme")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tid;

    private int cid;

    private int difficulty;

    private String tname;
    private String img;
    private String description;
    private String rplayer;
    private String time;
    private String genre;
    private String type;
    private float grade;
    private String activity;

}
