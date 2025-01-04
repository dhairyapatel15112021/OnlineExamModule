import { useEffect, useState } from "react"
//import { useDebounce } from "../hooks/useDebounce";
import { backendCall } from "../helperFunctions/backendCall";
import { validateData } from "../helperFunctions/validateData";
import { useRecoilState } from "recoil";
import { login } from "../store/atoms/login";
import { ApiEndPoints } from "../data/ApiEndPoints";
import { useNavigate } from "react-router-dom";
import { Input } from "../components/Input";
import { Buttons } from "../components/Buttons";

export const Login = () => {
  const [loginData,setLoginData] = useState({emailId : "", password: ""});
  const [user , setLogin] = useRecoilState(login);
  const navigate = useNavigate();
  // const debouncedValue = useDebounce(loginData);

  useEffect(() => {
    if (user.emailId.trim() != "") {
      navigate(user.isAdmin ? "/admin/dashboard" : "");
      return;
    }
   navigate("/login");
  }, [user])
  
  const onChangeFunction = (event : any) => {
    setLoginData({...loginData ,[event.target.name] : event.target.value });
  }

  const onSubmitFunction = async () => {
    try{
      const validateResponse = validateData({data : loginData , fields : ["password" , "emailId"]});
      if(!validateResponse.isOk){
        console.log(validateResponse.error);
        return;
      }
      const response = await backendCall({url : ApiEndPoints.login, method: "POST", data: loginData , fields : [{isAdmin : false} , {token : ""} , {emailId : ""}]});
      if(!response || (response.data === null) || (response.err != "") || (!response.data.token) || (response.data.token === "")){
        setLogin({emailId : "" , isAdmin : false});
        throw new Error(response.err);
      }
      console.log(response);
      setLogin({emailId : loginData.emailId.trim() , isAdmin : response.data.isAdmin || false} );
      localStorage.setItem("token" ,  "Bearer "  + response.data.token  );
    }
    catch(err){
      console.log(err);
    }
  }

  return (
    <div className='h-[90vh] flex justify-center items-center'>
        <div className='h-[40vh] w-[80vw]  lg:w-[23vw] border rounded-md p-4 lg:p-2 flex justify-evenly flex-col shadow'>
          <div className='tracking-widest self-center text-3xl m-2 mb-4'>LOGIN</div>
          <Input type="text" name="emailId" placeholder="Email" onChnageFunction={onChangeFunction} />
          <Input type="password" name="password" placeholder="Password" onChnageFunction={onChangeFunction} />
          <Buttons text="SUBMIT" onclick={onSubmitFunction}/>
        </div>
    </div>
  )
}
