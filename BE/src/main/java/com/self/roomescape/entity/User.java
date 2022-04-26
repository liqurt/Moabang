package com.self.roomescape.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uid")
    private long uid;

    private String email;
    private String nickname;
    private String birthday;
    private String gender;
    private String role;
    private String password;
//    @CreationTimestamp
//    private Timestamp createDate;

    @Builder
    public User(String email, String nickname, String birthday, String gender, String role, String password) {
        this.email = email;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.role = role;
        this.password = password;
    }
}
