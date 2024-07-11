package com.tripflow.TripFlow_BE.dto.join;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class JoinResponseDto {
    private String username; //사용자이름
    private String nickname; //닉네임
    private String userid; //아이디
    private String email; //이메일
    private String phonenumber; //전화번호
    private String birth; //생년월일 (YYYY-MM-DD)

    private LocalDateTime createdtime; //가입 일시
    private LocalDateTime recessaccess; //최근 로그인 일시
}