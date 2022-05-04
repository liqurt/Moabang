package response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ThemeDetailResDTO {
    private int Tid;

    private int Cid;

    private String Tname;

    private String Img;

    private String Description;

    private String Rplayer;

    private String Time;

    private String Genre;

    private String Type;

    private String Cname;

    private String Curl;

    private String Island;

    private String Si;

    private float Difficulty;

    private float Grade;

    private String Activity;
}
