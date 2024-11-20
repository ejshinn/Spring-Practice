# Spring-Practice

### 1. board1
- **프로젝트 생성**: Spring MVC 게시판 프로젝트 생성
- **환경 설정**:
  - 종속성 추가
  - 데이터베이스 설정 및 클래스 추가
  - SQL 쿼리 XML 파일 추가
- **기능 구현**:
  - Controller, Service, Mapper 파일 추가
  - 목록 페이지, 글쓰기 페이지, 글 상세보기 페이지 구현
  - 글 수정 및 삭제 기능 구현
  - 첨부파일 기능 추가 (업로드, 다운로드)
- **Thymeleaf 문법**:
  - 변수, 선택 변수, 리소스 번들, 링크 주소, 문자열, 숫자, boolean 출력
  - 산술, 비교, 삼항 연산자 사용
  - if, unless, switch ~ case, foreach 사용
  - HTML 속성 데이터 추가 수정
  - fragment, insert, replace, include 사용
- **기능 개선**:
  - BoardFileDTO 및 FileUtils 클래스 추가
  - BoardMapper, BoardController, BoardService, sql-board.xml 수정
  - 게시글 상세보기 페이지에 업로드된 파일 목록 및 다운로드 기능 추가
  - BoardDTO 에 파일 목록을 위한 List fileList 필드 추가
  - controller 에 다운로드를 위한 메서드 추가
  - Service에 다운로드를 위한 selectBoardFileInfo() 메서드 추가 및 구현
  - 게시글 상세보기를 위한 selectBoardDetail() 메서드에 조회수 업데이트 기능 추가 및 첨부파일 목록 기능 추가
  - Mapper에 첨부파일 목록을 위한 selectBoardFileList() 메서드 추가
  - 다운로드를 위한 selectBoardFileInfo() 메서드 추가
  - XML에 selectBoardFileList와 selectBoardFileInfo를 위한 SQL 쿼리문 추가


### 2. ajaxTest
- **Ajax 사용**:
  - Ajax와 비Ajax 비교
  - select box 정보 변경
  - Ajax를 사용한 문제 2개 구현


### 3. board2
- **로그인 처리**:
  - Spring MVC 게시판 프로젝트 생성 및 DB 설정
  - Board 관련 Controller, DTO, Service, Mapper, View 생성
  - LoginController, UserService, UserMapper, Login View 생성
  - Interceptor 사용 및 로그인 체크 추가
  - WebMVCConfiguration 추가


### 4. board3
- **REST 방식 사용하기**:
  - 프로젝트 생성 및 DB 설정
  - service, mapper, xml, View 파일 작성
  - REST 방식에 맞게 Controller 작성
  - REST API 추가
- **페이징**:
  - PageHelper 라이브러리 추가
  - 페이징 기능을 위한 Controller, Service, Mapper, View 추가
  - 페이지 이동 버튼 생성


### 5. xml_json_parser
- **XML 파싱**:
  - 공공데이터 포털의 전국 약국 정보 서비스 데이터 파싱, DTO 클래스 생성
  - JAXB 라이브러리 추가 및 관련 파일 생성
  - js를 통한 AJAX 통신 및 화면 구현
- **JSON 파싱**:
  - 영화진흥원의 일일박스오피스 데이터 파싱, DTO 클래스 생성
  - Gson 라이브러리 추가 및 관련 파일 생성
  - AJAX 통신 및 화면 구현


### 6. GsonTest
- **Gson 사용하기**: Gson 라이브러리 사용 및 테스트


### 7. outsideDir
- **외부 폴더 등록**: 외부 폴더를 Spring Framework의 내부 폴더로 등록하기


### 8. board4
- **JPA를 사용한 게시판**: JPA를 사용하여 게시판 구현


### 9. JPATest
- **JPA 기능 테스트**:
  - 테이블 생성 및 관계 설정
  - 쿼리 메서드 및 @Query 사용하여 쿼리 작성


### 10. Sechedule
- **스케줄링**: 주기적으로 실행되는 작업을 위한 스케줄링 구현


### 11. securityTest
- **Spring Security**: Spring Security를 활용한 회원가입 및 로그인 기능 구현


### 12. security
- **Spring Security**: Spring Security를 활용한 회원가입, 로그인 및 로그아웃, 게시물 관리 기능 구현


### 13. awsweb
- **서버 업로드**: AWS EC2 서버에 스프링 프로젝트 업로드하기, 필더 패턴
