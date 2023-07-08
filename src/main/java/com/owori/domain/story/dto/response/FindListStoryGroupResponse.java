package com.owori.domain.story.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindListStoryGroupResponse {
    private List<FindListStoryResponse> stories;
    private Boolean hasNext;
}
