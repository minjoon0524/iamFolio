package com.inhatc.portfolio.service;

import com.inhatc.portfolio.dto.BoardDto;
import com.inhatc.portfolio.entity.Board;
import com.inhatc.portfolio.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDto boardDto) {
        Board boardEntity = Board.dtoToentity(boardDto);
        boardRepository.save(boardEntity);

    }
}
