package com.fivemybab.ittabab.good.command.application.service;

import com.fivemybab.ittabab.good.command.application.dto.GoodDto;
import com.fivemybab.ittabab.good.command.domain.aggregate.Good;
import com.fivemybab.ittabab.good.command.domain.aggregate.Target;
import com.fivemybab.ittabab.good.command.domain.repository.GoodRepository;
import com.fivemybab.ittabab.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodCommandService {

    private final GoodRepository goodRepository;


    @Transactional
    public boolean toggleLike(GoodDto goodDTO) {
        Optional<Good> existingLike = goodRepository.findByUserIdAndTargetAndTargetId(goodDTO.getUserId(), goodDTO.getTarget(), goodDTO.getTargetId());

        if (existingLike.isPresent()) {
            // 좋아요 취소
            goodRepository.deleteByUserIdAndTargetAndTargetId(goodDTO.getUserId(), goodDTO.getTarget(), goodDTO.getTargetId());
            return false; // 좋아요가 취소된 상태
        } else {
            // 좋아요 등록
            Good good = new Good(goodDTO.getUserId(), goodDTO.getTarget(), goodDTO.getTargetId());
            goodRepository.save(good);
            return true; // 좋아요가 등록된 상태
        }
    }
}