package com.fivemybab.ittabab.report.query.mapper;

import com.fivemybab.ittabab.report.query.dto.ReportDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<ReportDto> adminReportList();

    List<ReportDto> userReportList(Long userId);
}
