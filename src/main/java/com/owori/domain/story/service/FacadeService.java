package com.owori.domain.story.service;

import com.owori.domain.comment.dto.request.AddCommentRequest;
import com.owori.domain.comment.dto.response.CommentResponse;
import com.owori.domain.comment.service.CommentService;
import com.owori.domain.heart.dto.HeartStatusResponse;
import com.owori.domain.heart.service.HeartService;
import com.owori.domain.member.entity.Member;
import com.owori.domain.member.service.AuthService;
import com.owori.domain.story.dto.response.FindStoryResponse;
import com.owori.domain.story.entity.Story;
import com.owori.global.dto.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FacadeService {
    private final StoryService storyService;
    private final CommentService commentService;
    private final HeartService heartService;
    private final AuthService authService;

    public List<CommentResponse> findComments(Story story, Member member){
        return commentService.findComments(story, member);
    }

    public boolean hasHeart(Member member, Story story) {
        return heartService.hasHeart(member, story);
    }

    public void removeComment(UUID id) {
        commentService.removeComment(id);
    }

    public Story loadStoryEntity(UUID storyId) {
        return storyService.loadEntity(storyId);
    }

    public FindStoryResponse findStory(UUID storyId)
    {
        Story story = loadStoryEntity(storyId);
        Member member = authService.getLoginUser();
        List<CommentResponse> comments = findComments(story, member);
        boolean isLiked = hasHeart(member, story);

        return storyService.findStory(story, comments, isLiked);
    }

    public void removeStory(UUID storyId){
        Story story = loadStoryEntity(storyId);
        story.getComments().stream()
                .forEach(comment -> removeComment(comment.getId()));
        storyService.removeStory(story);
    }

    public HeartStatusResponse toggleHeart(UUID storyId) {
        Story story = loadStoryEntity(storyId);
        return heartService.toggleHeart(story);
    }

    public IdResponse<UUID> addComment(AddCommentRequest request) {
        Story story = loadStoryEntity(request.getStoryId());
        return commentService.addComment(story, request);
    }
}