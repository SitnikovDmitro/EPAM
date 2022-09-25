package com.ra.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.model.entity.ChequeLine;
import com.ra.enums.Lang;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class provides localization of the site
 */
public class TextService {
    private static TextService instance;
    private final HashMap<String, String> ruMap = new HashMap<>();
    private final HashMap<String, String> enMap = new HashMap<>();
    private final HashMap<String, String> ukMap = new HashMap<>();


    private TextService() {
        try {
            JsonNode node = new ObjectMapper().readTree(getClass().getClassLoader().getResource("lang.json"));
            node.get("en").fields().forEachRemaining((entry) -> enMap.put(entry.getKey(), entry.getValue().asText()));
            node.get("ru").fields().forEachRemaining((entry) -> ruMap.put(entry.getKey(), entry.getValue().asText()));
            node.get("uk").fields().forEachRemaining((entry) -> ukMap.put(entry.getKey(), entry.getValue().asText()));
        } catch (IOException e) {
            throw new Error("Cannot load resource (lang.json)", e);
        }
    }

    /**
     * Returns translated phrase
     * @param key key of word or phrase
     * @param lang target language to translate
     * @return translated phrase
     */
    public String translate(String key, Lang lang) {
        String result = lang==Lang.RU ? ruMap.get(key) : lang==Lang.UK ? ukMap.get(key) : enMap.get(key);
        if (result==null) return "Text not found for : "+key;
        return result;
    }


    /**
     * Returns string representation of price
     * @param price price in cents
     * @return representation
     */
    public String formatPrice(int price) {
        String txt = Integer.toString(price);
        while (txt.length() < 3) txt = "0" + txt;
        return txt.substring(0, txt.length()-2)+"."+txt.substring(txt.length()-2);
    }

    /**
     * Returns string representation of weight
     * @param weight price in grams
     * @return representation
     */
    public String formatWeight(int weight) {
        String txt = Integer.toString(weight);
        while (txt.length() < 4) txt = "0" + txt;
        return txt.substring(0, txt.length()-3)+"."+txt.substring(txt.length()-3);
    }

    /**
     * Returns string representation of cheque line
     * @param chequeLine given line of cheque
     * @param lang language
     * @return representation
     */
    public String formatChequeLine(ChequeLine chequeLine, Lang lang) {
        if (chequeLine == null) return "NULL";
        String result = chequeLine.getProduct().getTitle()+" ("+chequeLine.getProduct().getCode()+") - ";
        if (chequeLine.getProduct().isCountable()) {
            result += chequeLine.getAmount() + " "+translate("pieces", lang)+", ";
            result += formatPrice(chequeLine.getProduct().getPrice()*chequeLine.getAmount())+" "+translate("dollars", lang);
        } else {
            result += formatWeight(chequeLine.getAmount()) + " "+translate("kilograms", lang)+", ";
            result += formatPrice(chequeLine.getProduct().getPrice()*chequeLine.getAmount()/1000)+" "+translate("dollars", lang);
        }
        return result;
    }



    public static TextService getInstance() {
        if (instance == null) instance = new TextService();
        return instance;
    }
}
