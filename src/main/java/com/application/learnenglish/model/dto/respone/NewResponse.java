package com.application.learnenglish.model.dto.respone;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewResponse {
    private Long id;
    private String title;
    private String description;
    private String urlToImage;
    private String url;
}
