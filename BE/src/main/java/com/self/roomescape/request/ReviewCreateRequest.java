package com.self.roomescape.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ReviewCreateRequest {
    private int tid;
    private float rating;
    private int isSuccess;
    private int hint;
    private int clearTime;
    private int player;
    private int recPlayer;
    private String active;
    private int difficulty;
    private Date playDate;
    private String content;
}
