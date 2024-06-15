package com.pockemon_review.pockemonAPI.Service.Implementation;

import com.pockemon_review.pockemonAPI.Dto.PokemonDto;
import com.pockemon_review.pockemonAPI.Dto.PokemonResponse;
import com.pockemon_review.pockemonAPI.Repository.PokemonRepository;
import com.pockemon_review.pockemonAPI.Service.PokemonService;
import com.pockemon_review.pockemonAPI.exception.PokemonNotFoundException;
import com.pockemon_review.pockemonAPI.models.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = mapToEntity(pokemonDto);
        Pokemon newPokemon = pokemonRepository.save(pokemon);

        return mapToDto(newPokemon);
    }

    @Override
    public PokemonResponse getPokemons(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemons.getContent();
        List<PokemonDto> pokemonDtos = pokemons.stream().map(this::mapToDto).toList();

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setData(pokemonDtos);
        pokemonResponse.setPageNum(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements((int) pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("No Pokemon with this id"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("No Pokemon with this id"));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemon(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("No Pokemon with this id"));
        pokemonRepository.deleteById(id);
    }

    public PokemonDto mapToDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    public Pokemon mapToEntity(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}
