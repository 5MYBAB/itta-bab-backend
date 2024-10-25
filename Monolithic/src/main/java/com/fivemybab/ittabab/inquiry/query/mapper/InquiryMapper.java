package com.fivemybab.ittabab.inquiry.query.mapper;

import com.fivemybab.ittabab.inquiry.query.dto.InquiryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryMapper {
    List<InquiryDto> userInquiryList(@Param("inquiryId") Long inquiryId);

    List<InquiryDto> adminInquiryList();

    InquiryDto inquiryIdList(@Param("inquiryId") Long inquiryId);
}
