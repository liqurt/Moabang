package com.self.roomescape.entity;

import lombok.*;

import javax.persistence.*;


@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_like")
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ulid")
    private int ulid;

    @ManyToOne(targetEntity = User.class)

    private User user;

    @ManyToOne(targetEntity = Theme.class)

    private Theme theme;
}
