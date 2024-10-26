package com.fivemybab.ittabab.good.command.application.service;

import com.fivemybab.ittabab.good.command.application.dto.GoodDto;
import com.fivemybab.ittabab.good.command.domain.aggregate.Good;
import com.fivemybab.ittabab.good.command.domain.aggregate.Target;
import com.fivemybab.ittabab.good.command.domain.repository.GoodRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
class GoodCommandServiceTest {
    @Autowired
    private GoodCommandService goodCommandService;

    @Autowired
    private GoodRepository goodRepository;


//    @Test
//    @DisplayName("좋아요 생성 성공")
//    void createGood_Success() {
//        Long userId = 8L;
//        Target target = Target.POST;
//        Long targetId = 1L;
//
//        // 좋아요 생성
//        goodCommandService.toggleLike(GoodDto);
//
//        // 저장소에서 좋아요가 생성되었는지 확인
//        Optional<Object> savedGood = goodRepository.findByUserIdAndTargetAndTargetId(userId, target, targetId);
//        assertEquals(true, savedGood.isPresent());
//    }

//    @Test
//    @DisplayName("이미 좋아요를 눌렀을 때 예외 발생")
//    void createGood_AlreadyLiked_Exception() {
//        Long userId = 8L;
//        Target target = Target.POST;
//        Long targetId = 1L;
//
//        // 이미 좋아요 생성
//        goodCommandService.createGood(userId, target, targetId);
//
//        // 같은 좋아요를 다시 생성하려고 할 때 예외 발생 확인
//        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
//            goodCommandService.createGood(userId, target, targetId);
//        });
//
//        assertEquals("이미 좋아요 함", exception.getMessage());
//    }

//    @Test
//    @DisplayName("좋아요 삭제 성공")
//    void deleteGood_Success() {
//        Long userId = 8L;
//        Target target = Target.POST;
//        Long targetId = 1L;
//
//        // 좋아요 삭제
//        goodCommandService.deleteGood(userId, target, targetId);
//
//        // 삭제 후 좋아요가 존재하지 않음을 확인
//        Optional<Object> deletedGood = goodRepository.findByUserIdAndTargetAndTargetId(userId, target, targetId);
//        assertEquals(false, deletedGood.isPresent());
//    }
//
//    @Test
//    @DisplayName("좋아요를 누르지 않았을 때 삭제 시도 시 예외 발생")
//    void deleteGood_NotLiked_Exception() {
//        Long userId = 8L;
//        Target target = Target.POST;
//        Long targetId = 1L;
//
//        // 좋아요 삭제 시도 (아직 좋아요를 누르지 않았음)
//        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
//            goodCommandService.deleteGood(userId, target, targetId);
//        });
//
//        assertEquals("좋아요 누른 적 없음", exception.getMessage());
//    }
}