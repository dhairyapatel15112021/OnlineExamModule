import  { useState } from 'react'
import { Input } from '../../../components/Input'
import { Buttons } from '../../../components/Buttons'
import { OnFileChange } from '../../../helperFunctions/OnFileChange'
import { ApiEndPoints } from '../../../data/ApiEndPoints'
import { McqInterface } from '../../../data/Interface'
import {  useParams } from 'react-router-dom'
import { validateData } from '../../../helperFunctions/validateData'
import { backendCall } from '../../../helperFunctions/backendCall'

export const Mcq = () => {
    const {id} = useParams();
    const [mcq,setMcq] = useState<McqInterface>({testId : parseInt(id || "") , questionDescription : "" , option1 : "" , option2 : "" , category : "" , difficulty : "" , positiveMark : 0 , negativeMark : 0, correctAnswer : "" , id : 0});

      const onChangeFunction = (event: any) => {
        setMcq({ ...mcq, [event.target.name]: event.target.value });
      }
    
      const onFileChangeFunction = (event : any)=>{
        const file : File = event.target.files?.[0];
        OnFileChange({file : file , url : ApiEndPoints.mcqRegister ,fields :[ {"success" : []} , {"failure" : []}] })
      }

      const onSubmitFunction = async () =>{
        try{
          const validateResponse = validateData({data : mcq , fields : ["testId" , "questionDescription" , "option1","option2","category","difficulty","positiveMark" , "negativeMark" , "correctAnswer"]});
          if(!validateResponse.isOk){
            console.log(validateResponse.error);
            return;
          }
          const {id,...data} = mcq;
          const response = await backendCall({url : ApiEndPoints.mcqRegister , data : [data] , method : 'POST' , header : localStorage.getItem("token") || "" , fields :[{success : []} , {failure : [] }] });
          if (!response || !response.data || response.data === null || response.err != "") {
            throw new Error(response.err);
          }
          console.log(response.data);
        }
        catch(err){
          console.log(err);
        }
      }
    
  return (
   <div className='flex flex-col'>
             <div className='h-[45vh] flex flex-col justify-evenly'>
               <div className='text-lg md:text-base md:font-medium font-normal flex justify-between'>
                 <div>Submit This Form To Create Mcq or <span className="underline underline-offset-4">Upload File</span> </div>
                 <label htmlFor="mcqFile">
                   <div className="px-4 py-2 bg-blue rounded-md border border-blue text-white hover:text-blue hover:bg-white tracking-normal md:tracking-wider text-nowrap">Upload File</div>
                   <input onChange={onFileChangeFunction} type="file" name="file" id="mcqFile" accept=".xlsx" className="hidden" />
                 </label>
               </div>
               <div className=' w-[100vw] flex flex-wrap items-start gap-4'>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='text' name='questionDescription' placeholder='Question Description' onChnageFunction={onChangeFunction} />
                 </div>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='text' name='option1' placeholder='Option 1' onChnageFunction={onChangeFunction} />
                 </div>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='text' name='option2' placeholder='Option 2' onChnageFunction={onChangeFunction} />
                 </div>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='text' name='option3' placeholder='Option 3' onChnageFunction={onChangeFunction} />
                 </div>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='text' name='option4' placeholder='Option 4' onChnageFunction={onChangeFunction} />
                 </div>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='text' name='correctAnswer' placeholder='Correct Answer' onChnageFunction={onChangeFunction} />
                 </div>
                 <select onChange={onChangeFunction} name="category" defaultValue="Category" className="w-[40vw] md:w-[30vw] focus:outline-none border h-fit py-[0.60rem] px-2 rounded-md text-base md:text-lg">
                    <option value="Category" disabled>Category</option>
                    <option value="Apptitude">Apptitude</option>
                    <option value="Technical">Technical</option>
                 </select>
                 <select onChange={onChangeFunction} name="difficulty" defaultValue="Difficulty" className="w-[40vw] md:w-[30vw] focus:outline-none border h-fit py-[0.60rem] px-2 rounded-md text-base md:text-lg">
                    <option value="Difficulty" disabled>Difficulty</option>
                    <option value="Easy">Easy</option>
                    <option value="Medium">Medium</option>
                    <option value="Hard">Hard</option>
                 </select>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='number' name='positiveMark' placeholder='Positive Mark' onChnageFunction={onChangeFunction} />
                 </div>
                 <div className='w-[40vw] md:w-[30vw]'>
                   <Input type='number' name='negativeMark' placeholder='Negative Mark' onChnageFunction={onChangeFunction} />
                 </div>
                 <Buttons onclick={onSubmitFunction} text="Create Mcq" />
               </div>
             </div>
             {/* {
               students.length === 0 ?
                 <div className='w-[100vw] flex justify-center items-center text-2xl font-medium'>
                   No Students
                 </div> :
                 <div className='p-7'>
                   <div className='text-lg md:text-base md:font-medium font-normal flex gap-4'>
                     <div>Already Existed Students Are Below</div>
                     <div className='font-extralight md:font-light'>( Hover To See Students id )</div>
                   </div>
                   <div className='flex flex-wrap gap-4 mt-7'>
                     {
                       students.map((item, index) => {
                         return (
                           <div></div>
                           
                         )
                       })
                     }
       
                   </div>
                 </div>
             } */}
           </div>
  )
}
