package com.fivemybab.ittabab.good.query.mapper;

import com.fivemybab.ittabab.good.command.domain.aggregate.Target;
import com.fivemybab.ittabab.good.query.dto.CountGoodRequest;
import com.fivemybab.ittabab.good.query.dto.IsLikedResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface GoodMapper {

    int countByTargetAndTargetId(CountGoodRequest request);

    boolean existsLike(@Param("userId") Long userId,
                       @Param("target") Target target,
                       @Param("targetId") Long targetId);
}
