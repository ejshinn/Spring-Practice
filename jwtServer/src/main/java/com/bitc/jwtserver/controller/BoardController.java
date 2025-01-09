package com.bitc.jwtserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    // 게시판 목록
    @GetMapping({"", "/"})
    public ResponseEntity<?> boardList() {
    return ResponseEntity.ok("board list");
  }

    // 게시판 글 상세보기
    @GetMapping("/{boardIdx}")
    public ResponseEntity<?> boardDetail(@PathVariable int boardIdx) {
        return ResponseEntity.ok("board detail " + boardIdx);
    }

    // 게시판 글 등록
    @PostMapping("/")
    public ResponseEntity<?> boardWrite() {
    return ResponseEntity.ok("board write");
  }

    // 게시판 글 수정
    @PutMapping("/{boardIdx}")
    public ResponseEntity<?> boardUpdate(@PathVariable int boardIdx) {
        return ResponseEntity.ok("board update " + boardIdx);
    }

    // 게시판 글 삭제
    @DeleteMapping("/{boardIdx}")
    public ResponseEntity<?> boardDelete(@PathVariable int boardIdx) {
        return ResponseEntity.ok("board delete " + boardIdx);
    }
}