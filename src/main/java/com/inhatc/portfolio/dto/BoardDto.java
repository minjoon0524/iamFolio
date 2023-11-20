package com.inhatc.portfolio.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private String title;
    private String description;
    private String  comments;
}
