package bitc.fullstack405.securitytest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 사용자 요청을 처리하는 컨트롤러
@Controller
public class PageController {

    // 모두 접속 가능
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 모두 접속 가능
    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    // 인증받은 사용자만 접속 가능한 페이지
    @GetMapping("/member")
    public String memberPage() {
        return "member/profile";
    }

    // 관리자로 인증받은 사용자만 접속 가능한 페이지
    @GetMapping("/admin")
    public String adminPage() {
        return "admin/manager";
    }
}