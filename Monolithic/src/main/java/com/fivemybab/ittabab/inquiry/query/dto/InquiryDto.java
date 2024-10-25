package com.fivemybab.ittabab.inquiry.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquiryDto {

    private Long inquiryId;
    private String inquiryContent;
    private LocalDateTime createDate;
    private String inquiryReply;
    private LocalDateTime inquiryReplyTime;
    private Long responseUserId;
    private Long inquiryUserId;
}
