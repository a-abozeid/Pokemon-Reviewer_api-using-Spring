package com.pockemon_review.pockemonAPI.Repository;

import com.pockemon_review.pockemonAPI.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    void deleteByPokemonId(int pokemonId);
    Page<Review> findByPokemonId(int pokemonId, Pageable pageable);
}
