package com.pockemon_review.pockemonAPI.Repository;

import com.pockemon_review.pockemonAPI.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

}
