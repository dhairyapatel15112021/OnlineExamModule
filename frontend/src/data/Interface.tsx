export interface BatchInterface{
    year: string,
    id : number
}

export interface TestInterface{
    title : string, 
    totalApptitudeQuestion : number, 
    totalProgrammingQuestion : number,
    totalTechnicalQuestion : number, 
    time : string, 
    batchId : number,
    id : number,
    passingPercentage? : number
}

export interface data{
    file : File,
    url : string,
    fields : object[],
}

export interface CollegeInterface{
    emailId: string,
    name: string,
    address: string,
    contactNumber: string,
    id: number
}

export interface StudentInterface{
    emailId : string,
    password : string,
    name : string,
    enrollmentNumber : string,
    mobileNumber : string,
    batchId : number,
    clgId : number,
    id : number
}
  
export interface backendData {
    url: string,
    method: string,
    data?: any,
    header?: string
    fields: object[]
}

export interface McqInterface{
    testId : number,
    questionDescription : string,
    option1 : string,
    option2 : string,
    option3? : string,
    option4? : string,
    category : string,
    difficulty : string,
    positiveMark : number,
    negativeMark : number,
    correctAnswer : string,
    id : 0
}

export interface testcasesInterface{
    programmeId : number,
    input : string,
    output : string,
    id : number
}

export interface ProgrammeInterface{
    testId : number,
    questionDescription : string,
    positiveMark : number,
    category : string,
    difficulty : string,
    id : number
}

export interface response {
    // login
    isAdmin?: boolean,
    token?: string,
    emailId?: string,

    //signup
    message?: string,

    // batch
    batch? : BatchInterface[]
    id ? : number,

    //colleges 
    clg? : CollegeInterface [],

    //student
    success? : StudentInterface[],
    failure? : StudentInterface[],

    //tests
    tests? : TestInterface[],

    //programmes
    programmes? : ProgrammeInterface[]
}

export interface backendResponse {
    data: response,
    err: string,
    abort(): any
}