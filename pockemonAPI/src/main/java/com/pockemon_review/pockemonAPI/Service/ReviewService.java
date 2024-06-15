package com.pockemon_review.pockemonAPI.Service;

import com.pockemon_review.pockemonAPI.Dto.ReviewDto;
import com.pockemon_review.pockemonAPI.Dto.ReviewResponse;
import com.pockemon_review.pockemonAPI.models.Review;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
    ReviewResponse getReviewsByPokemonId(int pokemonID, int pageNum, int pageSize);
    ReviewDto getReviewById(int pokemonId, int reviewId);
    ReviewDto updateReviewById(ReviewDto reviewDto, int pokemonId, int id);
    void deleteReviewsByPokemonId(int pokemonId);
    void deleteReviewById(int pokemonId, int reviewId);
}
