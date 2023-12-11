package com.inhatc.portfolio.dto;

import com.inhatc.portfolio.entity.Board;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardWriteRequestDto {
    private Long id;
    private String title;
    private String Description;
    private String content;
    private String email; // 로그인한 사용자의 이메일
    private List<MultipartFile> imageFiles;
    private List<MultipartFile> projectFiles;
    private static ModelMapper modelMapper=new ModelMapper();
    public static Board BoardDtoToEntity(BoardWriteRequestDto writeRequestDto){
        return modelMapper.map(writeRequestDto,Board.class);
    }
}
