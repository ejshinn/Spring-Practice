package bitc.fullstack405.awsweb.service;

import bitc.fullstack405.awsweb.dto.PhoneBookDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneBookService {
    public List<PhoneBookDTO> getPhoneBookList() throws Exception {
        // PhoneBookDTO phone1 = new PhoneBookDTO("짱구", "01012345678", "shin@bitc.ac.kr");

        // 빌터 패턴을 사용하여 객체 생성
        PhoneBookDTO phone1 = PhoneBookDTO.builder()
                .name("짱구")
                .phone("01012345678")
                .email("shin@bitc.ac.kr")
                .build();

        PhoneBookDTO phone3 = new PhoneBookDTO("짱구", "01012345678");

        PhoneBookDTO phone4 = PhoneBookDTO.builder()
                .name("철수")
                .phone("0100000")
                .build();

        PhoneBookDTO phone2 = PhoneBookDTO.builder()
                .name("훈이")
                .phone("01087654321")
                .email("hun@bitc.ac.kr")
                .build();

        List<PhoneBookDTO> phoneBookList = new ArrayList<PhoneBookDTO>();
        phoneBookList.add(phone1);
        phoneBookList.add(phone2);

        return phoneBookList;
    }
}
