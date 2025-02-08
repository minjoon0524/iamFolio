package com.inhatc.portfolio.service;

import com.inhatc.portfolio.dto.BoardResponseDto;
import com.inhatc.portfolio.dto.BoardWriteRequestDto;
import com.inhatc.portfolio.entity.Board;
import com.inhatc.portfolio.entity.BoardImage;
import com.inhatc.portfolio.entity.Member;
import com.inhatc.portfolio.entity.ProjectFile;
import com.inhatc.portfolio.repository.BoardImageRepository;
import com.inhatc.portfolio.repository.BoardRepository;
import com.inhatc.portfolio.repository.MemberRepository;
import com.inhatc.portfolio.repository.ProjectFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final ProjectFileRepository projectFileRepository;
    public Board saveBoard(Board board){

        return boardRepository.save(board);
    }

    private void validateDuplicationMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        if(!findMember.isPresent()){
            throw new IllegalStateException("이메일이 조회되지 않습니다.");
        }

    }


    public BoardResponseDto boardFindById(Long id) {
        Optional<Board> optionalBoardEntity = boardRepository.findById(id);

        if(optionalBoardEntity.isPresent()){
            Board boardEntity = optionalBoardEntity.get();
            BoardResponseDto boardDTO=Board.BoardDetailEntityToDto(boardEntity);
            return boardDTO;
        }else{
            return null;
        }

    }
    // 전체 리스트 가져오는 로직
    public List<BoardResponseDto> boardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> boardDTOs = new ArrayList<>();


        for (Board board : boards) {
            BoardResponseDto result = BoardResponseDto.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .email(board.getEmail())
                    .createdAt(board.getCreatedAt())
                    .id(board.getId())
                    .build();
            boardDTOs.add(result);
        }

        return boardDTOs;
    }

    // 검색한 리스트를 가져오는 로직
    public List<BoardResponseDto> getSearchList(String keyword){
        //키워드가 포함 되어 있으면, List 출력
        List<Board> keywordList = boardRepository.findByTitleOrContent(keyword);
        // 받아온 데이터 DTO에 담을 준비
        List<BoardResponseDto> boardDTOs = new ArrayList<>();
        for (Board board : keywordList) {
            BoardResponseDto result = BoardResponseDto.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .email(board.getEmail())
                    .createdAt(board.getCreatedAt())
                    .id(board.getId())
                    .build();
            boardDTOs.add(result);
        }


        return boardDTOs;

    }

    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        return Board.BoardDetailEntityToDto(board);
    }

    public void updateBoard(Long id, BoardWriteRequestDto boardWriteRequestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        board.setTitle(boardWriteRequestDto.getTitle());
        board.setDescription(boardWriteRequestDto.getDescription() != null ? boardWriteRequestDto.getDescription() : "");
        board.setContent(boardWriteRequestDto.getContent());

        boardRepository.save(board);
    }

    public void boardDelete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        System.out.println("id = " + id);

        boardRepository.delete(board);
    }


    public void saveBoardImage(BoardImage boardImage) {
        boardImageRepository.save(boardImage);
    }

    public String saveFile(MultipartFile file, String savePath) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 파일 확장자
        String savedFilename = UUID.randomUUID().toString() + extension; // UUID를 이용한 새 파일명 생성
        File saveFile = new File(savePath, savedFilename);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedFilename;
    }

    public void saveProjectFile(ProjectFile projectFile) {
        projectFileRepository.save(projectFile);
    }

    public BoardImage getBoardImageById(Long id) {
        return boardImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 존재하지 않습니다. id=" + id));
    }



    public byte[] loadFileAsBytes(String path) {
        try {
            Path filePath = Paths.get(path);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + path, e);
        }
    }


    public ProjectFile getProjectFileById(Long id) {
        return projectFileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다. id=" + id));
    }
}

