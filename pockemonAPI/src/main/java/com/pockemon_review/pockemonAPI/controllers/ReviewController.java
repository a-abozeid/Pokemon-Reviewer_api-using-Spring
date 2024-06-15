package com.pockemon_review.pockemonAPI.controllers;

import com.pockemon_review.pockemonAPI.Dto.ReviewDto;
import com.pockemon_review.pockemonAPI.Dto.ReviewResponse;
import com.pockemon_review.pockemonAPI.Service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto){
        return new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewResponse> getReview(
            @PathVariable(value = "pokemonId") int pokemonId,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pagesize
    ){
        return new ResponseEntity<>(reviewService.getReviewsByPokemonId(pokemonId, pageNum, pagesize), HttpStatus.OK);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(
            @PathVariable(value = "pokemonId") int pokemonId,
            @PathVariable(value = "reviewId") int reviewId
    ){
        return new ResponseEntity<>(reviewService.getReviewById(pokemonId, reviewId), HttpStatus.OK);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReviewById(
            @RequestBody ReviewDto reviewDto,
            @PathVariable(value = "pokemonId") int pokemonId,
            @PathVariable(value = "reviewId") int reviewId
    ){
        return new ResponseEntity<>(reviewService.updateReviewById(reviewDto, pokemonId, reviewId), HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<String> deleteReviewsByPokemonId(@PathVariable(value = "pokemonId") int pokemonId){
        reviewService.deleteReviewsByPokemonId(pokemonId);
        return ResponseEntity.ok("a lot deleted!");
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReviewById(
            @PathVariable(value = "pokemonId") int pokemonId,
            @PathVariable(value = "reviewId") int reviewId
    ){
        reviewService.deleteReviewById(pokemonId, reviewId);
        return ResponseEntity.ok("deleted!");
    }
}
