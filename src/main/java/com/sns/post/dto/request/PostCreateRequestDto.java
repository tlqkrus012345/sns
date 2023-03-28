package com.sns.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCreateRequestDto {
    private String title;
    private String context;
}
