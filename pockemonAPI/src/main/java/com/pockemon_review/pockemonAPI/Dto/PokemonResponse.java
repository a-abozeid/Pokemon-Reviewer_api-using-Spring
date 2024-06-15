package com.pockemon_review.pockemonAPI.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PokemonResponse {
    private List<PokemonDto> data;
    private int pageNum;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private Boolean last;
}
