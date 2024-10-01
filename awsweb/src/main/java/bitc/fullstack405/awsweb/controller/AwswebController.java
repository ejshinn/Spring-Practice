package bitc.fullstack405.awsweb.controller;

import bitc.fullstack405.awsweb.dto.PhoneBookDTO;
import bitc.fullstack405.awsweb.service.PhoneBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// @RequiredArgsConstructor : 롬복 라이브러리에서 제공하는 애너테이션으로 @Autowired 대신 사용하는 애너테이션
@RequiredArgsConstructor
@Controller
public class AwswebController {

    // @RequiredArgsConstructor를 사용하여 만들어진 객체는 반드시 final 키워드를 사용해야 함
    private final PhoneBookService phoneBookService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/boardList")
    public ModelAndView boardList() throws Exception {
        ModelAndView mav = new ModelAndView("board/boardList");

        List<PhoneBookDTO> dataList = phoneBookService.getPhoneBookList();

        mav.addObject("phoneBookList", dataList);

        return mav;
    }
}