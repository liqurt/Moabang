package com.self.roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coid;
    private String content;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @CreationTimestamp
    private Timestamp regDate;
    private long uid;
    private long communityId;
}
