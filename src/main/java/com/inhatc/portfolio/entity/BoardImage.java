package com.inhatc.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "boardImage")
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_img_id")
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name; // 파일 이름을 저장하는 필드 추가

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
