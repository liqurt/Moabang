package com.self.roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long rid;

    @OneToOne
    @JoinColumn(name = "uid")
    private UserInfo userInfo;
    private int tid;
    private float rating;

    private int isSuccess;
    private int hint;
    private String timeLeft;
    private int player;
    private String recPlayer;
    private String active;
    private int difficulty;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Timestamp playDate;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Timestamp regDate;
    private String content;


}
