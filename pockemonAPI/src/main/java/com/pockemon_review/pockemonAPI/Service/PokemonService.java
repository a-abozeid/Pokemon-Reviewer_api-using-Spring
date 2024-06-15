package com.pockemon_review.pockemonAPI.Service;

import com.pockemon_review.pockemonAPI.Dto.PokemonDto;
import com.pockemon_review.pockemonAPI.Dto.PokemonResponse;

import java.util.List;

public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getPokemons(int pageNum, int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemon(int id);
}
