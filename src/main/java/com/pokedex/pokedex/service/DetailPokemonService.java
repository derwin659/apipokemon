package com.pokedex.pokedex.service;

import com.pokedex.pokedex.model.Abilities;
import com.pokedex.pokedex.model.Ability;
import com.pokedex.pokedex.model.Lista;
import com.pokedex.pokedex.model.Lista1;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface DetailPokemonService {

  Lista getPokemon(Integer id, String protocoloService, String hostPokemon, String contentPokemon);

  Lista1 getPokemonDescriptions(Integer id, String protocoloService, String hostPokemon, String contentPokemon);




}
