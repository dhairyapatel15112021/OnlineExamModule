package com.example.controllers.admin;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.example.dto.TestGet;
import com.example.model.Batch;
import com.example.model.College;
import com.example.model.Languages;
import com.example.model.Mcq;
import com.example.model.Programme;
import com.example.model.Question;
import com.example.model.Student;
import com.example.model.Test;
import com.example.model.Testcases;
import com.example.model.Teststatus;
import com.example.model.Question.QuestionCategory;
import com.example.model.Status.status;
import com.example.service.admin.AdminService;
import com.example.service.batch.BatchService;
import com.example.service.college.CollegeService;
import com.example.service.language.LanguageService;
import com.example.service.mcq.McqService;
import com.example.service.programme.ProgrammeService;
import com.example.service.student.StudentService;
import com.example.service.test.TestService;
import com.example.service.testcases.TestcasesService;
import com.example.service.teststatus.TestStatusService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminservice;
    private final StudentService studentservice;
    private final CollegeService collegeService;
    private final BatchService batchService;
    private final TestService testService;
    private final McqService mcqService;
    private final ProgrammeService programmeService;
    private final TestcasesService testcasesService;
    private final LanguageService languageService;
    private final TestStatusService testStatusService;

    public AdminController(AdminService adminservice, StudentService studentservice, CollegeService collegeService,
            BatchService batchService, TestService testService, McqService mcqService,
            ProgrammeService programmeService,TestcasesService testcasesService,LanguageService languageService,TestStatusService testStatusService) {
        this.adminservice = adminservice;
        this.studentservice = studentservice;
        this.collegeService = collegeService;
        this.batchService = batchService;
        this.testService = testService;
        this.mcqService = mcqService;
        this.programmeService = programmeService;
        this.testcasesService = testcasesService;
        this.languageService = languageService;
        this.testStatusService = testStatusService;
    }


    // @PostMapping("/student")
    public Student student(@RequestBody studentRequest request) {
        return studentservice.getStudent(request.emailId());
    }

    record studentRequest(String emailId) {
    }

    @GetMapping("/student/get")
    public ResponseEntity<getStudentResponse> getStudents(){
        try{
            List<Student> students = studentservice.getStudents();
            if(students.isEmpty()){
                throw new Exception("No Students");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new getStudentResponse(students));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getStudentResponse(List.of()));
        }
    }

    record getStudentResponse(List<Student> students){};

    @PostMapping("/student/register")
    public ResponseEntity<studentRegisterResponse> createStudent(
            @RequestBody List<studentRegisterRequest> studentRegisterRequest) {
        ArrayList<studentRegisterRequest> responseUnsuceesfull = new ArrayList<studentRegisterRequest>();
        ArrayList<studentRegisterRequest> responseSucessfull = new ArrayList<studentRegisterRequest>();
        studentRegisterResponse studentRegisterResponse = new studentRegisterResponse(responseSucessfull,
                responseUnsuceesfull);

        for (studentRegisterRequest stu : studentRegisterRequest) {
            try {
                if (studentservice.getStudent(stu.emailId()) != null || adminservice.getAdmin(stu.emailId()) != null
                        || collegeService.getCollege(stu.emailId()) != null) {
                    throw new Exception("Exist with this email id");
                }
                if (stu.mobileNumber().length() != 10) {
                    throw new Exception("Mobile Number Length should be 10");
                }
                Student student = new Student();
                student.setEmailId(stu.emailId());
                student.setPassword(stu.password());
                student.setName(stu.name());
                student.setEnrollmentNumber(stu.enrollmentNumber());
                student.setMobileNumber(stu.mobileNumber());
                Boolean responseRegisterStudent = studentservice.createStudent(student, stu.batchId(), stu.clgId());
                if (!responseRegisterStudent) {
                    throw new Exception("Problem While saving the Student");
                }
                responseSucessfull.add(stu);
            } catch (Exception e) {
                responseUnsuceesfull.add(stu);
            }
        }

        return ResponseEntity.status(responseUnsuceesfull.size() == 0 ? HttpStatus.OK : HttpStatus.MULTI_STATUS)
                .body(studentRegisterResponse);

    }

    record studentRegisterRequest(String emailId, String password, String name, String enrollmentNumber,
            String mobileNumber, int batchId, int clgId) {
    }

    record studentRegisterResponse(ArrayList<studentRegisterRequest> success,
            ArrayList<studentRegisterRequest> failure) {
    };

    @PostMapping("/clg/register")
    public ResponseEntity<clgRegisterResponse> createCollege(@RequestBody College clg) {
        try {
            College college = collegeService.createCollege(clg);
            if (college != null) {
                return new ResponseEntity<>(new clgRegisterResponse(college.getId()), HttpStatus.OK);
            }
            throw new Exception("Something Went Wrong");
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    record clgRegisterResponse(int id){}

    @PostMapping("/batch/create")
    public ResponseEntity<batchCreateResponse> createBatch(@RequestBody Batch batch) {
        try {
            if(batchService.isBatchExist(batch.getYear())){
                throw new Exception("Already Batch Existed");
            }
            Batch createdBatch = batchService.createBatch(batch);
            if (createdBatch != null) {
                return new ResponseEntity<>(new batchCreateResponse(createdBatch.getId()), HttpStatus.OK);
            }
            throw new Exception("Something Went Wrong");
        } catch (Exception e) {
            return new ResponseEntity<>(new batchCreateResponse(0), HttpStatus.BAD_REQUEST);
        }
    }

    record batchCreateResponse(int id) {};

    @GetMapping("/batch/get")
    public ResponseEntity<getBatchResponse> getBatches(){
        try{
            List<Batch> batches = batchService.getBatches();
            if(batches.isEmpty()){
                throw new Exception("No Batches");
            }
            return new ResponseEntity<getBatchResponse>(new getBatchResponse(batches), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<getBatchResponse>(new getBatchResponse(List.of()), HttpStatus.BAD_REQUEST);
        }
    }

    record getBatchResponse(List<Batch> batch) {};

    @GetMapping("/tests/get")
    public ResponseEntity<getTestResponse> getTests(){
        try{
            List<Test> tests = testService.getTests();
            if(tests.isEmpty()){
                throw new Exception("No Tests");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new getTestResponse(tests.stream().map(test -> new TestGet(test.getId(), test.getTitle(), test.getTotalApptitudeQuestion(), test.getTotalTechnicalQuestion(), test.getTotalProgrammingQuestion(), test.getPassingPercentage(), test.getTime(), test.getAdmin().getId(), test.getBatch().getId())).toList()));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getTestResponse(List.of()));
        }
    }
    
    record getTestResponse(List<TestGet> tests){}
    
    @GetMapping("/college/get")
    public ResponseEntity<getCollegeResponse> getColleges(){
        try{
            List<College> colleges = collegeService.getCollges();
            if(colleges.isEmpty()){
                throw new Exception("No Collges Found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new getCollegeResponse(colleges));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getCollegeResponse(null));
        }
    }

    record getCollegeResponse(List<College> clg){};

    @PostMapping("/test/register")
    public ResponseEntity<testRegisterResponse> createTest(HttpServletRequest request,
            @RequestBody testRegisterRequest testRegisterRequest) {
        try {
            Test test = new Test();
            test.setTime(testRegisterRequest.time());
            test.setTitle(testRegisterRequest.title());
            test.setTotalApptitudeQuestion(testRegisterRequest.totalApptitudeQuestion());
            test.setTotalProgrammingQuestion(testRegisterRequest.totalProgrammingQuestion());
            test.setTotalTechnicalQuestion(testRegisterRequest.totalTechnicalQuestion());

            Test testCreated = testService.createTest(test, testRegisterRequest.batchId(),
            Integer.parseInt(request.getAttribute("id").toString()));
            if (testCreated == null) {
                throw new Exception("Something Went Wrong");
            }
            return new ResponseEntity<>(new testRegisterResponse(testCreated.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new testRegisterResponse(0), HttpStatus.BAD_REQUEST);
        }
    }

    record testRegisterResponse(int id){};

    record testRegisterRequest(String title, int totalApptitudeQuestion, int totalProgrammingQuestion,
            int totalTechnicalQuestion, LocalTime time, int batchId) {
    }

    @PostMapping("/mcq/register")
    @Transactional
    public ResponseEntity<mcqRegisterResponse> createMCQS(@RequestBody ArrayList<mcqRegisterRequest> mcqs) {
        if (mcqs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        int testId = mcqs.get(0).testId();
        Test test = testService.findTotalQuestion(testId);

        if (test == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        if(test.getTestStatus() != status.NotStarted) 
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        ArrayList<mcqRegisterRequest> mcqUnsuceesfull = new ArrayList<>();
        ArrayList<McqRegister> mcqSuceesfull = new ArrayList<>();
        mcqRegisterResponse mcqRegisterResponse = new mcqRegisterResponse(mcqSuceesfull, mcqUnsuceesfull);
        int mcqApptitudeCount = test.getTotalApptitudeQuestion();
        int mcqTechnicalCount = test.getTotalTechnicalQuestion();
        int apptitudeCount = mcqService.getAllMcqs(testId, QuestionCategory.Apptitude),
                technicalCount = mcqService.getAllMcqs(testId, QuestionCategory.Technical);

        for (mcqRegisterRequest mcq : mcqs) {
            try {
                Mcq newMcqInstance = new Mcq();
                newMcqInstance.setCategory(mcq.category());
                newMcqInstance.setCorrectAnswer(mcq.correctAnswer());
                newMcqInstance.setDifficulty(mcq.difficulty());
                newMcqInstance.setNegativeMark(mcq.negativeMark());
                newMcqInstance.setPositiveMark(mcq.positiveMark());
                newMcqInstance.setOption1(mcq.option1());
                newMcqInstance.setOption2(mcq.option2());
                newMcqInstance.setTest(test);
                if (mcq.option3() != null)
                    newMcqInstance.setOption3(mcq.option3());
                if (mcq.option4() != null)
                    newMcqInstance.setOption4(mcq.option4());
                newMcqInstance.setQuestionDescription(mcq.questionDescription());

                if (mcq.category() == QuestionCategory.Apptitude && apptitudeCount < mcqApptitudeCount) {
                    Mcq mcqCreated = mcqService.createMcq(newMcqInstance);
                    if (mcqCreated != null) {
                        apptitudeCount += 1;
                        mcqSuceesfull.add(
                            new McqRegister(mcqCreated.getId(), testId, mcqCreated.getQuestionDescription(), mcqCreated.getOption1(), mcqCreated.getOption2(), mcqCreated.getOption3(), mcq.option4(), mcqCreated.getCategory(), mcqCreated.getDifficulty(), mcqCreated.getPositiveMark(), mcqCreated.getNegativeMark(), mcqCreated.getCorrectAnswer())
                        );
                    } else {
                        mcqUnsuceesfull.add(mcq);
                    }
                }

                else if (mcq.category() == QuestionCategory.Technical && technicalCount < mcqTechnicalCount) {
                    Mcq mcqCreated = mcqService.createMcq(newMcqInstance);
                    if (mcqCreated != null) {
                        technicalCount += 1;
                        mcqSuceesfull.add(
                            new McqRegister(mcqCreated.getId(), testId, mcqCreated.getQuestionDescription(), mcqCreated.getOption1(), mcqCreated.getOption2(), mcqCreated.getOption3(), mcq.option4(), mcqCreated.getCategory(), mcqCreated.getDifficulty(), mcqCreated.getPositiveMark(), mcqCreated.getNegativeMark(), mcqCreated.getCorrectAnswer())
                        );
                    } else {
                        mcqUnsuceesfull.add(mcq);
                    }
                }

                else {
                    mcqUnsuceesfull.add(mcq);
                }
            } catch (Exception e) {
                mcqUnsuceesfull.add(mcq);
            }
        }
        return ResponseEntity
                .status(mcqUnsuceesfull.size() == 0 && mcqs.size() == mcqSuceesfull.size() ? HttpStatus.OK
                        : HttpStatus.MULTI_STATUS)
                .body(mcqRegisterResponse);
    }

    record mcqRegisterRequest(
            int testId,
            String questionDescription,
            String option1,
            String option2,
            String option3,
            String option4,
            Question.QuestionCategory category,
            float positiveMark,
            float negativeMark,
            String correctAnswer,
            Question.Difficulty difficulty) {
    };

    record mcqRegisterResponse(ArrayList<McqRegister> success, ArrayList<mcqRegisterRequest> failure) {
    };

    @GetMapping("/mcq/get/{id}")
    public ResponseEntity<getMcqResponse> getMcqs(@PathVariable("id") int id){
        try{
            List<Mcq> mcqs = mcqService.getAllMcqs(id);
            if(mcqs.isEmpty()){
                throw new Exception("No Mcqs Found");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                        new getMcqResponse(mcqs.stream().map(mcq -> new McqRegister(mcq.getId(), mcq.getTest().getId(), mcq.getQuestionDescription(), mcq.getOption1(), mcq.getOption2(), mcq.getOption3(), mcq.getOption4(), mcq.getCategory(), mcq.getDifficulty(), mcq.getPositiveMark(), mcq.getNegativeMark(), mcq.getCorrectAnswer())).toList())
                    );
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getMcqResponse(List.of()));
        }
    }

    record getMcqResponse(List<McqRegister> mcqs){}

    @GetMapping("/programme/get/{id}")
    public ResponseEntity<getProgrammeResponse> getProgrammes(@PathVariable("id") int id){
        try{
            List<Programme> programmes = programmeService.getProgrammes(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(new getProgrammeResponse(programmes.stream().map(programme -> new ProgrammeRegister(programme.getId(), programme.getQuestionDescription(), programme.getPositiveMark(), programme.getDifficulty(), programme.getCategory(), id)).toList()));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new getProgrammeResponse(List.of()));
        }
    }

    record getProgrammeResponse(List<ProgrammeRegister> programmes){};

    @PostMapping("/programme/register")
    public ResponseEntity<programmeRegisterResponse> createProgramme(
            @RequestBody ArrayList<programmeRegisterRequest> programmes) {
        if (programmes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Test test = testService.findTotalQuestion(programmes.get(0).testId());
        if (test == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

       if(test.getTestStatus() == status.Completed || test.getTestStatus() == status.Running)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        int programmeCount = programmeService.getAllProgrammesCount(test.getId());
        int testProgrammeCount = test.getTotalProgrammingQuestion();
        List<ProgrammeRegister> programmeSucessfull = new ArrayList<>();
        List<programmeRegisterRequest> programmeUnsucessfull = new ArrayList<>();
        programmeRegisterResponse programmeRegisterResponse = new programmeRegisterResponse(programmeSucessfull,
                programmeUnsucessfull);

        for (programmeRegisterRequest programme : programmes) {
            try {
                Programme newProgrammeInstance = new Programme();
                newProgrammeInstance.setCategory(programme.category());
                newProgrammeInstance.setDifficulty(programme.difficulty());
                newProgrammeInstance.setPositiveMark(programme.positiveMark());
                newProgrammeInstance.setQuestionDescription(programme.questionDescription());
                newProgrammeInstance.setTest(test);
                if (newProgrammeInstance.getCategory() == QuestionCategory.Programming
                        && programmeCount < testProgrammeCount) {
                            Programme programmeCreated = programmeService.createProgramne(newProgrammeInstance);
                    if (programmeCreated != null) {
                        programmeSucessfull.add(new ProgrammeRegister(programmeCreated.getId(), programmeCreated.getQuestionDescription(), programmeCreated.getPositiveMark(), programmeCreated.getDifficulty(), programmeCreated.getCategory(), programmeCreated.getTest().getId()));
                        programmeCount += 1;
                    } else {
                        programmeUnsucessfull.add(programme);
                    }
                } else {
                    programmeUnsucessfull.add(programme);
                }
            } catch (Exception e) {
                programmeUnsucessfull.add(programme);
            }
        }
        return ResponseEntity
                .status(programmeUnsucessfull.isEmpty() && programmeSucessfull.size() == programmes.size()
                        ? HttpStatus.OK
                        : HttpStatus.MULTI_STATUS)
                .body(programmeRegisterResponse);
    }

    record programmeRegisterRequest(
            int testId,
            Question.QuestionCategory category,
            Question.Difficulty difficulty,
            String questionDescription,
            float positiveMark) {
    }

    record programmeRegisterResponse(List<ProgrammeRegister> success, List<programmeRegisterRequest> failure) {
    };

    @PostMapping("/testcases/register")
    public ResponseEntity<testcasesRegisterResponse> createTestcases(
            @RequestBody ArrayList<testcasesRegisterRequest> testcases) {
        if (testcases.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        List<testcasesRegisterRequest> testcasesSuccesful = new ArrayList<>();
        List<testcasesRegisterRequest> testcasesUnsuccesful = new ArrayList<>();
        testcasesRegisterResponse testcasesResponse = new testcasesRegisterResponse(testcasesSuccesful,
                testcasesUnsuccesful);

        for (testcasesRegisterRequest testcase : testcases) {
            try {
                Programme programme = programmeService.getProgramme(testcase.programmeId());
                if ( programme == null) throw new Exception("Programme Id is wrong");
                if (programme.getTest().getTestStatus() == status.Running || programme.getTest().getTestStatus() == status.Completed)
                    throw new Exception("Test is Running or Completed");

                Testcases newTestcaseInstance = new Testcases();
                newTestcaseInstance.setInput(testcase.input());
                newTestcaseInstance.setOutput(testcase.output());
                newTestcaseInstance.setProgramme(programme);
                if (testcasesService.createTestcases(newTestcaseInstance)) { 
                    testcasesSuccesful.add(testcase);
                }
                else{
                    testcasesUnsuccesful.add(testcase);
                }
            } catch 
            (Exception e) {
                testcasesUnsuccesful.add(testcase);
            }
        }

        return ResponseEntity
                .status(testcasesUnsuccesful.isEmpty() && testcasesSuccesful.size() == testcases.size() ? HttpStatus.OK
                        : HttpStatus.MULTI_STATUS)
                .body(testcasesResponse);
    }

    record testcasesRegisterRequest(
            int programmeId,
            String input,
            String output) {
    }

    record testcasesRegisterResponse(List<testcasesRegisterRequest> success, List<testcasesRegisterRequest> failure) {
    };

    @PostMapping("/register/languages")
    public ResponseEntity<Boolean> registerLanguages(@RequestBody List<Languages> languages){
        try{
            if(languages == null){
                throw new Exception("Something Went Wrong");
            }
            if(languageService.registerLanguages(languages)){
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
            throw new Exception("Something Went Wrong");
            
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
