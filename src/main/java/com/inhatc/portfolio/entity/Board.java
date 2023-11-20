package com.inhatc.portfolio.entity;

import com.inhatc.portfolio.dto.BoardDto;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String  comments;

    private static ModelMapper modelMapper=new ModelMapper();

    public static Board dtoToentity(BoardDto boardDto){
        return modelMapper.map(boardDto,Board.class);
    }
}
