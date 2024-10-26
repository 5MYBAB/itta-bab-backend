package com.fivemybab.ittabab.good.query.service;

import com.fivemybab.ittabab.good.command.domain.aggregate.Target;
import com.fivemybab.ittabab.good.query.dto.CountGoodRequest;
import com.fivemybab.ittabab.good.query.dto.IsLikedResponse;
import com.fivemybab.ittabab.good.query.mapper.GoodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodQueryService {

    private final GoodMapper goodMapper;

    // 특정 대상의 좋아요 개수 반환
    public int countGoods(Target target, Long targetId) {
        CountGoodRequest request = new CountGoodRequest();
        request.setTarget(target);
        request.setTargetId(targetId);

        // Mapper 호출
        return goodMapper.countByTargetAndTargetId(request);
    }

    // 좋아요 존재 여부
    public boolean isLiked(IsLikedResponse isLikedResponse) {
        return goodMapper.existsLike(isLikedResponse.getUserId(), isLikedResponse.getTarget(), isLikedResponse.getTargetId());
    }
}
