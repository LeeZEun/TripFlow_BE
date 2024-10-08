package com.ddabong.tripflow.chatbot.service;

import com.ddabong.tripflow.chatbot.dao.IChatLogRepository;
import com.ddabong.tripflow.chatbot.dto.UpdateUserStateDTO;
import com.ddabong.tripflow.chatbot.dto.UserStateDTO;
import com.ddabong.tripflow.chatbot.model.ChatLog;
import com.ddabong.tripflow.chatbot.model.ChatLogMapping;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatLogService implements IChatLogService {
    @Autowired
    private IChatLogRepository iChatLogRepository;

    @Override
    public void save(ChatLog chatLog) {
        iChatLogRepository.save(chatLog);
    }

    @Override
    public void initState(UserStateDTO userStateDTO) {
        ChatLog chatLog = new ChatLog();
        ChatLogMapping chatLogMapping = new ChatLogMapping();

        chatLog.setUserInput(userStateDTO.getUserInput());
        chatLog.setBotResponse(userStateDTO.getBotResponse());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        chatLog.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));

        chatLog.setDays(userStateDTO.getDays());
        chatLog.setTransport(userStateDTO.getTransport());
        chatLog.setCompanion(userStateDTO.getCompanion());
        chatLog.setTheme(userStateDTO.getTheme());
        chatLog.setFood(userStateDTO.getFood());

        chatLog.setAge(userStateDTO.getAge());
        chatLog.setToken(userStateDTO.getToken());
        chatLog.setPastChatId(userStateDTO.getPastChatId());
        chatLog.setStartTime(userStateDTO.getStartTime());

        chatLog.setScheduler(userStateDTO.getScheduler());
        chatLog.setFoodsContext(userStateDTO.getFoodsContext());
        chatLog.setPlayingContext(userStateDTO.getPlayingContext());
        chatLog.setHotelContext(userStateDTO.getHotelContext());
        chatLog.setExplain(userStateDTO.getExplain());
        chatLog.setSecondSentence(userStateDTO.getSecondSentence());
        chatLog.setIsValid(userStateDTO.getIsValid());

        iChatLogRepository.initState(chatLog);
        Long curChatLogId = chatLog.getChatLogId();
        System.out.println("저장된 ID : " + curChatLogId);

        System.out.println("채팅로그 매핑");
        chatLogMapping.setMemberId(userStateDTO.getToken());
        chatLogMapping.setStartChatId(curChatLogId);
        chatLogMapping.setLastChatId(curChatLogId);
        chatLogMapping.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));
        chatLogMapping.setLastModifiedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));
        iChatLogRepository.initLastChatMapping(chatLogMapping);
    }

    @Override
    public UserStateDTO setUserState(Long memberId) {
        UserStateDTO userStateDTO = new UserStateDTO("", "", null,null,null,null,null,
                0,0L,0L, null,
                null,null,null,null,null,null,0);
        ChatLog chatLog = iChatLogRepository.setUserState(memberId);

        System.out.println("chat log 조회 : " + chatLog);
        System.out.println("chat_log id : " + chatLog.getChatLogId());


        if (chatLog != null) {
            if(chatLog.getDays() != null){
                System.out.println("DAYS 업데이트");
                userStateDTO.setDays(chatLog.getDays());
            }
            if(chatLog.getTransport() != null){
                System.out.println("대중교통 업데이트");
                userStateDTO.setTransport(chatLog.getTransport());
            }
            if(chatLog.getCompanion() != null){
                System.out.println("동행자 업데이트");
                userStateDTO.setCompanion(chatLog.getCompanion());
            }
            if(chatLog.getTheme() != null){
                System.out.println("테마 업데이트");
                userStateDTO.setTheme(chatLog.getTheme());
            }
            if(chatLog.getFood() != null){
                System.out.println("음식 업데이트");
                userStateDTO.setFood(chatLog.getFood());
            }
            userStateDTO.setAge(chatLog.getAge());
            userStateDTO.setToken(chatLog.getToken());
            userStateDTO.setPastChatId(chatLog.getChatLogId()); // 이전 채팅 id 저장
            userStateDTO.setStartTime(chatLog.getStartTime());
            if(chatLog.getScheduler() != null){
                System.out.println("일정 업데이트");
                userStateDTO.setScheduler(chatLog.getScheduler());
            }
            if(chatLog.getFoodsContext() != null){
                System.out.println("식당 업데이트");
                userStateDTO.setFoodsContext(chatLog.getFoodsContext());
            }
            if(chatLog.getPlayingContext() != null){
                System.out.println("관광지 업데이트");
                userStateDTO.setPlayingContext(chatLog.getPlayingContext());
            }
            if(chatLog.getHotelContext() != null){
                System.out.println("숙박 업데이트");
                userStateDTO.setHotelContext(chatLog.getHotelContext());
            }
            if(chatLog.getExplain() != null){
                System.out.println("설명 업데이트");
                userStateDTO.setExplain(chatLog.getExplain());
            }
            if(chatLog.getSecondSentence() != null){
                System.out.println("만족도 업데이트");
                userStateDTO.setSecondSentence(chatLog.getSecondSentence());
            }
        } else {
            // chatLog가 null일 경우 기본값을 설정하거나 적절한 처리를 수행합니다.
            System.out.println("chatLog is null");
        }

        return userStateDTO;
    }

    @Override
    public void updateState(UserStateDTO userStateDTO) {
        ChatLog chatLog = new ChatLog();
        ChatLogMapping chatLogMapping = new ChatLogMapping();

        chatLog.setUserInput(userStateDTO.getUserInput());
        chatLog.setBotResponse(userStateDTO.getBotResponse());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        chatLog.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));

        chatLog.setDays(userStateDTO.getDays());
        chatLog.setTransport(userStateDTO.getTransport());
        chatLog.setCompanion(userStateDTO.getCompanion());
        chatLog.setTheme(userStateDTO.getTheme());
        chatLog.setFood(userStateDTO.getFood());

        chatLog.setAge(userStateDTO.getAge());
        chatLog.setToken(userStateDTO.getToken());

        Long pastChatId = iChatLogRepository.findPastChatIdByMemberId(userStateDTO.getToken());
        Long chatMappingId = iChatLogRepository.findChatLogMappingIdByMemberId(userStateDTO.getToken());
        chatLog.setPastChatId(pastChatId);
        String startTime = iChatLogRepository.getStartTimeByPastChatId(pastChatId);
        System.out.println("pastChatId : " + pastChatId);
        System.out.println("start time : " + startTime);
        chatLog.setStartTime(startTime);

        chatLog.setScheduler(userStateDTO.getScheduler());
        chatLog.setFoodsContext(userStateDTO.getFoodsContext());
        chatLog.setPlayingContext(userStateDTO.getPlayingContext());
        chatLog.setHotelContext(userStateDTO.getHotelContext());
        chatLog.setExplain(userStateDTO.getExplain());
        chatLog.setSecondSentence(userStateDTO.getSecondSentence());
        chatLog.setIsValid(userStateDTO.getIsValid());

        iChatLogRepository.updateState(chatLog);
        Long curChatLogId = chatLog.getChatLogId();
        System.out.println("저장된 ID : " + curChatLogId);

        System.out.println("채팅로그 매핑");
        chatLogMapping.setChatLogMapId(chatMappingId);
        chatLogMapping.setLastChatId(curChatLogId);
        chatLogMapping.setLastModifiedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));
        iChatLogRepository.updateLastChatMapping(chatLogMapping);


    }

    @Override
    public Long getChatLogId(Long memberId) {
        return iChatLogRepository.findChatLogMappingIdByMemberId(memberId);
    }

    @Override
    public UpdateUserStateDTO setUpdateUserState(Long travelId) {
        UpdateUserStateDTO updateUserStateDTO = new UpdateUserStateDTO("", "", null,null,null,null,null,
                0,0L,0L, null,
                null,null,null,null,null,null,0, null);
        ChatLog chatLog = iChatLogRepository.setUpdateUserState(travelId);

        System.out.println("chat log 조회 : " + chatLog);
        System.out.println("chat_log id : " + chatLog.getChatLogId());


        if (chatLog != null) {
            if(chatLog.getDays() != null){
                System.out.println("DAYS 업데이트");
                updateUserStateDTO.setDays(chatLog.getDays());
            }
            if(chatLog.getTransport() != null){
                System.out.println("대중교통 업데이트");
                updateUserStateDTO.setTransport(chatLog.getTransport());
            }
            if(chatLog.getCompanion() != null){
                System.out.println("동행자 업데이트");
                updateUserStateDTO.setCompanion(chatLog.getCompanion());
            }
            if(chatLog.getTheme() != null){
                System.out.println("테마 업데이트");
                updateUserStateDTO.setTheme(chatLog.getTheme());
            }
            if(chatLog.getFood() != null){
                System.out.println("음식 업데이트");
                updateUserStateDTO.setFood(chatLog.getFood());
            }
            updateUserStateDTO.setAge(chatLog.getAge());
            updateUserStateDTO.setToken(chatLog.getToken());
            updateUserStateDTO.setPastChatId(chatLog.getChatLogId()); // 이전 채팅 id 저장
            updateUserStateDTO.setStartTime(chatLog.getStartTime());
            if(chatLog.getScheduler() != null){
                System.out.println("일정 업데이트");
                updateUserStateDTO.setScheduler(chatLog.getScheduler());
            }
            if(chatLog.getFoodsContext() != null){
                System.out.println("식당 업데이트");
                updateUserStateDTO.setFoodsContext(chatLog.getFoodsContext());
            }
            if(chatLog.getPlayingContext() != null){
                System.out.println("관광지 업데이트");
                updateUserStateDTO.setPlayingContext(chatLog.getPlayingContext());
            }
            if(chatLog.getHotelContext() != null){
                System.out.println("숙박 업데이트");
                updateUserStateDTO.setHotelContext(chatLog.getHotelContext());
            }
            if(chatLog.getExplain() != null){
                System.out.println("설명 업데이트");
                updateUserStateDTO.setExplain(chatLog.getExplain());
            }
            if(chatLog.getSecondSentence() != null){
                System.out.println("만족도 업데이트");
                updateUserStateDTO.setSecondSentence(chatLog.getSecondSentence());
            }
        } else {
            // chatLog가 null일 경우 기본값을 설정하거나 적절한 처리를 수행합니다.
            System.out.println("chatLog is null");
        }

        return updateUserStateDTO;
    }
}
