import React, { useState } from 'react'
import { Input } from '../../components/Input';
import { Buttons } from '../../components/Buttons';
import { useRecoilState, useRecoilValue } from 'recoil';
import { batchAtom } from '../../store/atoms/bacthesAtom';
import { BatchInterface, TestInterface } from '../../data/Interface';
import { validateData } from '../../helperFunctions/validateData';
import { backendCall } from '../../helperFunctions/backendCall';
import { ApiEndPoints } from '../../data/ApiEndPoints';
import { TestAtom } from '../../store/atoms/TestAtom';
import { Link } from 'react-router-dom';

export const Tests = () => {
  const batches = useRecoilValue<BatchInterface[]>(batchAtom);
  const [tests,setTests] = useRecoilState<TestInterface[]>(TestAtom);
  const [test, setTest] = useState<TestInterface>({title : "" , totalApptitudeQuestion : 0 , totalProgrammingQuestion : 0 , totalTechnicalQuestion : 0,time : "" , batchId : 0 , id : 0 , passingPercentage : 0});
  const onChangeFunction = (event: any) => {
    setTest({ ...test, [event.target.name]: event.target.value });
  }
  const onSubmitFunction = async () => {
    try{
      const validateResponse = validateData({data : test , fields : ["title","totalApptitudeQuestion","totalProgrammingQuestion","totalTechnicalQuestion","time","batchId","passingPercentage"]});
      if(!validateResponse.isOk){
        console.log(validateResponse.error);
        return;
      }
      const {id,...data} = test;
      const response = await backendCall({url : ApiEndPoints.testRegister,data : data , method : 'POST', fields : [{id : 0}],header : localStorage.getItem("token") || "", });
      if(!response || response == null || !response.data || response.data == null || response.err!=""){
        throw new Error(response.err);
      }
      setTest({title : "" , totalApptitudeQuestion : 0 , totalProgrammingQuestion : 0 , totalTechnicalQuestion : 0,time : "" , batchId : 0 , id : 0, passingPercentage : 0}  );
      setTests((prevTests)=>[...prevTests , {...test,id : response.data.id || 0}]);
    }
    catch(err){
      console.log(err);
    }
  } 

  return (
    <div className='flex flex-col'>
      <div className='h-[35vh] flex flex-col justify-evenly'>
        <div className='px-7 text-lg md:text-base md:font-medium font-normal'>
          Submit This Form To Create Test
        </div>
        <div className='px-7 w-[100vw] flex flex-wrap items-start gap-4'>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='text' name='title' placeholder='Title' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='number' name='totalApptitudeQuestion' placeholder='Total Apptitude Question' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='number' name='totalProgrammingQuestion' placeholder='Total Programming Question' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='number' name='totalTechnicalQuestion' placeholder='Total Technical Question' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='text' name='time' placeholder='Test Time ( Format : 01:00 )' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='number' name='passingPercentage' placeholder='Passing Percentage (Default : 0)' onChnageFunction={onChangeFunction} />
          </div>
          <select onChange={onChangeFunction} defaultValue="Batch" name="batchId" className="w-[40vw] md:w-[30vw] focus:outline-none border h-fit py-[0.60rem] px-2 rounded-md text-base md:text-lg">
            <option disabled value="Batch">Batch</option>
            {
              batches.map((item, index) => {
                return (
                  <option value={item.id} key={item.id}>{item.year} </option>
                )
              })
            }
          </select>
          <Buttons onclick={onSubmitFunction} text="Create Test" />
        </div>
      </div>
      {
        tests.length === 0 ?
          <div className='w-[100vw] flex justify-center items-center text-2xl font-medium'>
            No Tests
          </div> :
          <div className='p-7'>
            <div className='text-lg md:text-base md:font-medium font-normal flex gap-4'>
              <div>Already Existed Tests Are Below</div>
              <div className='font-extralight md:font-light'>( Hover To See Tests id )</div>
            </div>
            <div className='grid grid-flow-row grid-cols-1 mt-7 gap-y-1'>
              <div className='grid grid-rows-1 grid-cols-9 place-items-center p-2 bg-blue text-white rounded-md font-extralight md:font-bold'>
                  <div className='col-span-2'>Title</div>
                  <div>TAQ</div>
                  <div>TPQ</div>
                  <div>TTQ</div>
                  <div>Time</div>
                  <div>Percentage</div>
                  <div className='col-span-2'>Batch</div>
              </div>
              {
                tests.map((item, index) => {
                  return (
                    <Link to={`/admin/test/${item.id}/mcq`} key={item.id} className='p-2 grid grid-rows-1 grid-cols-9 place-items-center bg-blue text-white rounded-md font-extralight'>
                      <div className='col-span-2'>{item.title}</div>
                      <div>{item.totalApptitudeQuestion}</div>
                      <div>{item.totalProgrammingQuestion}</div>
                      <div>{item.totalTechnicalQuestion}</div>
                      <div>{item.time}</div>
                      <div>{item.passingPercentage}</div>
                      <div className='col-span-2'>{batches.filter((batch)=> batch.id == item.batchId)[0]?.year}</div>
                    </Link>
                  )
                })
              }

            </div>
          </div>
      }
    </div>
  )
}
