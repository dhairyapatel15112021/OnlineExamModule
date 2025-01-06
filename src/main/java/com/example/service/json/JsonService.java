package com.example.service.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.ApiResponse;
import com.example.dto.SubmissionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonService {

    public List<ApiResponse> extractTokens(String responseBody) {
        List<ApiResponse> tokens = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            for (JsonNode node : root) {
                String token = node.path("token").asText();
                tokens.add(new ApiResponse(token));
            }
        } catch (Exception e) {
            System.out.println("something went wrong in the progrmme service extract token");
            e.printStackTrace();
        }
        return tokens;
    }

    public List<SubmissionResponse> extractSubmissionResponse(String responseBody) {
        List<SubmissionResponse> response = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode submissions = mapper.readTree(responseBody).path("submissions");
            // {"submissions":[{"language_id":12,"stdout":"ZmFsc2UK","status_id":3,"stderr":null,"token":"5e917d51-3625-48db-9791-af75e8c6cc90"},{"language_id":12,"stdout":"dHJ1ZQo=","status_id":3,"stderr":null,"token":"a9dacc2a-7bb1-4624-83df-54f2b14521cc"}]}
            for (JsonNode node : submissions) {
                response.add(
                        new SubmissionResponse(
                                node.path("language_id").asInt(),
                                node.path("stdout").asText(),
                                node.path("status_id").asInt(),
                                node.path("stderr").asText(null),
                                node.path("token").asText()));
            }
        } catch (Exception e) {
            System.out.println("something went wrong in the progrmme service extract token");
            e.printStackTrace();
            return null;
        }
        return response;
    }
}
