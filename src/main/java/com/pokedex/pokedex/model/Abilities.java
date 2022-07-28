package com.pokedex.pokedex.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class Abilities {
    private List<Ability> abilitySet= new ArrayList<>();
    private Boolean is_hidden;
    private int slot;
}
