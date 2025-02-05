package com.inhatc.portfolio.controller;

import com.inhatc.portfolio.dto.BoardImageUploadDto;
import com.inhatc.portfolio.dto.BoardResponseDto;
import com.inhatc.portfolio.dto.BoardWriteRequestDto;
import com.inhatc.portfolio.entity.Board;
import com.inhatc.portfolio.entity.BoardImage;
import com.inhatc.portfolio.entity.Member;
import com.inhatc.portfolio.entity.ProjectFile;
import com.inhatc.portfolio.service.BoardService;
import com.inhatc.portfolio.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("writeForm",new BoardWriteRequestDto());
        return "board/write";
    }

    @PostMapping("/write")
    public String write(BoardWriteRequestDto writeRequestDto, BindingResult bindingResult, @ModelAttribute BoardImageUploadDto boardImageUploadDTO){
        if(bindingResult.hasErrors()){
            return "board/write";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();

            writeRequestDto.setEmail(email);

            // 서비스를 통해 로그인한 사용자의 Member 엔티티를 찾습니다.
            Member member = memberService.findByEmail(email);

            Board board = BoardWriteRequestDto.BoardDtoToEntity(writeRequestDto);
            board.setMember(member);

            boardService.saveBoard(board);

            // 이미지 파일 업로드 처리
            List<MultipartFile> imageFiles = writeRequestDto.getImageFiles();
            if (imageFiles != null && !imageFiles.isEmpty()) {
                for(MultipartFile file : imageFiles) {
                    String savePath = "C:\\Spring_WS\\portfolio\\src\\main\\resources\\static\\saveImg";
                    String savedFilename = boardService.saveFile(file, savePath);

                    // BoardImage 엔티티 생성 및 저장
                    BoardImage boardImage = BoardImage.builder()
                            .url(savePath + "\\" + savedFilename)
                            .name(file.getOriginalFilename()) // 파일 이름 설정
                            .board(board)
                            .build();
                    boardService.saveBoardImage(boardImage);
                }
            }

            // 프로젝트 파일 업로드 처리
            List<MultipartFile> projectFiles = writeRequestDto.getProjectFiles();
            if (projectFiles != null && !projectFiles.isEmpty()) {
                for(MultipartFile file : projectFiles) {
                    String savePath = "C:\\Spring_WS\\portfolio\\src\\main\\resources\\static\\pfiles";
                    String savedFilename = boardService.saveFile(file, savePath);

                    // ProjectFile 엔티티 생성 및 저장
                    ProjectFile projectFile = ProjectFile.builder()
                            .url(savePath + "\\" + savedFilename)
                            .name(file.getOriginalFilename()) // 파일 이름 설정
                            .board(board)
                            .build();
                    boardService.saveProjectFile(projectFile);
                }
            }
        }

        return "redirect:/";
    }


    // 상세조회
    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable Long id,Model model){
        BoardResponseDto boardResponseDto=boardService.boardFindById(id);
        model.addAttribute("board",boardResponseDto);
        System.out.println(boardResponseDto);
        return "board/detail";
    }



    @GetMapping("/board/list")
    public String Home(Model model) {
        List<BoardResponseDto> dto = boardService.boardList();
        model.addAttribute("boardList", dto);

        return "board/home";
    }

    @GetMapping("/board/update/{id}")
    public String boardUpdateForm(@PathVariable Long id, Model model) {
        BoardResponseDto result = boardService.getBoard(id);
        model.addAttribute("dto", result);
        return "board/update";
    }

    @PostMapping("/board/update")
    public String boardUpdate(@ModelAttribute("dto") BoardWriteRequestDto writeRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/update";
        }
        boardService.updateBoard(writeRequestDto.getId(), writeRequestDto);
        return "redirect:/board/" + writeRequestDto.getId();
    }

    @GetMapping("/board/delete/{id}")
    public String boardDelete(@PathVariable Long id){
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/downloadImage/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        BoardImage boardImage = boardService.getBoardImageById(id);
        String filePath = boardImage.getUrl();
        byte[] data = boardService.loadFileAsBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + boardImage.getName());

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("/downloadProjectFile/{id}")
    public ResponseEntity<byte[]> downloadProjectFile(@PathVariable Long id) {
        ProjectFile projectFile = boardService.getProjectFileById(id);
        String filePath = projectFile.getUrl();
        byte[] data = boardService.loadFileAsBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + projectFile.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("/board/keyword")
    public String Home(@RequestParam(name = "keyword", defaultValue = "") String keyword, Model model) {
        List<BoardResponseDto> searchList = boardService.getSearchList(keyword);
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);  // 검색어를 템플릿에 전달
        return "board/home";
    }




}




