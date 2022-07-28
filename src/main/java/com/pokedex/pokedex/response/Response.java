package com.pokedex.pokedex.response;

import com.pokedex.pokedex.model.Abilities;
import com.pokedex.pokedex.model.Lista;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Response {

    private List<Lista> pokemones = new ArrayList<>();
}
