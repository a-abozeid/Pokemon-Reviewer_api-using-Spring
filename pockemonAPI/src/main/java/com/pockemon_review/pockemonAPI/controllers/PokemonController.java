package com.pockemon_review.pockemonAPI.controllers;

import com.pockemon_review.pockemonAPI.Dto.PokemonDto;
import com.pockemon_review.pockemonAPI.Dto.PokemonResponse;
import com.pockemon_review.pockemonAPI.Service.PokemonService;
import com.pockemon_review.pockemonAPI.models.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController {

    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping("/pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto){
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon")
    public ResponseEntity<PokemonResponse> getPokemons(
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pagesize
    ){
        return new ResponseEntity<>(pokemonService.getPokemons(pageNum, pagesize), HttpStatus.OK);
    }

    @GetMapping("/pokemon/{id}")
    public ResponseEntity<PokemonDto> pokemonDetails(@PathVariable int id){
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @PutMapping("/pokemon/{id}")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable("id") int id){
        return new ResponseEntity<>(pokemonService.updatePokemon(pokemonDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id){
        pokemonService.deletePokemon(id);
        return ResponseEntity.ok("deleted");
    }
}
