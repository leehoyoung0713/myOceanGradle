package com.example.myoceanproject.dtoTest;

import com.example.myoceanproject.domain.AlarmDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.Alarm;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.AlarmRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.type.ReadStatus;
import com.example.myoceanproject.type.UserAccountStatus;
import com.example.myoceanproject.type.UserLoginMethod;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import static com.example.myoceanproject.entity.QAlarm.alarm;
import static com.example.myoceanproject.entity.QUser.user;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class AlarmTest {
    @Autowired
    private AlarmDTO alarmDTO;
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Test
    public void saveAlarmTest(){

//      1번 유저 불러오기
        Optional<User> user = userRepository.findById(1L);
        AlarmDTO alarmDTO = new AlarmDTO();

//      alarmDTO에 필요한 값 저장
        alarmDTO.setAlarmContent("두번째 알람입니다.");
        alarmDTO.setReadStatus(ReadStatus.UNREAD);

//      alarmDTO에 저장한 값들을 entity로 변환
        Alarm alarm1 = alarmDTO.toEntity();

//      alarmDTO에 처음에 조회했던 유저 정보를 저장(optional이기 때문에 get 사용)
//      changeUser 메소드로 alarmDTO에 저장된 User값을 alarm1로 전달
        alarmDTO.setUser(user.get());
        alarm1.changeUser(alarmDTO.getUser());

//      alarm 엔티티에 해당 값들을 모두 저장
        alarmRepository.save(alarm1);
    }

//    @Test
//    public void findAllTest(){
//        List<Alarm> alarms = jpaQueryFactory.selectFrom(alarm)
//                .join(alarm.user)
//                .fetchJoin()
//                .fetch();
//        alarms.stream().map(Alarm::toString).forEach(log::info);
//
//
//    }

//    @Test
//    public void updateTest(){
//
//    }

}
