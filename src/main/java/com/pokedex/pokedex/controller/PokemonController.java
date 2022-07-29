package com.pokedex.pokedex.controller;


import com.pokedex.pokedex.model.Lista;
import com.pokedex.pokedex.model.Lista1;
import com.pokedex.pokedex.model.Results;
import com.pokedex.pokedex.serviceimpl.AllPokemonImpl;
import com.pokedex.pokedex.serviceimpl.DetailPokemonImpl;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping({"/v1"})
public class PokemonController {
    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Value("${protocolo.services}")
    String protocoloService;

    @Value("${connect.host.pokemon}")
    String hostPokemon;

    @Value("${connect.content.pokemon}")
    String contentPokemon;

    @Autowired
    private DetailPokemonImpl detailPokemon;

    @Autowired
    private AllPokemonImpl allPokemon;

    public PokemonController(DetailPokemonImpl detailPokemon) {
        this.detailPokemon = detailPokemon;
    }

    @RequestMapping(value = {"/pokemones"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public ResponseEntity<?> pokemon(@RequestParam(required = false) int pageNum,@RequestParam(required = false) int pageSize) throws Exception, NoSuchElementException, NullPointerException {

        List<Results> listado= this.allPokemon.getAllPokemon(protocoloService,hostPokemon,contentPokemon,pageNum,pageSize);

        int cantidad= listado.size();

        List<Lista> lista= new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            String valor =listado.get(i).getUrl().toString().substring(34,36);
            if(valor.substring(1,2).equals("/") ){
                Integer numPokemon=Integer.valueOf(listado.get(i).getUrl().toString().substring(34,35));
                lista.add(i,detailPokemon1(numPokemon));
            }else{
                Integer numPokemon=Integer.valueOf(listado.get(i).getUrl().toString().substring(34,36));
                lista.add(i,detailPokemon1(numPokemon));
            }

        }
        return new ResponseEntity<>(lista, HttpStatus.OK);

    }

    @RequestMapping(value = {"/pokemon/detail/{id}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public ResponseEntity<Lista1> detailPokemon(@PathVariable Integer id) throws Exception, NoSuchElementException, NullPointerException {
        logger.info("****DETAIL POKEMON**********");
        System.out.println(id);
        Lista1 listaDetail= this.detailPokemon.getPokemonDescriptions(id,protocoloService,hostPokemon,contentPokemon);

        System.out.println(listaDetail);
        return new ResponseEntity<Lista1>(listaDetail, HttpStatus.OK);

    }

    private Lista detailPokemon1(Integer id) throws Exception, NoSuchElementException, NullPointerException {
        logger.info("****DETAIL POKEMON 1**********");
        Lista listaDetail= this.detailPokemon.getPokemon(id,protocoloService,hostPokemon,contentPokemon);
        System.out.println(listaDetail.getAbilities());
        return listaDetail;

    }




}



