import { useState } from "react"
//import { useDebounce } from "../hooks/useDebounce";
import { backendCall } from "../helperFunctions/backendCall";

export const Login = () => {
  const [loginData,setLoginData] = useState({emailId : "", password: ""});
  // const debouncedValue = useDebounce(loginData);

  const onChangeFunction = (event : any) => {
    setLoginData({...loginData ,[event.target.name] : event.target.value });
  }

  const onSubmitFunction = async () => {
    try{
      const response = await backendCall({url : "http://localhost:5151/api/v1/login" , method : "POST" , data : loginData});
      if(response.data === ""){
        throw new Error(response.err);
      }
      console.log(response.data);
    }
    catch(err){
      console.log(err);
    }
  }

  return (
    <div className='h-[90vh] flex justify-center items-center'>
        <div className='lg:h-[40vh] lg:w-[23vw] border rounded-md p-2 flex justify-center flex-col shadow'>
          <div className='tracking-widest self-center text-3xl m-2 mb-4'>LOGIN</div>
          <div className='flex justify-center items-start flex-col border m-2 mt-4 p-2 rounded-sm'>
            <input type="text" name="emailId" placeholder='Email' className='focus:outline-none text-lg w-[100%]' onChange={onChangeFunction} />
          </div>
          <div className='flex justify-center items-start flex-col border m-2 p-2'>
            <input type="password" name="password" placeholder='Password' className='focus:outline-none text-lg w-[100%]' onChange={onChangeFunction}/>
          </div>
          <button onClick={onSubmitFunction} type="button" className='self-center tracking-wider p-2 px-4 m-2 mt-4 border border-blue bg-blue text-white rounded-sm text-lg hover:bg-white hover:text-blue'>SUBMIT</button>
        </div>
    </div>
  )
}
