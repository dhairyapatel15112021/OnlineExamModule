import  { useState } from 'react'
import { validateData } from '../helperFunctions/validateData';
import { backendCall } from '../helperFunctions/backendCall';
import { ApiEndPoints } from '../data/ApiEndPoints';
import { Input } from '../components/Input';
import { Buttons } from '../components/Buttons';
import { useNavigate } from 'react-router-dom';

export const Signup = () => {
    const [formData , setFormData] = useState({ name : "" , emailId : "" , password : "" , confirmPassword : "" });
    const navigate = useNavigate();
    const onChangeFunction = (event : any) : void => {
        setFormData({...formData , [event.target.name] : event.target.value});
    }

    const onSubmitFunction = async () => {
        try{
          const validateResponse = validateData({data : formData , fields : ["name" , "emailId" , "password" , "confirmPassword"]});
          if( !validateResponse.isOk ){
            console.log(validateResponse.error);
            return;
          }

          const {confirmPassword , ...data} = {...formData};
          const response = await backendCall({url : ApiEndPoints.signup , method : "POST" , data : data , fields : [{ message : ""}]});
          if(!response || !response.data || response.data === null || response.err != "" ){
            throw new Error(response.err);
          }
          navigate("/login");
          console.log(response.data);
        }
        catch(err){
            console.log(err);
        }
    }
    return (
        <div className='h-[90vh] w-[100vw] flex justify-center items-center'>
            <div className='h-[50vh] lg:h-[60vh] w-[80vw] lg:w-[30vw] flex flex-col border rounded-md shadow px-4 py-5 gap-y-[2.2vh] lg:gap-y-[3vh]'>
                <div className='tracking-widest text-center text-3xl w-full'>SIGNUP</div>
                <Input type='text' name='name' onChnageFunction={onChangeFunction} placeholder='Name' />
                <Input type='text' name='emailId' onChnageFunction={onChangeFunction} placeholder='Email' />
                <Input type='password' name='password' onChnageFunction={onChangeFunction} placeholder='Password' />
                <Input type='password' name='confirmPassword' onChnageFunction={onChangeFunction} placeholder='Confirm Password' />
                <Buttons text='SUBMIT' onclick={onSubmitFunction}/>
            </div>
        </div>
    ) 
}
