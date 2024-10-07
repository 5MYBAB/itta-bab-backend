package com.fivemybab.ittabab.report.command.application.service;

import com.fivemybab.ittabab.board.command.domain.aggregate.Post;
import com.fivemybab.ittabab.board.command.domain.aggregate.PostComment;
import com.fivemybab.ittabab.board.command.domain.repository.PostCommentRepository;
import com.fivemybab.ittabab.board.command.domain.repository.PostRepository;
import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.report.command.application.dto.ResolveReportRequest;
import com.fivemybab.ittabab.report.command.application.dto.ResolveReportResponse;
import com.fivemybab.ittabab.report.command.domain.aggregate.Target;
import com.fivemybab.ittabab.report.command.application.dto.CreateReportRequest;

import com.fivemybab.ittabab.report.command.domain.aggregate.Report;
import com.fivemybab.ittabab.report.command.domain.repository.ReportRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreReviewRepository;
import com.fivemybab.ittabab.store.command.domain.aggregate.StoreReview;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final StoreReviewRepository storeReviewRepository;
    private final PostCommentRepository postCommentRepository;


    // 신고 생성
    public CreateReportRequest createReport(CreateReportRequest createReportRequest, Long userId) {

        if (userId == null) {
            throw new NotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        // 게시물 작성자 확인
        Long contentOwnerId = findContentOwner(createReportRequest.getReportTarget(), createReportRequest.getTargetId());

        // 본인의 게시물을 본인이 신고할 수 없도록 처리
        if (contentOwnerId.equals(userId)) {
            throw new IllegalArgumentException("본인의 게시물은 신고할 수 없습니다.");
        }

        // 이미 신고한 대상 확인
        if (reportRepository.existsByUserIdAndTargetIdAndTarget(userId, createReportRequest.getTargetId(), Target.valueOf(createReportRequest.getReportTarget()))) {
            throw new IllegalArgumentException("이미 신고한 대상입니다.");
        }

        // 신고 객체 생성
        Report report = Report.builder()
                .reportTitle(createReportRequest.getReportTitle())
                .reportContent(createReportRequest.getReportContent())
                .target(Target.valueOf(createReportRequest.getReportTarget()))
                .targetId(createReportRequest.getTargetId())
                .userId(userId)
                .isResolved(false)
                .build();

        reportRepository.save(report);
        return modelMapper.map(report, CreateReportRequest.class);
    }


    // 신고 처리 (관리자)
    public ResolveReportResponse resolveReport(Long reportId, ResolveReportRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고를 찾을 수 없습니다."));

        // 신고 처리
        report.resolve(LocalDateTime.now());

        reportRepository.save(report);  // 변경 사항 저장

        return modelMapper.map(report, ResolveReportResponse.class);
    }

    private Long findContentOwner(String reportTarget, Long targetId) {

        if ("POST".equals(reportTarget)) {
            Post post = postRepository.findById(targetId)
                    .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다."));
            return post.getUserId();
        } else if ("REVIEW".equals(reportTarget)) {
            StoreReview storeReview = storeReviewRepository.findById(targetId)
                    .orElseThrow(() -> new NotFoundException("해당 리뷰을 찾을 수 없습니다."));
            return storeReview.getUserId();
        } else if ("COMMENT".equals(reportTarget)) {
            PostComment postComment = postCommentRepository.findById(targetId)
                    .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다."));
            return postComment.getUserId();
        }

        // 다른 타입의 대상은 필요한 경우 추가로 처리
        throw new IllegalArgumentException("유효하지 않은 신고 대상입니다.");
    }
}
