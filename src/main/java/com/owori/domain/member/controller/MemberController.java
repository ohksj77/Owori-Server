package com.owori.domain.member.controller;

import com.owori.domain.member.dto.request.EmotionalBadgeRequest;
import com.owori.domain.member.dto.request.MemberDetailsRequest;
import com.owori.domain.member.dto.request.MemberProfileRequest;
import com.owori.domain.member.dto.request.MemberRequest;
import com.owori.domain.member.dto.response.*;
import com.owori.domain.member.service.MemberService;
import com.owori.global.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 멤버 생성 컨트롤러입니다.
     * 멤버를 조회한 후 이미 존재하면 Jwt 토큰을 생성하고, 없다면 생성 후 Jwt 토큰을 생성해 response 합니다.
     * @param memberRequest 멤버의 OAuth2 인증 후 OAuth2 Provider 와 조회가능한 accountId 를 가집니다.
     * @return 멤버의 JwtToken 입니다.
     */
    @PostMapping
    public ResponseEntity<MemberJwtResponse> saveMember(@RequestBody @Valid MemberRequest memberRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveIfNone(memberRequest));
    }

    /**
     * 멤버 정보 업데이트 컨트롤러입니다. (회원가입시 받는 정보들)
     * 멤버 정보를 받아와 업데이트한 후 상태코드 200번을 빈 body 와 함께 response 합니다.
     * @param memberDetailsRequest 멤버의 닉네임, 생일을 가집니다.
     * @return 바디로 response 하는 정보는 없습니다.
     */
    @PostMapping("/details")
    public ResponseEntity<MemberValidateResponse> updateMemberDetails(@RequestBody @Valid MemberDetailsRequest memberDetailsRequest) {
        return ResponseEntity.ok(memberService.updateMemberDetails(memberDetailsRequest));
    }

    /**
     * 멤버 프로필이미지 업데이트 컨트롤러입니다.
     * 멤버 프로필 이미지를 받아와 업데이트한 후 상태코드 200번을 빈 body 와 함께 response 합니다.
     * @param profileImage 멤버의 프로필 이미지입니다.
     * @return 바디로 response 하는 정보는 없습니다.
     * @throws IOException 파일 업로드시 발생할 수 있는 예외입니다.
     */
    @PostMapping("/profile-image")
    public ResponseEntity<ImageResponse> updateMemberProfileImage(@RequestPart("profile_image") MultipartFile profileImage) {
        return ResponseEntity.ok(memberService.updateMemberProfileImage(profileImage));
    }

    /**
     * 멤버 프로필 업데이트 컨트롤러입니다.
     * 멤버 프로필 정보를 받아와 업데이트한 후 상태코드 200번을 빈 body 와 함께 response 합니다.
     * @param memberProfileRequest 멤버의 수정 요청한 프로필 정보입니다.
     * @return 바디로 response 하는 정보는 없습니다.
     */
    @PostMapping("/profile")
    public ResponseEntity<Void> updateMemberProfile(@RequestBody @Valid MemberProfileRequest memberProfileRequest) {
        memberService.updateMemberProfile(memberProfileRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 멤버 회원 탈퇴 컨트롤러입니다.
     * 멤버를 삭제한 후 상태코드 200번을 빈 body 와 함께 response 합니다.
     * @return 바디로 response 하는 정보는 없습니다.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteMember() {
        memberService.deleteMember();
        return ResponseEntity.ok().build();
    }

    /**
     * 멤버 감정 뱃지 업데이트 컨트롤러입니다.
     * 멤버의 감정 뱃지를 받아와 수정합니다.
     * @param emotionalBadgeRequest 수정할 감정 뱃지 정보를 갖습니다.
     * @return 바디로 response 하는 정보는 없습니다.
     */
    @PostMapping("/emotional-badge")
    public ResponseEntity<Void> updateEmotionalBadge(@RequestBody EmotionalBadgeRequest emotionalBadgeRequest) {
        memberService.updateEmotionalBadge(emotionalBadgeRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 멤버 홈화면 조회 컨트롤러입니다.
     * @return 홈화면 정보를 반환합니다.
     */
    @GetMapping("/home")
    public ResponseEntity<MemberHomeResponse> findHomeData() {
        return ResponseEntity.ok(memberService.findHomeData());
    }

    /**
     * 마이페이지 유저 조회 컨트롤러입니다.
     * @return 로그인 유저 정보를 반환합니다.
     */
    @GetMapping("/profile")
    public ResponseEntity<MyPageProfileResponse> getMyPageProfile() {
        return ResponseEntity.ok(memberService.getMyPageProfile());
    }


    /**
     * 멤버의 수정가능한 색상 조회 컨트롤러입니다.
     * 멤버가 수정할 수 없게 표시할 색상을 true, 수정 불가 표시를 안해도 되는 색상에는 false를 response합니다.
     * @return 색상별로 true/false 값을 가지는 dto입니다.
     */
    @GetMapping("/colors")
    public ResponseEntity<MemberColorResponse> getEnableColor() {
        return ResponseEntity.ok(memberService.getEnableColor());
    }
}
