package response;

import com.self.roomescape.entity.Community;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyCommuAndReviewResDTO {
    List<MyThemeDetailResDTO> myThemeDetailResDTO;
    List<Community> communityList;
}
