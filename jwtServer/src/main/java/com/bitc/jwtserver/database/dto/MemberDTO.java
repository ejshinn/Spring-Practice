package com.bitc.jwtserver.database.dto;

import com.bitc.jwtserver.database.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 클라이언트와 데이터를 주고 받기 위해서 생성한 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String userId;
    private String userPw;
    private String userNick;
    private String userEmail;
    private Role role;
}