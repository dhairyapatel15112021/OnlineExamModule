package com.example.controllers.admin;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


import com.example.model.Admin;
import com.example.model.Batch;
import com.example.model.College;
import com.example.model.Mcq;
import com.example.model.Programme;
import com.example.model.Question;
import com.example.model.Student;
import com.example.model.Test;
import com.example.model.Question.QuestionCategory;
import com.example.service.admin.AdminService;
import com.example.service.batch.BatchService;
import com.example.service.college.CollegeService;
import com.example.service.mcq.McqService;
import com.example.service.programme.ProgrammeService;
import com.example.service.student.StudentService;
import com.example.service.test.TestService;

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
    

    public AdminController(AdminService adminservice , StudentService studentservice,CollegeService collegeService,BatchService batchService,TestService testService,McqService mcqService,ProgrammeService programmeService) {
        this.adminservice = adminservice;
        this.studentservice = studentservice;
        this.collegeService = collegeService;
        this.batchService = batchService;
        this.testService = testService;
        this.mcqService = mcqService;
        this.programmeService = programmeService;
    }

    @GetMapping("/students")
    public List<Student> students(){
        return studentservice.getStudents();
    }

    // @PostMapping("/student")
    public Student student(@RequestBody studentRequest request){
        return studentservice.getStudent(request.emailId());
    }

    record studentRequest(String emailId){}

    @PostMapping("/student/register")
    public ResponseEntity<studentRegisterResponse> createStudent(@RequestBody List<studentRegisterRequest> studentRegisterRequest){
        ArrayList<studentRegisterRequest> responseUnsuceesfull  = new ArrayList<studentRegisterRequest>();
        ArrayList<studentRegisterRequest> responseSucessfull = new ArrayList<studentRegisterRequest>();
        studentRegisterResponse studentRegisterResponse = new studentRegisterResponse(responseSucessfull,responseUnsuceesfull);
  
            for(studentRegisterRequest stu : studentRegisterRequest){
                try{
                    if(studentservice.getStudent(stu.emailId()) != null || adminservice.getAdmin(stu.emailId()) != null || collegeService.getCollege(stu.emailId()) != null){
                        throw new Exception("Exist with this email id");
                    }
                    if(stu.mobileNumber().length() != 10){
                        throw new Exception("Mobile Number Length should be 10");
                    }
                    Student student = new Student();
                    student.setEmailId(stu.emailId());
                    student.setPassword(stu.password());
                    student.setName(stu.name());
                    student.setEnrollmentNumber(stu.enrollmentNumber());
                    student.setMobileNumber(stu.mobileNumber());
                   Boolean responseRegisterStudent = studentservice.createStudent(student, stu.batchId() , stu.clgId());
                    if(!responseRegisterStudent){
                        throw new Exception("Problem While saving the Student");
                    }
                    responseSucessfull.add(stu);
                }
                catch(Exception e){
                    responseUnsuceesfull.add(stu);
                }
            }
         
        return ResponseEntity.status(responseUnsuceesfull.size() == 0 ? HttpStatus.OK : HttpStatus.MULTI_STATUS)
                .body(studentRegisterResponse);
            
    }

    record studentRegisterRequest(String emailId,String password , String name , String enrollmentNumber , String mobileNumber , int batchId , int clgId) {}
    record studentRegisterResponse(ArrayList<studentRegisterRequest> success , ArrayList<studentRegisterRequest> failure){};

    @PostMapping("/clg/register")
    public ResponseEntity<String> createCollege(@RequestBody College clg){
        try{
            if(collegeService.createCollege(clg)){
                return new ResponseEntity<String>("College Created Succesfully", HttpStatus.OK);
            }
            throw new Exception("Something Went Wrong");
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/batch/create")
    public ResponseEntity<String> createBatch(@RequestBody Batch batch){
        try{
            if(batchService.createBatch(batch)){
                return new ResponseEntity<String>("Batch Created Succesfully", HttpStatus.OK);
            }
            throw new Exception("Something Went Wrong");
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/test/register")
    public ResponseEntity<String> createTest(HttpServletRequest request , @RequestBody testRegisterRequest testRegisterRequest){
        try{
            Test test = new Test();
            test.setTime(testRegisterRequest.time());
            test.setTitle(testRegisterRequest.title());
            test.setTotalApptitudeQuestion(testRegisterRequest.totalApptitudeQuestion());
            test.setTotalProgrammingQuestion(testRegisterRequest.totalProgrammingQuestion());
            test.setTotalTechnicalQuestion(testRegisterRequest.totalTechnicalQuestion());

            if(!testService.createTest(test , testRegisterRequest.batchId() , Integer.parseInt(request.getAttribute("id").toString()))){
                throw new Exception("Something Went Wrong");
            }

            return new ResponseEntity<String>("Test Registered", HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    record testRegisterRequest(String title , int totalApptitudeQuestion , int totalProgrammingQuestion , int totalTechnicalQuestion , LocalTime time , int batchId) {}

    @PostMapping("/mcq/register")
    @Transactional
    public ResponseEntity<mcqRegisterResponse> createMCQS(@RequestBody ArrayList<mcqRegisterRequest> mcqs){
        if(mcqs.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        int testId = mcqs.get(0).testId();
        Test test = testService.findTotalQuestion(testId);

        if(test == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ArrayList<mcqRegisterRequest> mcqUnsuceesfull = new ArrayList<>();
        ArrayList<mcqRegisterRequest> mcqSuceesfull = new ArrayList<>();
        mcqRegisterResponse mcqRegisterResponse = new mcqRegisterResponse(mcqSuceesfull, mcqUnsuceesfull);
        int mcqApptitudeCount = test.getTotalApptitudeQuestion();
        int mcqTechnicalCount = test.getTotalTechnicalQuestion();
        int apptitudeCount = mcqService.getAllMcqs(testId, QuestionCategory.Apptitude)  , technicalCount = mcqService.getAllMcqs(testId,QuestionCategory.Technical);

        for(mcqRegisterRequest mcq : mcqs){
            try{
                Mcq newMcqInstance = new Mcq();
                newMcqInstance.setCategory(mcq.category());
                newMcqInstance.setCorrectAnswer(mcq.correctAnswer());
                newMcqInstance.setDifficulty(mcq.difficulty());
                newMcqInstance.setNegativeMark(mcq.negativeMark());
                newMcqInstance.setPositiveMark(mcq.positiveMark());
                newMcqInstance.setOption1(mcq.option1());
                newMcqInstance.setOption2(mcq.option2());
                newMcqInstance.setTest(test);
                if(mcq.option3() != null) newMcqInstance.setOption3(mcq.option3());
                if(mcq.option4() != null) newMcqInstance.setOption4(mcq.option4());
                newMcqInstance.setQuestionDescription(mcq.questionDescription());

                if(mcq.category() == QuestionCategory.Apptitude && apptitudeCount < mcqApptitudeCount){
                    if(mcqService.createMcq(newMcqInstance)){
                        apptitudeCount += 1;
                        mcqSuceesfull.add(mcq);
                    }else{
                        mcqUnsuceesfull.add(mcq);
                    }  
                }

                else if(mcq.category() == QuestionCategory.Technical && technicalCount < mcqTechnicalCount){
                    if(mcqService.createMcq(newMcqInstance)){
                        technicalCount += 1;
                        mcqSuceesfull.add(mcq);
                    }else{
                        mcqUnsuceesfull.add(mcq);
                    }  
                }

                else{
                    mcqUnsuceesfull.add(mcq);
                }
            }
            catch(Exception e){
                mcqUnsuceesfull.add(mcq);
            }
        }
        return ResponseEntity.status(mcqUnsuceesfull.size() == 0 && mcqs.size() == mcqSuceesfull.size() ? HttpStatus.OK : HttpStatus.MULTI_STATUS)
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
        Question.Difficulty difficulty){};
    record mcqRegisterResponse(ArrayList<mcqRegisterRequest> success , ArrayList<mcqRegisterRequest> failure){};

    @PostMapping("/programme/register")
    public ResponseEntity<programmeRegisterResponse> createProgramme(@RequestBody ArrayList<programmeRegisterRequest> programmes){
        if(programmes.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Test test = testService.findTotalQuestion(programmes.get(0).testId());
        if(test == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        int programmeCount = programmeService.getAllProgrammesCount(test.getId());
        int testProgrammeCount = test.getTotalProgrammingQuestion();
        List<programmeRegisterRequest> programmeSucessfull = new ArrayList<>();
        List<programmeRegisterRequest> programmeUnsucessfull = new ArrayList<>();
        programmeRegisterResponse programmeRegisterResponse = new programmeRegisterResponse(programmeSucessfull, programmeUnsucessfull);
       
        for(programmeRegisterRequest programme : programmes){
            try{
                Programme newProgrammeInstance = new Programme();
                newProgrammeInstance.setCategory(programme.category());
                newProgrammeInstance.setDifficulty(programme.difficulty());
                newProgrammeInstance.setPositiveMark(0);
                newProgrammeInstance.setQuestionDescription(programme.questionDescription());
                newProgrammeInstance.setTest(test);
                if(newProgrammeInstance.getCategory() == QuestionCategory.Programming && programmeCount < testProgrammeCount){
                    if(programmeService.createProgramne(newProgrammeInstance)){
                        programmeSucessfull.add(programme);
                        programmeCount += 1;
                    }
                    else{
                        programmeUnsucessfull.add(programme);
                    }
                }
                else{
                    programmeUnsucessfull.add(programme);
                }
            }
            catch(Exception e){
                programmeUnsucessfull.add(programme);
            }
        }
        return ResponseEntity.status(programmeUnsucessfull.isEmpty() && programmeSucessfull.size() == programmes.size() ? HttpStatus.OK : HttpStatus.MULTI_STATUS)
                .body(programmeRegisterResponse);
    }

    record programmeRegisterRequest(
        int testId,
        Question.QuestionCategory category,
        Question.Difficulty difficulty,
        String questionDescription,
        float positiveMark
    ){}
    record programmeRegisterResponse(List<programmeRegisterRequest> success , List<programmeRegisterRequest> failure){};
}
