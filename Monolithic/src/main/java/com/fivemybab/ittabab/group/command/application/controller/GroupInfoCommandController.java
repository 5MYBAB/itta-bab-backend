package com.fivemybab.ittabab.group.command.application.controller;

import com.fivemybab.ittabab.group.command.application.service.GroupInfoCommandService;
import com.fivemybab.ittabab.group.command.domain.aggregate.ChatRoomStatus;
import com.fivemybab.ittabab.group.query.dto.ChatMessageDto;
import com.fivemybab.ittabab.group.query.dto.GroupInfoDto;
import com.fivemybab.ittabab.group.query.dto.RequestChatDto;
import com.fivemybab.ittabab.group.query.service.GroupInfoQueryService;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import com.fivemybab.ittabab.user.command.domain.aggregate.UserRole;
import com.fivemybab.ittabab.user.query.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/group")
@Slf4j
@Tag(name = "Group", description = "모임 관련 API")
@RequiredArgsConstructor
public class GroupInfoCommandController {

    private final GroupInfoCommandService groupService;
    private final GroupInfoQueryService groupInfoQueryService;
    private final UserQueryService userQueryService;
    private final ModelMapper modelMapper;

    /* 모임 등록 */
    @Operation(
            summary = "모임 등록",
            description = "새로운 모임을 등록합니다."
    )
    @PostMapping
    public ResponseEntity<String> registGroup(
            @RequestBody GroupInfoDto newGroupInfo,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        System.out.println("123");
        Long userId = loginUser.getUserId();
        System.out.println("userId = " + userId);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            System.out.println("userId = " + userId);
            newGroupInfo.setUserId(userId);
            newGroupInfo.setCreateDate(LocalDateTime.now());
            groupService.registGroup(loginUser.getUserId(), newGroupInfo);

            return new ResponseEntity<>("등록 완료", HttpStatus.OK);
        }
    }

    /* 모임 참여 */
    @Operation(
            summary = "모임 참여",
            description = "모임에 참여합니다. \n해당 모임에 가입되지 않은 상태여야 합니다."
    )
    @PostMapping("/detail/{groupId}")
    public ResponseEntity<String> registGroupUser(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        try {
            // 1. 모임이 존재하는 확인
            GroupInfoDto foundGroupInfo = groupService.findGroupByGroupId(groupId);
            if (foundGroupInfo == null) {
                return new ResponseEntity<>("그런 모임은 없습니다.", HttpStatus.NOT_FOUND);
            }

            // 2. 이미 가입되어 있는지 확인
            List<Long> groupUserList = groupService.findGroupUserByGroupId(groupId);
            for (Long userId : groupUserList) {
                if (userId.equals(loginUser.getUserId())) {
                    return new ResponseEntity<>("이미 가입하셨습니다.", HttpStatus.ALREADY_REPORTED);
                }
            }

            // 3. 가입하면 모집 인원 이하가 되는지 확인
            if (groupUserList.size() + 1 <= foundGroupInfo.getUserCounting()) {
                // insert
                groupService.registGroupUser(loginUser.getUserId(), foundGroupInfo.getGroupId());
                return new ResponseEntity<>("참여가 완료되었습니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("모집 인원이 초과했습니다.", HttpStatus.LOCKED);
            }

        } catch (Exception e) {
            e.printStackTrace(); // 로그 출력
            return new ResponseEntity<>("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 모임 채팅 생성 */
    @Operation(
            summary = "모임 채팅 생성",
            description = "모임 모집을 마감하고 채팅방을 생성합니다. 채팅방이 생성되지 않은 상태여야 생성이 가능합니다.")
    @PostMapping("/chat")
    public ResponseEntity<String> createChat(
            @RequestBody RequestChatDto requestChatDto,
            @AuthenticationPrincipal CustomUserDetails loginUser,
            Model model
    ) {

        ChatRoomStatus chatRoomStatus = groupInfoQueryService.findGroupByGroupId(requestChatDto.getGroupId()).getChatRoomStatus();

        if (chatRoomStatus.equals(ChatRoomStatus.NOT_CREATED)) {
            ChatMessageDto chatMessageDto = new ChatMessageDto();

            chatMessageDto.setMessageType(ChatMessageDto.MessageType.ENTER);
            chatMessageDto.setChatRoomId(requestChatDto.getGroupId());
            chatMessageDto.setMessage(chatMessageDto.getMessage());
            groupService.createChat(chatMessageDto, loginUser);
            return new ResponseEntity<>("채팅 생성 성공", HttpStatus.OK);
        } else if (chatRoomStatus.equals(ChatRoomStatus.CREATED)) {
            return new ResponseEntity<>("이미 생성되어 있습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("채팅방이 닫혔습니다. 관리자에게 문의하세요.", HttpStatus.OK);
        }
    }

    /* 모임 채팅방 채팅 전송 */
    @Operation(
            summary = "채팅 전송",
            description = "채팅방에 채팅을 전송합니다. 채팅방이 활성화된 상태여야 전송이 가능합니다."
    )
    @PostMapping("/chat/{groupId}")
    public ResponseEntity<String> joinChat(
            @PathVariable Long groupId,
            @RequestBody ChatMessageDto chatMessageDto,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {

        ChatRoomStatus chatRoomStatus = groupInfoQueryService.findGroupByGroupId(groupId).getChatRoomStatus();

        if (chatRoomStatus.equals(ChatRoomStatus.CREATED)) {
            chatMessageDto.setMessageType(ChatMessageDto.MessageType.TALK);
            chatMessageDto.setChatRoomId(groupId);
            groupService.sendChat(chatMessageDto, loginUser);

            return new ResponseEntity<>("채팅 전송 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("채팅 전송에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /* 모임 삭제 */
    @Operation(
            summary = "모임 삭제",
            description = "모임을 삭제합니다. 작성자 또는 관리자만이 삭제가 가능합니다."
    )
    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {

        Long userUserId = loginUser.getUserId();
        UserRole role = UserRole.valueOf(userQueryService.findById(userUserId).getUserRole());

        if (checkCreator(loginUser, groupId) || role.equals(UserRole.ADMIN)) {
            groupService.deleteGroupInfo(groupId);

            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return new ResponseEntity<>("작성자가 아닙니다.", HttpStatus.OK);
        }
    }

    /* 모임 모집자 확인 */
    public boolean checkCreator(
            CustomUserDetails loginUser,
            Long groupId
    ) {
        Long creatorId = groupService.findGroupByGroupId(groupId).getUserId();
        System.out.println("creatorId = " + creatorId);

        return creatorId == loginUser.getUserId();
    }
}