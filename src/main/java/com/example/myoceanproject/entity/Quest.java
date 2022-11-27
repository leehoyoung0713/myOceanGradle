package com.example.myoceanproject.entity;

import com.example.myoceanproject.embeddable.File;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_QUEST")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quest extends Period{
    @Id
    @GeneratedValue
    private long questId; //PK
    @NotNull
    private String questCategory;
    @NotNull
    private String questName;
    @NotNull
    private String questContent;
    private LocalDateTime questDeadLine;
    @Embedded
    @NotNull
    private File file;

    @Builder
    public Quest(String questCategory, String questName, String questContent, LocalDateTime questDeadLine,File file/*, String filePath, String fileOriginName*/) {
        this.questCategory = questCategory;
        this.questName = questName;
        this.questContent = questContent;
        this.questDeadLine = questDeadLine;
//        this.file.setFilePath(filePath);
//        this.file.setFileOriginName(fileOriginName);
        this.file = file;
    }
    public void update(String questCategory, String questName, String questContent, LocalDateTime questDeadLine) {
        this.questCategory = questCategory;
        this.questName = questName;
        this.questContent = questContent;
        this.questDeadLine = questDeadLine;
    }



}
