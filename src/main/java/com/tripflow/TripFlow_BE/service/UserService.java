package com.tripflow.TripFlow_BE.service;

import com.tripflow.TripFlow_BE.dto.join.JoinResponseDto;
import com.tripflow.TripFlow_BE.dto.login.LoginResponseDto;
import com.tripflow.TripFlow_BE.dto.ResponseDataDto;
import com.tripflow.TripFlow_BE.dto.join.JoinRequestDto;
import com.tripflow.TripFlow_BE.dto.login.LoginRequestDto;
import com.tripflow.TripFlow_BE.entity.UserInfo;
import com.tripflow.TripFlow_BE.repository.UserRepository;
import com.tripflow.TripFlow_BE.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    //회원가입 확인
    public ResponseDataDto<JoinResponseDto> join(JoinRequestDto users) {
        Optional<UserInfo> optNickname = userRepository.findByNickname(users.getNickname());
        Optional<UserInfo> optUserid = userRepository.findByUserid(users.getUserid());
        Optional<UserInfo> optEmail = userRepository.findByEmail(users.getEmail());
        Optional<UserInfo> optPhonenumber = userRepository.findByPhonenumber(users.getPhonenumber());

        System.out.println(users.getUsername());

        if(optNickname.isEmpty() && optUserid.isEmpty() && optEmail.isEmpty() && optPhonenumber.isEmpty()){ //같은 게 없으면 (회원가입 성공)

            //초기 설정
            UserInfo userEntity = users.toEntity();
            System.out.println(userEntity.getUsername());
            System.out.println(userEntity.getNickname());
            userEntity.setCreatedtime(LocalDateTime.now()); //가입 일시
//            userEntity.setLogin_num(0L); //로그인 횟수
            userRepository.save(userEntity);
            return new ResponseDataDto("Signup Success", 200, new JoinResponseDto(optUserid.get().getUsername(), optUserid.get().getNickname(), optUserid.get().getUserid(), optUserid.get().getEmail(), optUserid.get().getPhonenumber(), optUserid.get().getBirth(), optUserid.get().getCreatedtime()));
        }
        else { //회원가입 실패
            return new ResponseDataDto("Signup Failed", 406, null);
        }
    }

    //로그인 확인
    public ResponseDataDto<LoginResponseDto> login(LoginRequestDto users) {

        Optional<UserInfo> optUserid = userRepository.findByUserid(users.getUserid());

        if(optUserid.isPresent()){ //아이디가 있으면
            UserInfo userInfo = optUserid.get();

            //데베에 저장된 비번과 입력받은 비번 비교
            if (userInfo.getPassword().equals(users.getPassword())) { //로그인 성공
                final String jwttoken = tokenProvider.create(optUserid.get()); //토큰 생성

                System.out.println(userInfo);
//                userInfo.setLogin_num(loginNumber);
//                userInfo.setRecent_access(LocalDateTime.now());
                System.out.println(optUserid);

//                userRepository.save(userInfo);
                return new ResponseDataDto("Login Success", 200, new LoginResponseDto(optUserid.get().getUsername(), optUserid.get().getNickname(), optUserid.get().getUserid(), optUserid.get().getEmail(), optUserid.get().getPhonenumber(), optUserid.get().getBirth(), optUserid.get().getCreatedtime(), jwttoken));
            }
            else {
                return new ResponseDataDto("Info is wrong", 406, null);
            }
        }
        else {
            return new ResponseDataDto("Email is not found", 406, null);
        }
    }
}
