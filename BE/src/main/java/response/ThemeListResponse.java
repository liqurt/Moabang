package response;

import lombok.Data;

import javax.persistence.*;

@Data
public class ThemeListResponse {
    private int tid;
    private int cid;
    private String tname;
    private String img;
    private String description;
    private String rplayer;
    private String time;
    private String genre;
    private String type;
    private String cname;
    private String curl;
    private String island;
    private String si;
    private float difficulty;
    private String grade;
    private String activity;
    private boolean islike;
}
