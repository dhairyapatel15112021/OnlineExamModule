import { useState } from "react";
import { Buttons } from "../../components/Buttons";
import { Input } from "../../components/Input";
import { useRecoilValue } from "recoil";
import { batchAtom } from "../../store/atoms/bacthesAtom";
import { ColllegeAtom } from "../../store/atoms/CollegesAtom";
import { BatchInterface, CollegeInterface, StudentInterface } from "../../data/Interface";
import { validateData } from "../../helperFunctions/validateData";
import { backendCall } from "../../helperFunctions/backendCall";
import { ApiEndPoints } from "../../data/ApiEndPoints";
import { OnFileChange } from "../../helperFunctions/OnFileChange";

export const Students = () => {
  const batches = useRecoilValue<BatchInterface[]>(batchAtom);
  const colleges = useRecoilValue<CollegeInterface[]>(ColllegeAtom);
  const [students,setStudents] = useState<StudentInterface[]>([]);
  const [student,setStudent] = useState<StudentInterface>({emailId : "" , enrollmentNumber : "" , password : "" , clgId : 0 , batchId : 0 , name : "" , id : 0, mobileNumber : ""});

  const onChangeFunction = (event: any) => {
    setStudent({ ...student, [event.target.name]: event.target.value });
  }

  const onFileChangeFunction = (event : any)=>{
    const file : File = event.target.files?.[0];
    OnFileChange({file : file , url : ApiEndPoints.studentRegister ,fields :[ {"success" : []} , {"failure" : [] }] })
  }

  const onSubmitFunction = async () => {
      try{
        const validateResponse = validateData({data : student , fields : ["emailId" ,"password","name","enrollmentNumber","mobileNumber","batchId","clgId" ]});
        if (!validateResponse.isOk) {
          console.log(validateResponse.error);
          return;
        }
        else if (student.mobileNumber.length != 10) {
          console.log("Contanct Number Should be Lenght of 10");
          return;
        }
        const {id,...data} = student;
        const response = await backendCall({url : ApiEndPoints.studentRegister , data : [data] , method : 'POST' , fields: [{"success" : []} , {"failure" : [] }], header: localStorage.getItem("token") || ""});
        if (!response || !response.data || response.data === null || response.err != "") {
          throw new Error(response.err);
        }
        setStudent({emailId : "" , enrollmentNumber : "" , password : "" , clgId : 0 , batchId : 0 , name : "" , id : 0, mobileNumber : ""});
        console.log(response.data.success);
        console.log(response.data.failure);
        setStudents((prevStudents) => [...prevStudents , {...student , id : response.data.id || 0}]);
      }
      catch(err){
        console.log(err);
      }
  }

 
  return (
        <div className='flex flex-col'>
          <div className='h-[35vh] flex flex-col justify-evenly'>
            <div className='px-7 text-lg md:text-base md:font-medium font-normal flex justify-between'>
              <div>Submit This Form To Create Student or <span className="underline underline-offset-4">Upload File</span> </div>
              <label htmlFor="studentFile">
                <div className="px-4 py-2 bg-blue rounded-md border border-blue text-white hover:text-blue hover:bg-white tracking-normal md:tracking-wider text-nowrap">Upload File</div>
                <input onChange={onFileChangeFunction} type="file" name="file" id="studentFile" accept=".xlsx" className="hidden" />
              </label>
            </div>
            <div className='px-7 w-[100vw] flex flex-wrap items-start gap-4'>
              <div className='w-[40vw] md:w-[30vw]'>
                <Input type='text' name='emailId' placeholder='Email' onChnageFunction={onChangeFunction} />
              </div>
              <div className='w-[40vw] md:w-[30vw]'>
                <Input type='password' name='password' placeholder='Password' onChnageFunction={onChangeFunction} />
              </div>
              <div className='w-[40vw] md:w-[30vw]'>
                <Input type='text' name='name' placeholder='Name' onChnageFunction={onChangeFunction} />
              </div>
              <div className='w-[40vw] md:w-[30vw]'>
                <Input type='text' name='enrollmentNumber' placeholder='Enrollment Number' onChnageFunction={onChangeFunction} />
              </div>
              <div className='w-[40vw] md:w-[30vw]'>
                <Input type='text' name='mobileNumber' placeholder='Contact Number' onChnageFunction={onChangeFunction} />
              </div>
              <select onChange={onChangeFunction} defaultValue="Batch" name="batchId" className="w-[40vw] md:w-[30vw] focus:outline-none border h-fit py-[0.60rem] px-2 rounded-md text-base md:text-lg">
                <option disabled value="Batch">Batch</option>
                {
                  batches.map((item,_)=>{
                    return (
                      <option value={item.id} key={item.id}>{item.year} </option>
                    )
                  })
                }
              </select>
              <select onChange={onChangeFunction} defaultValue="College" name="clgId" className="w-[40vw] md:w-[30vw] focus:outline-none border h-fit py-[0.60rem] px-2 rounded-md text-base md:text-lg">
                <option disabled value="College">College</option>
                {
                  colleges.map((item,_)=>{
                    return (
                      <option value={item.id} key={item.id}>{item.name} </option>
                    )
                  })
                }
              </select>
              <Buttons onclick={onSubmitFunction} text="Create Student" />
            </div>
          </div>
          {
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
                    students.map(() => {
                      return (
                        <div></div>
                        // <div key={index} className='px-5 py-2 bg-blue text-white rounded-md flex flex-col gap-2'>
                        //   <div className='flex items-center gap-2'>
                        //     <div>Name</div>
                        //     <div>:</div>
                        //     <input type="text" disabled defaultValue={item.name} className='bg-blue border border-white py-1 px-2 rounded-md' />
                        //   </div>
                        //   <div className='flex items-center gap-2'>
                        //     <div>Email</div>
                        //     <div>:</div>
                        //     <input type="text" disabled defaultValue={item.emailId} className='bg-blue border border-white py-1 px-2 rounded-md' />
                        //   </div>
                        //   <div className='flex items-center gap-2'>
                        //     <div>Address</div>
                        //     <div>:</div>
                        //     <input type="text" disabled defaultValue={item.address} className='bg-blue border border-white py-1 px-2 rounded-md' />
                        //   </div>
                        //   <div className='flex items-center gap-2'>
                        //     <div>Mo. No.</div>
                        //     <div>:</div>
                        //     <input type="text" disabled defaultValue={item.contactNumber} className='bg-blue border border-white py-1 px-2 rounded-md' />
                        //   </div>
                       // </div>
                      )
                    })
                  }
    
                </div>
              </div>
          }
        </div>
  )
}
