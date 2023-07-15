package com.owori.domain.saying.controller;

import com.owori.domain.saying.dto.request.AddSayingRequest;
import com.owori.domain.saying.dto.request.UpdateSayingRequest;
import com.owori.domain.saying.dto.response.FindSayingByFamilyResponse;
import com.owori.domain.saying.service.SayingService;
import com.owori.global.dto.IdResponse;
import com.owori.support.docs.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.owori.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.owori.support.docs.ApiDocsUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DisplayName("Saying 컨트롤러의")
@WebMvcTest(SayingController.class)
public class SayingControllerTest extends RestDocsTest {
    @MockBean private SayingService sayingService;

    @Test
    @DisplayName("POST / saying 서로에게 한마디 등록 API 테스트")
    void addSaying() throws Exception {
        // given
        IdResponse<UUID> expected = new IdResponse<>(UUID.randomUUID());
        given(sayingService.addSaying(any())).willReturn(expected);

        AddSayingRequest request = new AddSayingRequest("오늘 집 안 들어가요", List.of());

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/saying")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer ghuriewhv32j12.oiuwhftg32shdi.ogiurhw0gb")
                                .header("memberId", UUID.randomUUID().toString())
                                .content(toRequestBody(request))
                );

        // then
        perform.andExpect(status().isCreated());

        // docs
        perform.andDo(document("save saying", getDocumentRequest(), getDocumentResponse()));

    }

    @Test
    @DisplayName("POST / saying 서로에게 한마디 수정 API 테스트")
    void updateSaying() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        IdResponse<UUID> expected = new IdResponse<>(id);
        given(sayingService.updateSaying(any(), any())).willReturn(expected);

        UpdateSayingRequest request = new UpdateSayingRequest("오늘 집 들어갈래", List.of(UUID.randomUUID()));

        // when
        ResultActions perform =
                mockMvc.perform(
                        post("/saying/update")
                                .param("sayingId", id.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization","Bearer ghuriewhv32j12.oiuwhftg32shdi.ogiurhw0gb")
                                .header("memberId", UUID.randomUUID().toString())
                                .content(toRequestBody(request))
                );

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(document("update saying", getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("DELETE / saying 서로에게 한마디 삭제 API 테스트")
    void deleteSaying() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        doNothing().when(sayingService).deleteSaying(any());

        // when
        ResultActions perform =
                mockMvc.perform(
                        delete("/saying")
                                .param("sayingId", id.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization","Bearer ghuriewhv32j12.oiuwhftg32shdi.ogiurhw0gb")
                                .header("memberId", UUID.randomUUID().toString())
                );

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(document("delete saying",getDocumentRequest(), getDocumentResponse()));
    }

    @Test
    @DisplayName("GET / saying 서로에게 한마디 가족 단위 조회 API 테스트")
    void findSayingByFamily() throws Exception {
        // given
        List<FindSayingByFamilyResponse> expected = List.of(
                new FindSayingByFamilyResponse(UUID.randomUUID(), "오늘 집 안들어가요", UUID.randomUUID(), List.of(), LocalDateTime.now()),
                new FindSayingByFamilyResponse(UUID.randomUUID(), "밥 먹고 들어갈게요",UUID.randomUUID(),List.of(UUID.randomUUID()), LocalDateTime.now())
        );
        given(sayingService.findSayingByFamily()).willReturn(expected);

        // when
        ResultActions perform =
                mockMvc.perform(
                        get("/saying")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer ghuriewhv32j12.oiuwhftg32shdi.ogiurhw0gb")
                                .header("memberId", UUID.randomUUID().toString())
                );

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").exists())
                .andExpect(jsonPath("$[0].member_id").exists());

        // docs
        perform.andDo(print())
                .andDo(document("find saying by family", getDocumentRequest(), getDocumentResponse()));
    }
}
