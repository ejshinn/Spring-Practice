package bitc.fullstack405.awsweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @Builder : 빌더 패턴을 사용할 수 있도록 도와주는 애너테이션
@Builder
@Getter
// @AllArgsConstructor : 클래스의 필드에 대한 모든 값을 입력할 수 있는 생성자를 자동으로 생성하는 애너테이션
@AllArgsConstructor
// @NoArgsConstructor : 기본 생성자를 자동으로 생성하는 애너테이션
@NoArgsConstructor
public class PhoneBookDTO {
    private String name;
    private String phone;
    private String email;

//    @AllArgsConstructor 사용 시 자동으로 생성
//    public PhoneBookDTO(String name, String phone, String email) {
//        this.name = name;
//        this.phone = phone;
//        this.email = email;
//    }

    // 빌더 패턴 사용 안 할 경우 필요한 생성자를 만들어줘야 함
    public PhoneBookDTO(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
