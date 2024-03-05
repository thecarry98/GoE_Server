package com.application.learnenglish.model.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Image {
    private String pathPhoto;
    private Integer ordinal;
    private String urlImage;
}
