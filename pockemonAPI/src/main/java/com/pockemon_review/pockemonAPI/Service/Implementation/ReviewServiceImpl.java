package com.pockemon_review.pockemonAPI.Service.Implementation;

import com.pockemon_review.pockemonAPI.Dto.ReviewDto;
import com.pockemon_review.pockemonAPI.Dto.ReviewResponse;
import com.pockemon_review.pockemonAPI.Repository.PokemonRepository;
import com.pockemon_review.pockemonAPI.Repository.ReviewRepository;
import com.pockemon_review.pockemonAPI.Service.ReviewService;
import com.pockemon_review.pockemonAPI.exception.PokemonNotFoundException;
import com.pockemon_review.pockemonAPI.exception.ReviewNotFoundException;
import com.pockemon_review.pockemonAPI.models.Pokemon;
import com.pockemon_review.pockemonAPI.models.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("No pokemon with given id"));
        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public ReviewResponse getReviewsByPokemonId(int pokemonID, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Review> reviews = reviewRepository.findByPokemonId(pokemonID, pageable);
        List<Review> listOfReviews = reviews.getContent();
        List<ReviewDto> reviewDtos = reviews.stream().map(this::mapToDto).toList();

        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setData(reviewDtos);
        reviewResponse.setPageNum(reviews.getNumber());
        reviewResponse.setPageSize(reviews.getSize());
        reviewResponse.setTotalElements(reviews.getNumberOfElements());
        reviewResponse.setTotalPages(reviews.getTotalPages());
        reviewResponse.setLast(reviews.isLast());

        return reviewResponse;
    }

    @Override
    public ReviewDto getReviewById(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("No review by this id"));
        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("this pokemon doesn't have this review");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReviewById(ReviewDto reviewDto, int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("No review by this id"));
        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("this pokemon doesn't have this review");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReviewsByPokemonId(int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        reviewRepository.deleteByPokemonId(pokemonId);
    }

    @Override
    public void deleteReviewById(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("No review by this id"));
        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("this pokemon doesn't have this review");
        }

        reviewRepository.deleteById(reviewId);
    }

    public ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());

        return reviewDto;
    }
    public Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        return review;
    }
}
