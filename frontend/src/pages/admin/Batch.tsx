import { useState } from 'react'
import { Input } from '../../components/Input';
import { Buttons } from '../../components/Buttons';
import { validateData } from '../../helperFunctions/validateData';
import { backendCall } from '../../helperFunctions/backendCall';
import { ApiEndPoints } from '../../data/ApiEndPoints';
import { useRecoilState } from 'recoil';

import { batchAtom } from '../../store/atoms/bacthesAtom';
import { BatchInterface } from '../../data/Interface';

export const Batch = () => {
  const [batches,setBatch] = useRecoilState<BatchInterface[]>(batchAtom);
  const [year,setYear] = useState<BatchInterface>({year : "" , id : 0});

  const onChangeFunction = (event : any) => {
    setYear({...year ,[event.target.name] : event.target.value });
  }

  const onSubmitFunction = async () => {
    try{
      const validateResponse = validateData({data : year , fields : ["year"]});
      if(!validateResponse.isOk){
        console.log(validateResponse.error);
        return;
      }

      const response = await backendCall({url : ApiEndPoints.batchRegister , method : "POST" , data : year , fields : [{id : 0}] , header : localStorage.getItem("token") || ""});
      if(!response || !response.data || response.data === null || response.err != "" ){
        throw new Error(response.err);
      }
      setYear({year : "" , id : 0});
      setBatch((prevBatches) => [...prevBatches, { year: year.year , id : response.data.id || 0 }]);
    }
    catch(err){
      console.log(err);
    }
  }

  return (
    <div className='flex flex-col'>
      <div className='h-[15vh] flex flex-col justify-evenly'>
        <div className='px-7 text-lg md:text-base md:font-medium font-normal'>Submit This Form To Create Batch</div>
        <div className='px-7 flex items-start w-[100vw] md:w-[40vw] gap-4'>
          <Input type='text' name='year' placeholder='Year' onChnageFunction={onChangeFunction} />
          <Buttons onclick={onSubmitFunction} text="Create Batch"/>
        </div>
      </div>
      {
        batches.length === 0 ?
          <div className='h-[75vh] w-[100vw] flex justify-center items-center text-2xl font-medium'>
            No Batches
          </div> :
          <div className='p-7'>
            <div className='text-lg md:text-base md:font-medium font-normal'>Already Existed Batch Are Below</div>
            <div className='flex flex-wrap gap-4 mt-7'>
            {
              batches.map((item,_)=>{
                return(
                  <div key={item.id} className='bg-blue text-white px-5 py-2 w-fit h-fit self-end border border-blue text-xl tracking-widest hover:bg-white hover:text-blue rounded-md text-nowrap'>{item.year}</div>
                )
              })
            }
 
          </div>
          </div>
      }
     
    </div>
  )
}
