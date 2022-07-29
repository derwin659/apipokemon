package com.pokedex.pokedex.serviceimpl;

import com.pokedex.pokedex.model.Ability;
import com.pokedex.pokedex.model.Pokemones;
import com.pokedex.pokedex.model.Results;
import com.pokedex.pokedex.service.AllPokemonService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Service
public class AllPokemonImpl implements AllPokemonService {

    private static final Logger logger = LoggerFactory.getLogger(AllPokemonImpl.class);
    RestTemplate restTemplate = new RestTemplate();
    int connectTimeoutMillis = 30000;
    int readTimeoutMillis = 30000;

    //private static final String URL_SERVICE = "http://localhost:8080/v1/pokemon/detail";

    public AllPokemonImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(connectTimeoutMillis))
                .setReadTimeout(Duration.ofMillis(readTimeoutMillis))
                .build();
    }

    public AllPokemonImpl() {
    }

    @Override
    public List<Results> getAllPokemon(String protocoloService, String hostPokemon, String contentPokemon, int pageNum, int pageSize) {

        logger.info("****RESP SERVICIO DETALLES DEL POKEMON**********");

        String endpointPokemon = protocoloService + hostPokemon + contentPokemon;
        logger.info(endpointPokemon);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Api de prueba Pokedex");
        HttpEntity<String> entityd = new HttpEntity<String>("parameters", headers);
        try {
            ResponseEntity<Pokemones> responseEntity = restTemplate.exchange(endpointPokemon, HttpMethod.GET, entityd, Pokemones.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                List<Results> list= responseEntity.getBody().getResults();
                logger.info("****paso 1*********");

                //JSONObject jsonObject = new JSONObject((String) responseEntity.getBody());
                //JSONArray results = jsonObject.getJSONArray("results");
                //Object results1 =  jsonObject.getJSONObject("results").get("url");
                //System.out.println(results1);
               // List<Object> list = results.toList();


                return getPageLimit(list, pageNum, pageSize);

            }
        } catch (HttpClientErrorException e) {
            logger.info("****error cliente*********");
        } catch (ServerErrorException e) {
            logger.info("***A ocurrido un error en el servicio de pokemon*********" + e.getMessage());
        } catch (Exception e) {
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
