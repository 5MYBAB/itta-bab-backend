package com.fivemybab.ittabab.store.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyReviewResponse {

    private Long reviewId;
    private Long storeId;
    private Long userId;
    private String reviewContent;
    private Integer rating;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isBlinded;
    private String storeName;
}
