package com.inhatc.portfolio.dto;

import com.inhatc.portfolio.entity.BoardImage;
import com.inhatc.portfolio.entity.ProjectFile;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String Description;
    private String content;
    private LocalDateTime createdAt;
    private String email;
    @NotEmpty(message = "대표사진은 필수 항목 입니다.")
    private List<BoardImage> boardImages;
    @NotEmpty(message = "프로젝트파일은 필수 항목 입니다.")
    private List<ProjectFile> projectFiles;
    private List<String> imageUrls;
    private List<String> imageNames;
    
    private List<String> projectFileUrls;
    private List<String> projectFileNames;


}
