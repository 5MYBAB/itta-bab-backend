package com.fivemybab.ittabab.good.command.domain.repository;

import com.fivemybab.ittabab.good.command.domain.aggregate.Good;
import com.fivemybab.ittabab.good.command.domain.aggregate.Target;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {

    Optional<Good> findByUserIdAndTargetAndTargetId(Long userId, Target target, Long targetId);

    void deleteByUserIdAndTargetAndTargetId(Long userId, Target target, Long targetId);
}
