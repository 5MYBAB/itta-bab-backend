package com.fivemybab.ittabab.report.query.service;

import com.fivemybab.ittabab.report.query.dto.ReportDto;
import com.fivemybab.ittabab.report.query.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import com.fivemybab.ittabab.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportQueryService {

    private final ReportMapper reportMapper;

    @Transactional(readOnly = true)
    public List<ReportDto> findReportList() throws NotFoundException {

        List<ReportDto> reportList = reportMapper.adminReportList();
        if (reportList.isEmpty()) {
            throw new NotFoundException("들어온 신고 없음");
        }

        return reportList;

    }

    public List<ReportDto> findMyReportList(Long userId) throws NotFoundException {

        List<ReportDto> reportList = reportMapper.userReportList(userId);
        if (reportList.isEmpty()) {
            throw new NotFoundException("들어온 신고 없음");
        }

        return reportList;

    }
}
