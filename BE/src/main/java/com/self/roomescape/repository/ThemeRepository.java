package com.self.roomescape.repository;

import com.self.roomescape.entity.Theme;
import com.self.roomescape.repository.mapping.ThemeListMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> { // ,c.url as curl,c.cname as cname
    List<Theme> findByCid(int cid);
//    List<Theme> findAllBy();

    @Query(value = "select t.tid as tid,t.cid as cid, t.tname as tname , t.img as img ,t.description as description,t.difficulty as difficulty,t.rplayer as rplayer, t.time as time, t.genre as genre, t.type as type,t.activity as activity,t.grade as grade,c.cname as cname,c.url as curl from  Theme t left join Cafe c on c.cid=t.cid")
    List<ThemeListMapping> findThemeAndCafe();
}