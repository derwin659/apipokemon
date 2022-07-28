package com.pokedex.pokedex.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.model.Lista;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

import java.awt.print.Pageable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping({"/v1"})
public class ListPokemonesController {
    private static final Logger logger = LoggerFactory.getLogger(ListPokemonesController.class);

    @Value("${protocolo.services}")
    String protocoloService;

    @Value("${connect.host.pokemon}")
    String hostPokemon;

    @Value("${connect.content.pokemon}")
    String contentPokemon;


    RestTemplate restTemplate = new RestTemplate();
    int connectTimeoutMillis = 30000;
    int readTimeoutMillis = 30000;

    public ListPokemonesController(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(connectTimeoutMillis))
                .setReadTimeout(Duration.ofMillis(readTimeoutMillis))
                .build();
    }

    public ListPokemonesController() {
    }

    @RequestMapping(value = {"/pokemones"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public ResponseEntity<?> login(@RequestParam int pageNum,@RequestParam int pageSize) throws Exception, NoSuchElementException, NullPointerException {
        logger.info("****RESP SERVICIO PH**********");

        return new ResponseEntity<>(responsePokemones(protocoloService, hostPokemon, contentPokemon,pageNum,pageSize), HttpStatus.OK);

    }

    private List responsePokemones(String protocoloService, String hostPokemon, String contentPokemon,int pageNum,int pageSize) {


        logger.info("****RESP SERVICIO PH**********");
        ObjectMapper mapper = new ObjectMapper();
        //String jsonString = mapper.writeValueAsString(requestPH);
        String endpointPokemon = protocoloService + hostPokemon + contentPokemon;
        logger.info(endpointPokemon);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Api de prueba Pokedex");
        HttpEntity<String> entityd = new HttpEntity<String>("parameters", headers);

        try {
            ResponseEntity<String> result = this.restTemplate.exchange(endpointPokemon, HttpMethod.GET, entityd, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                logger.info("****RESPONDIO OK**********");
                JSONObject jsonObject = new JSONObject((String) result.getBody());
                JSONArray results = jsonObject.getJSONArray("results");
                List<Object> list = results.toList();







                return getPageLimit(list, pageNum, pageSize);


            }

        } catch (HttpClientErrorException e) {
            logger.info("****error cliente*********");
        }catch (ServerErrorException e){
            logger.info("***A ocurrido un error en el servicio de pokemon*********" + e.getMessage());
        }
        catch (Exception e) {
            logger.info("***A ocurrido un error*********" + e.getMessage());
        }
        return null;
    }


    public static List getPageLimit(List dataList, int pageNum, int pageSize) {
        if (CollectionUtils.isEmpty(dataList)) {
            return dataList;
        }
        List resultList = new ArrayList();
        //What is the number of items in all datalist data
        int currIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && i < dataList.size() - currIdx; i++) {
            resultList.add(dataList.get(currIdx + i));
        }
        return resultList;
    }
}



