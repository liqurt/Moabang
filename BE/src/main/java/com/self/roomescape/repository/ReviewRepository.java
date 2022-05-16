package com.self.roomescape.repository;

import com.self.roomescape.entity.Review;
import com.self.roomescape.repository.mapping.MyPageReviewListMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByTid(int tid);

    Optional<Review> findByRid(long rid);

    List<Review> findAllByUserInfo_Uid(long uid);

    void deleteByRid(long rid);

    @Query("select r.rid as rid, u.uid as uid,t.tid as tid,t.tname as tname,c.cname as cname,t.img as img,r.rating as rating" +
            ",r.isSuccess as isSuccess,r.hint as hint, r.clearTime as clearTime,r.player as player, r.recPlayer as recPlayer, " +
            "r.active as active, r.chaegamDif as chaegamDif, r.playDate as playDate,r.regDate as regDate,r.content as content  " +
            "from Review r left join Theme t on r.tid=t.tid " +
            "left join User u on u.uid=r.userInfo.uid " +
            "left join t.cafe c on c.cid=t.cafe.cid " +
            "where r.userInfo.uid=:uid  ")
    List<MyPageReviewListMapping> findByReviewData(@Param("uid") long uid);
}
