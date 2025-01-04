package com.example.dto;

public record ApiRequest(int language_id, String source_code, String stdin, String expected_output) {
    
}
