package bitc.fullstack405.security.controller;

import bitc.fullstack405.security.database.domain.Article;
import bitc.fullstack405.security.database.dto.ArticleListViewResponse;
import bitc.fullstack405.security.database.dto.ArticleViewResponse;
import bitc.fullstack405.security.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// 블로그 웹 화면을 출력하기 위한 컨트롤러 클래스
@Controller
// @RequiredArgsConstructor : 롬복 라이브러리에서 제공하는 애너테이션으로, @Autowired 대신 사용함, final 키워드를 사용한 필드를 스프링 프레임워크가 관리하도록 함
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    // 게시물 목록 보기
    @GetMapping("/articles")
    public ModelAndView getArticles() {
        ModelAndView mv = new ModelAndView("articleList");

        // stream() : 자바 8부터 제공된 컬렉션 데이터를 사용하기 위한 메서드, 리스트의 데이터를 쉽게 사용할 수 있도록 해줌
        // map() : stream() 사용 시 사용할 수 있는 반복 실행 명령으로 컬렉션의 데이터를 하나씩 꺼내어 사용 후 결과값을 마지막에 다시 새로운 컬렉션으로 반환함, 자바스크립트 ES6의 map() 함수와 개념적으로 동일한 기능을 가지고 있음, 지연 실행 방식을 사용하기 때문에 마지막에 toList()와 같은 명령을 사용해야 컬렉션을 반환함
        // forEach() : stream() 사용 시 사용할 수 있는 반복 실행 명령으로 컬렉션의 데이터를 하나씩 꺼내어 사용, 자바스크립트 ES6의 forEach()와 개념적으로 동일한 기능을 가지고 있음
        // toList() : map() 실행 후 반환된 결과값을 모아서 List 타입의 데이터로 출력하는 메서드
        // 클래스명::new : 지정한 클래스의 생성자를 호출하여 새로운 객체로 매핑하는 방식
        //     ArticleListViewResponse::new == article -> new ArticleListViewResponse(article)의 람다식 방식과 동일한 역할을 함
        List<ArticleListViewResponse> articleList = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();

        mv.addObject("articles", articleList);

        return mv;
    }

    // 게시물 상세 보기
    @GetMapping("/articles/{id}")
    public ModelAndView getArticle(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("article");

        Article article = blogService.findById(id);
        mv.addObject("article", article);

        return mv;
    }

    // 게시물 등록/수정
    @GetMapping("/new-article")
    // required = false를 사용하여 사용자가 입력한 데이터가 없는 경우에도 오류를 출력하지 않음
    public ModelAndView newArticle(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("newArticle");

        // 사용자가 입력한 데이터가 있는지 없는지 확인
        if(id == null) {
            // 사용자 입력 데이터가 없을 경우 ArticleViewResponse 클래스 타입의 객체를 클라이언트에게 전달, 기존 데이터는 없음
            mv.addObject("article", new ArticleViewResponse());
        }
        else {
            // 사용자가 입력한 데이터가 있을 경우 데이터베이스에서 해당 id로 입력된 데이터 검색
            Article article = blogService.findById(id);
            // 검색된 데이터를 ArticleViewResponse 클래스 타입의 객체로 변환하여 클라이언트에게 전달
            mv.addObject("article", new ArticleViewResponse(article));
        }

        return mv;
    }
}