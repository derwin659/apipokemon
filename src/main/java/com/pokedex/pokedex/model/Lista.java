package com.pokedex.pokedex.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class Lista {
    private String type;
    private String url;
    private int weight;
    private List<Abilities> abilitiesSet = new ArrayList<>();

}
