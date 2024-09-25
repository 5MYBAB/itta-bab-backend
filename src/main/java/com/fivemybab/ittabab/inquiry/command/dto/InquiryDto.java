package com.fivemybab.ittabab.inquiry.command.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InquiryDto {

    private Long inquiryId;
    private String inquiryContent;
    private LocalDateTime createDate;
    private String inquiryReply;
    private LocalDateTime inquiryReplyTime;
    private Long responseUserId;
    private Long inquiryUserId;

}