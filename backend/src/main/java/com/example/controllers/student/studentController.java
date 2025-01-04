package com.example.controllers.student;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Response;
import com.example.dto.SubmissionResponse;
import com.example.model.Languages;
import com.example.model.Mcq;
import com.example.model.McqResponse;
import com.example.model.Programme;
import com.example.model.ProgrammeResponse;
import com.example.model.Student;
import com.example.service.admin.AdminService;
import com.example.service.language.LanguageService;
import com.example.service.mcq.McqService;
import com.example.service.mcqResponse.McqResponseService;
import com.example.service.programme.ProgrammeService;
import com.example.service.programmeResponse.ProgrammeResponseService;
import com.example.service.student.StudentService;
import com.example.service.submissions.SubmissionsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentservice;
    private final AdminService adminservice;
    private final McqService mcqService;
    private final ProgrammeService programmeService;
    private final LanguageService languageService;
    private final McqResponseService mcqResponseService;
    private final ProgrammeResponseService programmeResponseService;
    private final SubmissionsService submissionsService;

    public StudentController(StudentService studentservice, AdminService adminservice, McqService mcqService,
            McqResponseService mcqResponseService, ProgrammeResponseService programmeResponseService,ProgrammeService programmeService,LanguageService languageService,SubmissionsService submissionsService) {
        this.studentservice = studentservice;
        this.adminservice = adminservice;
        this.mcqService = mcqService;
        this.mcqResponseService = mcqResponseService;
        this.programmeResponseService = programmeResponseService;
        this.programmeService = programmeService;
        this.languageService = languageService;
        this.submissionsService = submissionsService;
    }

    @GetMapping("/students")
    public List<Student> students() {
        return studentservice.getStudents();
    }

    @PostMapping("/mcq/response")
    public ResponseEntity<Boolean> registerResponse(HttpServletRequest request, @RequestBody mcqResponseRegisterRequest response) {
        try {
            if (response == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            if(mcqResponseService.isResponseExist(studentId , response.questionId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            Student student = studentservice.getStudent(studentId);
            if (student == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            Mcq question = mcqService.getMcq(response.questionId());
            if (question == null)
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            if (response.response() == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            McqResponse mcqResponse = new McqResponse();
            mcqResponse.setMcq(question);
            mcqResponse.setResponse(response.response());
            mcqResponse.setStudent(student);

            if(response.response().equals(question.getCorrectAnswer())){
                mcqResponse.setTrue(true);
            }

            if (mcqResponseService.registerResponse(mcqResponse)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            } else {
                throw new Exception("Something wrong");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    record mcqResponseRegisterRequest(
            int questionId,
            String response) {
    }

    @PostMapping("/programme/response")
    public ResponseEntity<Boolean> registerResponse(HttpServletRequest request ,@RequestBody programmeResponseRequest response){
        try{
            if(response == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            if(response.response() == null || response.languageId() == 0 || response.questionId() == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            if(programmeResponseService.isResponseExist(studentId, response.questionId()) != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            Student student = studentservice.getStudent(studentId);
            if(student == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            Programme programme = programmeService.getProgramme(response.questionId());
            if(programme == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            Languages languages = languageService.getLanguage(response.languageId());
            if(languages == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            ProgrammeResponse programmeResponse = new ProgrammeResponse();

            programmeResponse.setLanguages(languages);
            programmeResponse.setStudent(student);
            programmeResponse.setProgramme(programme);
            
            if(programmeResponseService.registerProgrammeResponse(programmeResponse , response.response())){
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    record programmeResponseRequest(
        int questionId,
        String response, // base64
        int languageId
    ){};

    @PostMapping("/programme/submission")
    public ResponseEntity<List<SubmissionResponse>> getSubmission(HttpServletRequest request ,@RequestBody submissionRequest submission){
        try{
            if(submission.questionId() == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            ProgrammeResponse programmeResponse = programmeResponseService.isResponseExist(studentId, submission.questionId());
            
            if(programmeResponse == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            List<Response> tokens = submissionsService.getTokens(submission.questionId());
            if(tokens.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            
            List<SubmissionResponse> response = programmeResponseService.getSubmission(tokens);
            
            for(SubmissionResponse res : response) {
                if(res.stderr() != null){
                    programmeResponseService.setProgrammeResponse(programmeResponse , false);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }

            programmeResponseService.setProgrammeResponse(programmeResponse , true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    record submissionRequest(
        int questionId
    ){}

}
