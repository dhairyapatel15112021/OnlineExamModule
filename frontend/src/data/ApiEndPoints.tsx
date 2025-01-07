const BASE_URL : string = "https://onlineexammodule-production.up.railway.app/";

// http://localhost:5151/

export const ApiEndPoints = {
    logout : `${BASE_URL}api/v1/auth/logout`,
    login : `${BASE_URL}api/v1/auth/login`,
    signup : `${BASE_URL}api/v1/auth/admin/register`,
    students : `${BASE_URL}api/v1/admin/students`,
    studentRegister : `${BASE_URL}api/v1/admin/student/register`,
    clgRegister : `${BASE_URL}api/v1/admin/clg/register`,
    batchRegister : `${BASE_URL}api/v1/admin/batch/create`,
    testRegister : `${BASE_URL}api/v1/admin/test/register`,
    mcqRegister : `${BASE_URL}api/v1/admin/mcq/register`,
    programmeRegister : `${BASE_URL}api/v1/admin/programme/register`,
    testcasesRegister : `${BASE_URL}api/v1/admin/testcases/register`,
    languageRegister : `${BASE_URL}api/v1/admin/register/languages`,
    mcqResponse : `${BASE_URL}api/v1/student/mcq/register`,
    programmeResponse : `${BASE_URL}api/v1/student/programme/register`,
    submission : `${BASE_URL}api/v1/student/programme/submission`,
    getBatch : `${BASE_URL}api/v1/admin/batch/get`,
    getCollege : `${BASE_URL}api/v1/admin/college/get`,
    getTests : `${BASE_URL}api/v1/admin/tests/get`,
    getMcqs : `${BASE_URL}api/v1/admin/mcq/get/` , // add id,
    getProgrammes : `${BASE_URL}api/v1/admin/programme/get/` , // add id
    getStudentTest : `${BASE_URL}api/v1/student/tests/get"`,
    getStudentMcqs : `${BASE_URL}api/v1/student/mcq/get/` , // add id,
    getStudentProgrammes : `${BASE_URL}api/v1/student/programme/get/` , // add id
    getTestcases : `${BASE_URL}api/v1/student/testcases/get/` , // add id
    getLanguage : `${BASE_URL}api/v1/student/language/get`
}