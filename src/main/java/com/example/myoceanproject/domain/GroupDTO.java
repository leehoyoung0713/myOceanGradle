package com.example.myoceanproject.domain;

import com.example.myoceanproject.embeddable.GroupMemberLimit;
import com.example.myoceanproject.embeddable.GroupTime;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.entity.GroupSchedule;
import com.example.myoceanproject.entity.Period;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.type.GroupLocationType;
import com.example.myoceanproject.type.GroupStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Component
@Data
@NoArgsConstructor
public class GroupDTO {

    private Long groupId;
    private Long userId;
    private String userNickName;
    private String groupName;
    private String groupCategory;
    private String groupContent;
    private int groupPoint;
    private String groupOverSea;
    private String groupLocationName;
    private String groupLocation;
    private String groupLocationDetail;
    private String groupParkingAvailable;
    private String groupMoreInformation;
    private GroupLocationType groupLocationType;
    private GroupStatus groupStatus;
    private String groupFilePath;
    private String groupFileName;

    private String groupFileUuid;
    private Long groupFileSize;

//    임베드 타입 가져옴(이렇게 가져오는 것이 맞는지는 불확실함. 생성자와 toEntity에도 추가함)
    private Integer maxMember;
    private Integer minMember;


    @QueryProjection
    public GroupDTO(Long groupId, Long userId, String userNickName, String groupName, String groupCategory, String groupContent, int groupPoint, String groupOverSea, String groupLocationName, String groupLocation, String groupLocationDetail, String groupParkingAvailable, String groupMoreInformation, GroupLocationType groupLocationType, GroupStatus groupStatus, String groupFilePath, String groupFileName, String groupFileUuid, Long groupFileSize, Integer maxMember, Integer minMember) {
        this.groupId = groupId;
        this.userId = userId;
        this.userNickName = userNickName;
        this.groupName = groupName;
        this.groupCategory = groupCategory;
        this.groupContent = groupContent;
        this.groupPoint = groupPoint;
        this.groupOverSea = groupOverSea;
        this.groupLocationName = groupLocationName;
        this.groupLocation = groupLocation;
        this.groupLocationDetail = groupLocationDetail;
        this.groupParkingAvailable = groupParkingAvailable;
        this.groupMoreInformation = groupMoreInformation;
        this.groupLocationType = groupLocationType;
        this.groupStatus = groupStatus;
        this.groupFilePath = groupFilePath;
        this.groupFileName = groupFileName;
        this.groupFileUuid = groupFileUuid;
        this.groupFileSize = groupFileSize;
        this.maxMember = maxMember;
        this.minMember = minMember;
    }

    public Group toEntity(){
        GroupMemberLimit groupMemberLimit = new GroupMemberLimit();

        groupMemberLimit.setMaxMember(maxMember);
        groupMemberLimit.setMinMember(minMember);


        return Group.builder()
                .groupName(groupName)
                .groupCategory(groupCategory)
                .groupContent(groupContent)
                .groupPoint(groupPoint)
                .groupOverSea(groupOverSea)
                .groupLocationName(groupLocationName)
                .groupLocation(groupLocation)
                .groupLocationDetail(groupLocationDetail)
                .groupParkingAvailable(groupParkingAvailable)
                .groupMoreInformation(groupMoreInformation)
                .groupLocationType(groupLocationType)
                .groupStatus(GroupStatus.WAITING)
                .groupMemberLimit(groupMemberLimit)
                .groupName(groupName)
                .groupFileName(groupFileName)
                .groupFilePath(groupFilePath)
                .groupFileUuid(groupFileUuid)
                .groupFileSize(groupFileSize)
                .build();
    }

    private Set<WebSocketSession> sessions = new HashSet<>();


    public void handleMessage(WebSocketSession session, ChattingDTO chattingDTO,
                              ObjectMapper objectMapper) throws IOException {
            sessions.add(session);

        send(chattingDTO,objectMapper);
    }

    private void send( ChattingDTO chattingDTO, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.
                writeValueAsString(chattingDTO.getChattingContent()));
        for(WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }
}