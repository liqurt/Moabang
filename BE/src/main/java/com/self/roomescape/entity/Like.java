package com.self.roomescape.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "userLike")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ulid;

    private long userUid;

    private int themeTid;
}
