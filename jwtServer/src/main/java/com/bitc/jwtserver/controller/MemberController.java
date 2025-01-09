package com.bitc.jwtserver.controller;

import com.bitc.jwtserver.database.dto.MemberDTO;
import com.bitc.jwtserver.database.dto.ResponseDTO;
import com.bitc.jwtserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// 회원가입 및 로그인을 위한 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class MemberController {

    // 회원에 관련된 서비스 객체
    private final MemberService memberService;

    // 로그인, 매개변수로 userId/userPw를 받아서 사용
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userId, @RequestParam String userPw) {

        // 예외처리를 이용하여 사용자 인증을 안전하게 실행
        try {
            // 사용자 인정 정보 및 jwt 토큰 정보를 가져오기
            // ResponseDTO에는 jwt를 통해서 생성된 엑세스 토큰과 리프레시 토큰이 저장되어 있음
            ResponseDTO jwtToken = memberService.getJwtAuthenticate(userId, userPw);
        
            // 가져온 jwt 토큰 정보를 클라이언트로 전달
            return ResponseEntity.ok().body(jwtToken);
        }
        // 사용자 인증 실패 시 401 오류 및 메시지 출력
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    // 회원가입, 클라이언트와의 데이터 전달을 위해 MemberDTO 클래스를 사용
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO member) {

    // 예외처리를 통해서 안전하게 회원가입 실행
        try {
            // 회원가입 성공 시 성공 메시지 출력
            String resData = memberService.signupMember(member);
    
            // 클라이언트에게 200 성공 신호와 성공 메시지를 전달
            return ResponseEntity.ok().body(resData);
        }
        // 회원가입 실패 시 오류 메시지 출력
        catch (IllegalArgumentException e) {
            String resData = "회원 가입 실패\n" + e.getMessage();
            return ResponseEntity.badRequest().body(resData);
        }
    }

    // 클라이언트가 가지고 있는 엑세스 토큰이 만료되었을 경우 리프레시 토큰을 사용하여 새로운 엑세스 토큰을 발행
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        // 리프레시 토큰을 매개변수로 사용하여 새로운 엑세스 토큰 발급
        ResponseDTO newAccessToken = memberService.refreshAccessToken(refreshToken);

        // 발급 받은 엑세스 토큰을 클라이언트에게 전달
        return ResponseEntity.ok(newAccessToken);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
    return ResponseEntity.ok("logout");
  }
}