package com.inhatc.portfolio.entity;

import com.inhatc.portfolio.common.entity.BaseEntity;
import com.inhatc.portfolio.dto.BoardResponseDto;
import com.inhatc.portfolio.dto.BoardWriteRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String Description;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String email;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private static ModelMapper modelMapper=new ModelMapper();
    public static BoardWriteRequestDto BoardEntityToDto(Board board){
        return modelMapper.map(board,BoardWriteRequestDto .class);
    }

    public static BoardResponseDto BoardDetailEntityToDto(Board board){
        return modelMapper.map(board,BoardResponseDto .class);
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<BoardImage> boardImages;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<ProjectFile> projectFiles;

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", boardImages=" + boardImages.stream()
                .map(boardImage -> boardImage.getId()) // BoardImage의 id만 출력
                .collect(Collectors.toList()) +
                '}';
    }

}
