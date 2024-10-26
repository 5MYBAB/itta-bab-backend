package com.fivemybab.ittabab.good.command.application.dto;

import com.fivemybab.ittabab.good.command.domain.aggregate.Target;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoodDto {
    private Long userId;
    private Target target;
    private Long targetId;
}
