export const ApiEndPoints = {
    logout : "http://localhost:5151/api/v1/auth/logout",
    login : "http://localhost:5151/api/v1/auth/login",
    signup : "http://localhost:5151/api/v1/auth/admin/register",
    students : "http://localhost:5151/api/v1/admin/students",
    studentRegister : "http://localhost:5151/api/v1/admin/student/register",
    clgRegister : "http://localhost:5151/api/v1/admin/clg/register",
    batchRegister : "http://localhost:5151/api/v1/admin/batch/create",
    testRegister : "http://localhost:5151/api/v1/admin/test/register",
    mcqRegister : "http://localhost:5151/api/v1/admin/mcq/register",
    programmeRegister : "http://localhost:5151/api/v1/admin/programme/register",
    testcasesRegister : "http://localhost:5151/api/v1/admin/testcases/register",
    languageRegister : "http://localhost:5151/api/v1/admin/register/languages",
    mcqResponse : "http://localhost:5151/api/v1/student/mcq/register",
    programmeResponse : "http://localhost:5151/api/v1/student/programme/register",
    submission : "http://localhost:5151/api/v1/student/programme/submission",
    getBatch : "http://localhost:5151/api/v1/admin/batch/get",
    getCollege : "http://localhost:5151/api/v1/admin/college/get",
    getTests : "http://localhost:5151/api/v1/admin/tests/get",
    getMcqs : "http://localhost:5151/api/v1/admin/mcq/get/" , // add id,
    getProgrammes : "http://localhost:5151/api/v1/admin/programme/get/" , // add id
    getStudentTest : "http://localhost:5151/api/v1/student/tests/get",
    getStudentMcqs : "http://localhost:5151/api/v1/student/mcq/get/" , // add id,
    getStudentProgrammes : "http://localhost:5151/api/v1/student/programme/get/" , // add id
    getTestcases : "http://localhost:5151/api/v1/student/testcases/get/" , // add id
    getLanguage : "http://localhost:5151/api/v1/student/language/get"
}