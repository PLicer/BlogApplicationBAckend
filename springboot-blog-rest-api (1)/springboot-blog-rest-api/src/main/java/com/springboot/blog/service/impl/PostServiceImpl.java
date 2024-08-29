package com.springboot.blog.service.impl;
import com.springboot.blog.entity.PostResponse;
import org.springframework.data.domain.Pageable;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    public PostServiceImpl(PostRepository postRepository)
    {
        this.postRepository=postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {

        Post post=PostDtoToPost(postDto);

        Post newPost=postRepository.save(post);

        PostDto postDto1 = PostToPostDto(newPost);

        return postDto1;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortby,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortby).ascending():Sort.by(sortby).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts=postRepository.findAll(pageable);
        List<Post>listOfPosts=posts.getContent();
       List<PostDto> content= listOfPosts.stream().map(post-> PostToPostDto(post)).collect(Collectors.toList());
       PostResponse postResponse=new PostResponse();
       postResponse.setContents(content);
       postResponse.setPageNo(posts.getNumber());
       postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(Long id) {
        Post post =postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return PostToPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post =postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        Post updatedPost= postRepository.save(post);
        return PostToPostDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    private Post PostDtoToPost(PostDto postDto)
    {
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    private PostDto PostToPostDto(Post post)
    {
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        postDto.setId(post.getId());
        return postDto;
    }
}
