package response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ThemeStatisticsReponse {
    private float r_rating;
    private int r_difficulty;
    private float r_isSuccess;
    private String r_activity;
    private float r_clearTime;
    private int r_recPlayer;
    private float r_hint;
}
