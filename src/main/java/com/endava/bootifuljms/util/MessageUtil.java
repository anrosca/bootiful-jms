package com.endava.bootifuljms.util;

import com.endava.bootifuljms.serpinski.SerpinskiRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtil {

    public static String createRequestMessageWithDepth(int depth) {
        SerpinskiRequest serpinskiRequest = new SerpinskiRequest();
        serpinskiRequest.setDepth(depth);
        return toJson(serpinskiRequest);
    }

    public static SerpinskiRequest parseRequestMessageFromString(String json) {
        try {
            return new ObjectMapper().readValue(json, SerpinskiRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toJson(SerpinskiRequest serpinskiRequest) {
        try {
            return new ObjectMapper().writeValueAsString(serpinskiRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
