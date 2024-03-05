package com.application.learnenglish.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    @NotEmpty
    private String bookName;
    private String bookDescription;
    private String urlImage;
    private String cateBook;
}
