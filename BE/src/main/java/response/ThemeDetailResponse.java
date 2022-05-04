package response;

import com.self.roomescape.entity.Review;
import com.self.roomescape.entity.Theme;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ThemeDetailResponse {
    private Theme theme;
    private List<Review> reviewList;
}
