package com.application.learnenglish.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCateDTO {
    private Long id;
    private String bookCateName;
    private String description;
    private String color;
}
