package com.pokedex.pokedex.response;

import com.pokedex.pokedex.model.Lista;
import com.pokedex.pokedex.model.Lista1;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Response1 {

    private List<Lista1> pokemones = new ArrayList<>();
}
