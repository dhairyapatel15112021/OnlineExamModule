package com.example.service.programmeResponse;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;

import com.example.dto.ApiRequest;
import com.example.dto.ApiResponse;
import com.example.dto.Response;
import com.example.dto.SubmissionResponse;
import com.example.model.ProgrammeResponse;
import com.example.model.Submissions;
import com.example.model.Testcases;
import com.example.repository.programmeResponse.ProgrammeResponseRepository;
import com.example.service.json.JsonService;
import com.example.service.submissions.SubmissionsService;
import com.example.service.testcases.TestcasesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProgrammeResponseService {

    private final TestcasesService testcasesService;
    private final ProgrammeResponseRepository programmeResponseRepository;
    private final SubmissionsService submissionsService;
    private final JsonService jsonService;

    @Value("${api-key}")
    private String key;

    public ProgrammeResponseService(ProgrammeResponseRepository programmeResponseRepository,
            TestcasesService testcasesService, SubmissionsService submissionsService, JsonService jsonService) {
        this.programmeResponseRepository = programmeResponseRepository;
        this.testcasesService = testcasesService;
        this.submissionsService = submissionsService;
        this.jsonService = jsonService;
    }

    public List<ApiResponse> registerSubmission(ProgrammeResponse programmeResponse, String ProgrammeResponse) {
        try {
            List<Testcases> testcase = testcasesService.getTestcases(programmeResponse.getProgramme().getId());
            if (testcase.isEmpty()) {
                return null;
            }

            List<ApiRequest> submissions = new ArrayList<>();
            for (Testcases ts : testcase) {
                ApiRequest apiRequest = new ApiRequest(programmeResponse.getLanguages().getLanguageId(),
                        ProgrammeResponse, Base64.getEncoder().encodeToString(ts.getInput().getBytes()),
                        Base64.getEncoder().encodeToString(ts.getOutput().getBytes()));
                submissions.add(apiRequest);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String submissionsJson = objectMapper.writeValueAsString(submissions);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://judge0-extra-ce.p.rapidapi.com/submissions/batch?base64_encoded=true"))
                    .header("x-rapidapi-key", key)
                    .header("x-rapidapi-host", "judge0-extra-ce.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(
                            String.format("{\"submissions\":%s}", submissionsJson)))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());

            return jsonService.extractTokens(response.body());

        } catch (Exception e) {
            System.out.println("Something Went wrong in the programme response service " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public boolean registerProgrammeResponse(ProgrammeResponse programmeResponse, String response) {
        try {
            List<ApiResponse> tokens = registerSubmission(programmeResponse, response);
            programmeResponseRepository.save(programmeResponse);

            if (!tokens.isEmpty()) {
                List<Submissions> submissions = new ArrayList<>();

                tokens.forEach(tokenResponse -> {
                    Submissions submission = new Submissions();
                    submission.setProgrammeResponse(programmeResponse);
                    submission.setToken(tokenResponse.token());
                    submissions.add(submission);
                });

                if (submissionsService.createSubmission(submissions)) {
                    return true;
                }
                return false;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ProgrammeResponse isResponseExist(int studentId, int programmeId) {
        try {
            return programmeResponseRepository.findByStudentIdAndProgrammeId(studentId, programmeId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<SubmissionResponse> getSubmission(List<Response> token) {
        try {
            String requestToken = "";
            for (int i = 0; i < token.size(); i++) {
                requestToken += token.get(i).token() + (i != token.size() - 1 ? "," : "");
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(
                            "https://judge0-extra-ce.p.rapidapi.com/submissions/batch?tokens=%s&base64_encoded=true&fields=token,stdout,stderr,status_id,language_id",
                            requestToken)))
                    .header("x-rapidapi-key", key)
                    .header("x-rapidapi-host", "judge0-extra-ce.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());
                    
            return jsonService.extractSubmissionResponse(response.body());
        } catch (Exception e) {
            System.out.println(
                    "Something wrong in the get submission method in the program response service " + e.getMessage());
            return null;
        }
    }

    public void setProgrammeResponse(ProgrammeResponse programmeResponse, boolean bool) {
        programmeResponse.setTrue(bool);
        programmeResponseRepository.save(programmeResponse);
    }
}
