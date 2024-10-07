package com.fivemybab.ittabab.store.command.application.service;

import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.store.command.application.dto.CreateStoreReviewDto;
import com.fivemybab.ittabab.store.command.application.dto.UpdateStoreReviewDto;
import com.fivemybab.ittabab.store.command.domain.repository.StoreRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreReviewRepository;
import com.fivemybab.ittabab.store.command.domain.aggregate.StoreReview;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreReviewService {


    private final StoreReviewRepository storeReviewRepository;
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    /* 가게 리뷰 추가 */
    @Transactional
    public void registStoreReview(CreateStoreReviewDto newStoreReview, Long userId) {



        // 존재하지 않는 가게 ID 입력시
        if(!storeRepository.existsById(newStoreReview.getStoreId())) {
            throw new NotFoundException("해당 가게가 존재하지 않습니다.");
        }

        // 가게 리뷰는 5점이 최대
        if (newStoreReview.getRating() > 5) {
            newStoreReview.setRating(5);
        }


        StoreReview storeReview = modelMapper.map(newStoreReview, StoreReview.class);
        storeReview.setUserId(userId);
        storeReviewRepository.save(storeReview);
    }

    /* 가게 리뷰 수정*/
    @Transactional
    public void updateStoreReview(Long userId ,UpdateStoreReviewDto updateStoreReviewDTO) {

        Long reviewId = updateStoreReviewDTO.getReviewId();

        if(!storeReviewRepository.existsById(reviewId)) {
            throw new NotFoundException("해당 가게 리뷰가 존재하지 않습니다.");
        }


        StoreReview storeReview = storeReviewRepository.findByReviewIdAndUserId(reviewId, userId)
                .orElseThrow(()-> new IllegalArgumentException("타인의 가게 리뷰는 수정할 수 없습니다."));


        if(updateStoreReviewDTO.getReviewContent() != null) {
            storeReview.modifyStoreReviewContent(updateStoreReviewDTO.getReviewContent());
        }

        if (updateStoreReviewDTO.getRating() != null) {
            storeReview.modifyStoreReviewRating(updateStoreReviewDTO.getRating());

            if (updateStoreReviewDTO.getRating() > 5) {
                storeReview.modifyStoreReviewRating(5);
            }

        }


    }

    /* 가게 리뷰 삭제 */
    @Transactional
    public void deleteStoreReview(Long storeReviewId, Long userId) {

        storeReviewRepository.deleteByReviewIdAndUserId(storeReviewId, userId)
                .orElseThrow(()-> new IllegalArgumentException("타인의 가게 리뷰는 삭제할 수 없습니다."));
    }


}
