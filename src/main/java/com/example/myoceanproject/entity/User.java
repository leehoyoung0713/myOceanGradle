package com.example.myoceanproject.entity;

import com.example.myoceanproject.embeddable.File;
import com.example.myoceanproject.type.UserAccountStatus;
import com.example.myoceanproject.type.UserLoginMethod;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_USER")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Period {

    @Id
    @GeneratedValue
    private Long userId; //PK
    @NotNull
    private String userEmail;
    private String userPassword;
    @NotNull
    private String userNickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserAccountStatus userAccountStatus; //Enum으로 사용
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserLoginMethod userLoginMethod;
    @Embedded
    private File file;

    @NotNull
    private int userTotalPoint;

    //    그룹 테이블 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Group> groups;

    //    투두리스트 테이블 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ToDoList> toDoLists;

    //    다이어리 테이블 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Diary> diaries;

    //    문의사항 테이블 양방향(나의 질문)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Ask> asks;

    //    커뮤니티 포스트 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<CommunityPost> communityPosts;

    //    퀘스트 달성 테이블과 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<QuestAchievement> questAchievements;

    //    알람테이블과 양방향
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Alarm> alarms;

    @Builder
    public User(String userPassword, String userNickname, String userEmail,UserAccountStatus userAccountStatus,UserLoginMethod userLoginMethod, int userTotalPoint) {
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userAccountStatus = userAccountStatus;
        this.userLoginMethod = userLoginMethod;
        this.userTotalPoint = userTotalPoint;
    }

//  가입 후 유저 비밀번호와 닉네임만 변경이 가능하다.
    public void update(String userPassword, String userNickname, int userTotalPoint, UserAccountStatus userAccountStatus) {
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userTotalPoint = userTotalPoint;
        this.userAccountStatus = userAccountStatus;
    }

}