package com.example.controllers.student;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.McqRegister;
import com.example.dto.ProgrammeRegister;
import com.example.dto.Response;
import com.example.dto.SubmissionResponse;
import com.example.dto.TestGet;
import com.example.dto.TestcasesGet;
import com.example.dto.TeststatusGet;
import com.example.model.Languages;
import com.example.model.Mcq;
import com.example.model.McqResponse;
import com.example.model.Programme;
import com.example.model.ProgrammeResponse;
import com.example.model.Question;
import com.example.model.Status.status;
import com.example.model.Student;
import com.example.model.Test;
import com.example.model.Testcases;
import com.example.model.Teststatus;
import com.example.model.Question.QuestionCategory;
import com.example.service.admin.AdminService;
import com.example.service.language.LanguageService;
import com.example.service.mcq.McqService;
import com.example.service.mcqResponse.McqResponseService;
import com.example.service.programme.ProgrammeService;
import com.example.service.programmeResponse.ProgrammeResponseService;
import com.example.service.student.StudentService;
import com.example.service.submissions.SubmissionsService;
import com.example.service.test.TestService;
import com.example.service.testcases.TestcasesService;
import com.example.service.teststatus.TestStatusService;

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
    private final TestService testService;
    private final TestcasesService testcasesService;
    private final TestStatusService testStatusService;

    public StudentController(StudentService studentservice, AdminService adminservice, McqService mcqService,
            McqResponseService mcqResponseService, ProgrammeResponseService programmeResponseService,
            ProgrammeService programmeService, LanguageService languageService, SubmissionsService submissionsService,
            TestService testService, TestcasesService testcasesService, TestStatusService testStatusService) {
        this.studentservice = studentservice;
        this.adminservice = adminservice;
        this.mcqService = mcqService;
        this.mcqResponseService = mcqResponseService;
        this.programmeResponseService = programmeResponseService;
        this.programmeService = programmeService;
        this.languageService = languageService;
        this.submissionsService = submissionsService;
        this.testService = testService;
        this.testcasesService = testcasesService;
        this.testStatusService = testStatusService;
    }

    @GetMapping("/language/get")
    public ResponseEntity<getLanguageResponse> getLanguage() {
        try {
            List<Languages> languages = languageService.getLanguage();
            if (languages.isEmpty()) {
                throw new Exception("No Language Found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new getLanguageResponse(languages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getLanguageResponse(List.of()));
        }
    }

    record getLanguageResponse(List<Languages> languages) {
    }

    @GetMapping("/mcq/get/{id}")
    public ResponseEntity<getMcqResponse> getMcqs(@PathVariable("id") int id) {
        try {
            List<Mcq> mcqs = mcqService.getAllMcqs(id);
            if (mcqs.isEmpty()) {
                throw new Exception("No Mcqs Found");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new getMcqResponse(mcqs.stream()
                                    .map(mcq -> new McqRegister(mcq.getId(), mcq.getTest().getId(),
                                            mcq.getQuestionDescription(), mcq.getOption1(), mcq.getOption2(),
                                            mcq.getOption3(), mcq.getOption4(), mcq.getCategory(), mcq.getDifficulty(),
                                            mcq.getPositiveMark(), mcq.getNegativeMark(), mcq.getCorrectAnswer()))
                                    .toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getMcqResponse(List.of()));
        }
    }

    record getMcqResponse(List<McqRegister> mcqs) {
    }

    @GetMapping("/programme/get/{id}")
    public ResponseEntity<getProgrammeResponse> getProgrammes(@PathVariable("id") int id) {
        try {
            List<Programme> programmes = programmeService.getProgrammes(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new getProgrammeResponse(programmes.stream()
                            .map(programme -> new ProgrammeRegister(programme.getId(),
                                    programme.getQuestionDescription(), programme.getPositiveMark(),
                                    programme.getDifficulty(), programme.getCategory(), id))
                            .toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getProgrammeResponse(List.of()));
        }
    }

    record getProgrammeResponse(List<ProgrammeRegister> programmes) {
    };

    @GetMapping("/tests/get")
    public ResponseEntity<getTestResponse> getTests(HttpServletRequest request) {
        try {
            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            List<Test> tests = testService.getTests(studentId);
            if (tests.isEmpty()) {
                throw new Exception("No Tests");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new getTestResponse(tests.stream()
                            .map(test -> new TestGet(test.getId(), test.getTitle(), test.getTotalApptitudeQuestion(),
                                    test.getTotalTechnicalQuestion(), test.getTotalProgrammingQuestion(),
                                    test.getPassingPercentage(), test.getTime(), test.getAdmin().getId(),
                                    test.getBatch().getId()))
                            .toList()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getTestResponse(List.of()));
        }
    }

    record getTestResponse(List<TestGet> tests) {
    }

    @GetMapping("/students")
    public List<Student> students() {
        return studentservice.getStudents();
    }

    @GetMapping("/testcases/get/{id}")
    public ResponseEntity<getTestcasesResponse> getTestcases(@PathVariable("id") int id) {
        try {
            List<Testcases> testcases = testcasesService.getTestcases(id);
            if (testcases.isEmpty()) {
                throw new Exception("No testcases");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new getTestcasesResponse(
                    testcases.stream().map(testcase -> new TestcasesGet(testcase.getId(), id, testcase.getInput(),
                            testcase.getOutput())).toList()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getTestcasesResponse(List.of()));
        }
    }

    record getTestcasesResponse(List<TestcasesGet> testcases) {
    }

    @PostMapping("/test/start/{testId}")
    @Transactional
    public ResponseEntity<Boolean> startTestRegister(HttpServletRequest request, @PathVariable("testId") int testId) {
        try {
            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            Student student = studentservice.getStudent(studentId);
            if (student == null)
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(false);

            Test test = testService.getTest(testId);
            if (test == null)
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(false);
            
            Teststatus testStatus = new Teststatus();
            testStatus.setStudent(student);
            testStatus.setTest(test);
            testStatus.setTestStatus(status.Running);

            if(test.getTestStatus() != status.Running){
                testService.saveTest(test);
                test.setTestStatus(status.Running);
            }
            
            if (testStatusService.startTest(testStatus) != null)
                return ResponseEntity.status(HttpStatus.OK).body(true);

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(false);
        }
    }

    @PostMapping("/mcq/response")
    public ResponseEntity<Boolean> registerResponse(HttpServletRequest request,
            @RequestBody mcqResponseRegisterRequest response) {
        try {
            if (response == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            if (mcqResponseService.isResponseExist(studentId, response.questionId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            Student student = studentservice.getStudent(studentId);
            if (student == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            Mcq question = mcqService.getMcq(response.questionId());
            if (question == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            Teststatus Status = testStatusService.getTeststatus(studentId, question.getTest().getId());
            if (Status.getTestStatus() != status.Running) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            if (response.response() == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            McqResponse mcqResponse = new McqResponse();
            mcqResponse.setMcq(question);
            mcqResponse.setResponse(response.response());
            mcqResponse.setStudent(student);

            if (response.response().equals(question.getCorrectAnswer())) {
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
    public ResponseEntity<Boolean> registerResponse(HttpServletRequest request,
            @RequestBody programmeResponseRequest response) {
        try {
            if (response == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            if (response.response() == null || response.languageId() == 0 || response.questionId() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            if (programmeResponseService.isResponseExist(studentId, response.questionId()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            Student student = studentservice.getStudent(studentId);
            if (student == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            Programme programme = programmeService.getProgramme(response.questionId());
            if (programme == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            Teststatus Status = testStatusService.getTeststatus(studentId, programme.getTest().getId());
            if (Status.getTestStatus() != status.Running) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }
           
            Languages languages = languageService.getLanguage(response.languageId());
            if (languages == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

            ProgrammeResponse programmeResponse = new ProgrammeResponse();

            programmeResponse.setLanguages(languages);
            programmeResponse.setStudent(student);
            programmeResponse.setProgramme(programme);

            if (programmeResponseService.registerProgrammeResponse(programmeResponse, response.response())) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    record programmeResponseRequest(
            int questionId,
            String response, // base64
            int languageId) {
    };

    @PostMapping("/programme/submission")
    public ResponseEntity<List<SubmissionResponse>> getSubmission(HttpServletRequest request,
            @RequestBody submissionRequest submission) {
        try {
            if (submission.questionId() == 0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            ProgrammeResponse programmeResponse = programmeResponseService.isResponseExist(studentId,
                    submission.questionId());

            if (programmeResponse == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            List<Response> tokens = submissionsService.getTokens(submission.questionId());
            if (tokens.isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            List<SubmissionResponse> response = programmeResponseService.getSubmission(tokens);

            for (SubmissionResponse res : response) {
                if (res.stderr() != null) {
                    programmeResponseService.setProgrammeResponse(programmeResponse, false);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }

            programmeResponseService.setProgrammeResponse(programmeResponse, true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    record submissionRequest(
            int questionId) {
    }

    @PostMapping("/test/end/{testId}")
    @Transactional
    public ResponseEntity<TeststatusGet> endTestRegister(HttpServletRequest request, @PathVariable("testId") int testId , @RequestBody endTestRegister endTest){
        try{
            int studentId = Integer.parseInt(request.getAttribute("id").toString());

            Test test = testService.getTest(testId);
            if (test == null)
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);

            for(mcqResponseRegisterRequest mcq : endTest.mcqs()){
                registerResponse(request, mcq);
            }
            for(programmeResponseRequest programme : endTest.programmes()){
                registerResponse(request, programme);
            }
            
            double apptitudeQuestionMark = 0;
            double technicalQuestionMark = 0;
            double programminQuestionMark = 0;

            Teststatus Status = testStatusService.getTeststatus(studentId, testId);
            List<McqResponse> mcqsResponse = mcqResponseService.getAllMcqs(studentId,testId);
            List<ProgrammeResponse> programmeResponses = programmeResponseService.getAllProgrammes(studentId,testId);

            if(!mcqsResponse.isEmpty()){
                for(McqResponse mcqRes : mcqsResponse){
                    if(mcqRes.getResponse() != null && mcqRes.isTrue()){
                        if(mcqRes.getMcq().getCategory() == QuestionCategory.Apptitude) apptitudeQuestionMark += mcqRes.getMcq().getNegativeMark();
                        if(mcqRes.getMcq().getCategory() == QuestionCategory.Technical) technicalQuestionMark += mcqRes.getMcq().getNegativeMark();
                    }
                    else if(mcqRes.getResponse() != null){
                        if(mcqRes.getMcq().getCategory() == QuestionCategory.Apptitude) apptitudeQuestionMark -= mcqRes.getMcq().getNegativeMark();
                        if(mcqRes.getMcq().getCategory() == QuestionCategory.Technical) technicalQuestionMark -= mcqRes.getMcq().getNegativeMark();
                    }
                }
            }

            for(ProgrammeResponse proRes : programmeResponses){
                if(proRes.isTrue()){
                    programminQuestionMark += proRes.getProgramme().getPositiveMark();
                }
            }
            
            test.setTestStatus(status.Completed);
            Status.setTestStatus(status.Completed);
            Status.setApptitudeMarks(apptitudeQuestionMark);
            Status.setProgrammingMarks(programminQuestionMark);
            Status.setTechnicalMarks(technicalQuestionMark);
            testService.saveTest(test);
            Teststatus savedStatus = testStatusService.saveTestStatus(Status);

            return ResponseEntity.status(HttpStatus.OK).body(
                new TeststatusGet(savedStatus.getId(), savedStatus.getTestStatus(), testId, studentId, apptitudeQuestionMark, technicalQuestionMark, programminQuestionMark)
            );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

    record endTestRegister(List<mcqResponseRegisterRequest> mcqs , List<programmeResponseRequest> programmes){}
    
    @PostMapping("/test/result")
    public ResponseEntity<List<TeststatusGet>> getResults(HttpServletRequest request){
        try{
            int studentId = Integer.parseInt(request.getAttribute("id").toString());
            List<Teststatus> status = testStatusService.getTeststatus(studentId);
            if(status.isEmpty()){
                throw new Exception("Not Attempted");
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                status.stream().map(s -> new TeststatusGet(s.getId(), s.getTestStatus(), s.getTest().getId(), studentId, s.getApptitudeMarks(),s.getTechnicalMarks(), s.getProgrammingMarks())).toList()
            );
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }
}
