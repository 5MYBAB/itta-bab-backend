package com.fivemybab.ittabab.inquiry.query.service;

import com.fivemybab.ittabab.inquiry.query.dto.InquiryDto;
import com.fivemybab.ittabab.inquiry.query.mapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class InquiryQueryService {

    private final InquiryMapper inquiryMapper;

    /* 문의 목록 */
    @Transactional(readOnly = true)
    public List<InquiryDto> findInquiryList() throws NotFoundException {
        List<InquiryDto> inquiryList = inquiryMapper.adminInquiryList();

        if (inquiryList.isEmpty()) {
            throw new NotFoundException("들어온 문의 없음");
        }

        return inquiryList;
    }

    /* 사용자 문의 목록 */
    @Transactional(readOnly = true)
    public List<InquiryDto> findInquiryListByUserId(Long inquiryId) throws NotFoundException {
        // userId를 사용하여 문의 목록을 조회
        List<InquiryDto> inquiryList = inquiryMapper.userInquiryList(inquiryId);

        // 조회된 목록이 비어있을 경우 경고 로그를 출력하고 예외를 던짐
        if (inquiryList == null || inquiryList.isEmpty()) {
            log.warn("사용자 ID {}에 대한 문의가 없습니다.", inquiryId);
            throw new NotFoundException("사용자 ID " + inquiryId + "에 대한 문의가 없습니다.");
        } else {
            log.info("inquiryList: {}", inquiryList);
        }

        return inquiryList;
    }

    @Transactional(readOnly = true)
    public InquiryDto findInquiryListById(Long inquiryId) {
        return inquiryMapper.inquiryIdList(inquiryId);
    }
}
