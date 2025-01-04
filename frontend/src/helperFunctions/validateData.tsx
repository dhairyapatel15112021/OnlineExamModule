interface response{
    isOk : boolean,
    error : string
}

interface request{
    data : any,
    fields : string[]
}

export const validateData = (inputData : request) : response => {
    const dataResponse = { isOk : false, error : ""};
    let password = "" ;
    let confirmPassword = "";

    try{
        for (let field of inputData.fields){

            if(field === "password") password = inputData.data[field];
            if(field === "confirmPassword") confirmPassword = inputData.data[field];

            if(typeof(inputData.data[field]) === "string" && inputData.data[field].trim() === ""){
                throw new Error("Something Went Wrong in the field " + field);
            }
            else if(typeof(inputData.data[field]) === "number" && inputData.data[field] === 0){
                throw new Error("Something Went Wrong in the field " + field);
            }
        }
        if(inputData.fields.includes("confirmPassword") && password.trim() != confirmPassword.trim()){
            throw new Error("Password and Confirm password should be same");
        }
        dataResponse.isOk = true;
        dataResponse.error = "All Ok";
    }   
    catch(error : any) {
        dataResponse.isOk = false;
        dataResponse.error = error;
    }
    return dataResponse;
} 