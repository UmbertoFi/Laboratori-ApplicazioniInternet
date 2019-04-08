package it.polito.ai.lab2.demo.Entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.polito.ai.lab2.demo.Entity.Fermata;
import it.polito.ai.lab2.demo.Entity.Linea;


import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DeserializationL extends StdDeserializer<Linea> {

    public DeserializationL(){
        this(null);
    }

    public DeserializationL(Class<?> vc){
        super(vc);
    }

    @Override
    public Linea deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException{

        JsonNode node = jp.getCodec().readTree(jp);

        Integer id_linea = (Integer) node.get("id").asInt();
        String nome_linea = node.get("nome").asText();
        String amm = node.get("amministratore").asText();
        Iterator<JsonNode> fermate=node.get("fermate").elements();
        Linea bus= Linea.builder()
                        .id(id_linea)
                        .amministratore(amm)
                        .nome(nome_linea)
                        .build();
        Set<Fermata> insieme = new HashSet<Fermata>();

        while(fermate.hasNext()!=false){
            JsonNode f=fermate.next();
            Integer  id_fermata = (Integer) f.get("id").asInt();
            String nome_fermata = f.get("nome").asText();
            Integer  n_s = (Integer) f.get("n_seq").asInt();
            Integer  n_linea = (Integer) f.get("linea").asInt();
            String andata = f.get("ora_andata").asText();
            String ritorno = f.get("ora_ritorno").asText();

            Fermata stop=Fermata.builder()
                    .id(id_fermata)
                    .nome(nome_fermata)
                    .n_seq(n_s)
                    .linea(bus)
                    .ora_andata(andata)
                    .ora_ritorno(ritorno)
                    .build();

            insieme.add(stop);
        }

        bus.setFermate(insieme);

        return bus;
    }


}
