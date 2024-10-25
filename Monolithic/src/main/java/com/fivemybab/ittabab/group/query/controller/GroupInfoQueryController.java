package com.fivemybab.ittabab.group.query.controller;

import com.fivemybab.ittabab.group.query.dto.GroupInfoDto;
import com.fivemybab.ittabab.group.query.service.GroupInfoQueryService;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/group")
@Slf4j
@Tag(name = "Group", description = "모임 관련 API")
public class GroupInfoQueryController {

    private final GroupInfoQueryService groupInfoQueryService;

    @Autowired
    public GroupInfoQueryController(GroupInfoQueryService groupInfoQueryService) {
        this.groupInfoQueryService = groupInfoQueryService;
    }

    @Operation(
            summary = "관리자 전체 모임 조회",
            description = "관리자는 course_id 상관없이 모두 조회합니다."
    )
    @GetMapping("/admin/list")
    public ResponseEntity<List<GroupInfoDto>> allGroup() {
        List<GroupInfoDto> groupList = groupInfoQueryService.findAllGroup();

        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    /* 전체 모임 조회 */
    @Operation(
            summary = "전체 모임 조회",
            description = "전체 모임을 조회합니다(블라인드 처리되지 않은)."
    )
    @GetMapping("/list")
    public ResponseEntity<List<GroupInfoDto>> group(
            Model model,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        System.out.println("loginUser.getUserId = " + loginUser.getUserId());
        // 인증된 사용자가 아닌 경우 에러 페이지로 이동 -> 에러 페이지 구현해야 함.
        if (loginUser == null || loginUser.getAuthorities().isEmpty()) {
            // 적절한 오류 메시지를 모델에 추가하고 에러 페이지로 리다이렉트
            model.addAttribute("errorMessage", "로그인 후에 접근할 수 있습니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // 인증된 사용자의 이름을 가져옴(ex: test01)
        System.out.println("loginUser.getUserId = " + loginUser.getUserId());

        List<GroupInfoDto> groupList = groupInfoQueryService.findGroupByGroupStatus(loginUser.getUserId());

        if (!groupList.isEmpty()) {
            model.addAttribute("groupList", groupList);
        }

        return ResponseEntity.status(HttpStatus.OK).body(groupList);
    }

    /* 참여중인 모임 조회 */
    @GetMapping("/attend")
    public ResponseEntity<List<GroupInfoDto>> attendGroup(
            @AuthenticationPrincipal CustomUserDetails loginUser) {
        System.out.println("loginUser.getCourseId() = " + loginUser.getCourseId());
        List<GroupInfoDto> foundGroup = groupInfoQueryService.findGroupByUserId(loginUser.getUserId(), loginUser.getCourseId());

        return ResponseEntity.ok(foundGroup);
    }

    /* 모임 상세 조회 */
    @Operation(
            summary = "모임 상세 조회",
            description = "특정 모임의 상세 정보를 조회합니다."
    )
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupInfoDto> groupDetail(
            @PathVariable Long groupId,
            Model model
    ) {
        GroupInfoDto foundGroup = groupInfoQueryService.findGroupByGroupId(groupId);
        model.addAttribute("foundGroup", foundGroup);
        return ResponseEntity.ok(foundGroup);
    }
}
