package com.springboot.blog.entity;

import com.springboot.blog.payload.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> contents;
    private int pageSize;
    private int pageNo;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
