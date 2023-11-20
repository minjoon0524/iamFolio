package com.inhatc.portfolio.controller;

import com.inhatc.portfolio.dto.BoardDto;
import com.inhatc.portfolio.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board/save")
    public String saveForm(){
        return "board/board";
    }

    @PostMapping("board/save")
    public void save(@ModelAttribute BoardDto boardDto){
    boardService.save(boardDto);
    }
}
