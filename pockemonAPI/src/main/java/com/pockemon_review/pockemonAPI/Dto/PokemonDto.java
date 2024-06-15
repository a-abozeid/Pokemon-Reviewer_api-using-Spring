package com.pockemon_review.pockemonAPI.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PokemonDto {
    private int id;
    private String name;
    private String type;
}
