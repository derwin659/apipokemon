package com.pokedex.pokedex.service;

import com.pokedex.pokedex.model.Ability;
import com.pokedex.pokedex.model.Results;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AllPokemonService {
    List<Results> getAllPokemon(String protocoloService, String hostPokemon, String contentPokemon, int pageNum, int pageSize);
}
