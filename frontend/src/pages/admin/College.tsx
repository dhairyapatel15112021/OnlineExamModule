import { useState } from 'react';
import { Input } from '../../components/Input';
import { Buttons } from '../../components/Buttons';
import { validateData } from '../../helperFunctions/validateData';
import { backendCall } from '../../helperFunctions/backendCall';
import { ApiEndPoints } from '../../data/ApiEndPoints';
import { useRecoilState } from 'recoil';
import { ColllegeAtom } from '../../store/atoms/CollegesAtom';
import { CollegeInterface } from '../../data/Interface';

export const College = () => {
  const [colleges, setColleges] = useRecoilState<CollegeInterface[]>(ColllegeAtom);
  const [college, setCollege] = useState<CollegeInterface>({ emailId: "", name: "", address: "", contactNumber: "", id: 0 });

  const onChangeFunction = (event : any) => {
    setCollege({ ...college, [event.target.name]: event.target.value });
  }

  const onSubmitFunction = async () => {
    try {
      const validateResponse = validateData({ data: college, fields: ["emailId", "name", "address", "contactNumber"] });
      if (!validateResponse.isOk) {
        console.log(validateResponse.error);
        return;
      }
      else if (college.contactNumber.length != 10) {
        console.log("Contanct Number Should be Lenght of 10");
        return;
      }

      const response = await backendCall({ url: ApiEndPoints.clgRegister, method: 'POST', data: college, fields: [{ id: 0 }], header: localStorage.getItem("token") || "" });
      if (!response || !response.data || response.data === null || response.err!= "") {
        throw new Error(response.err);
      }
      setCollege({ emailId: "", name: "", address: "", contactNumber: "", id: 0 });;
      setColleges((prevColleges) => [...prevColleges, { ...college, id: response.data.id || 0 }])
    }
    catch (err) {
      console.log(err);
    }
  }

  return (
    <div className='flex flex-col'>
      <div className='h-[25vh] flex flex-col justify-evenly'>
        <div className='px-7 text-lg md:text-base md:font-medium font-normal'>Submit This Form To Create college</div>
        <div className='px-7 w-[100vw] flex flex-wrap items-start gap-4'>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='text' name='emailId' placeholder='Email' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='text' name='name' placeholder='Name' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='text' name='address' placeholder='Address' onChnageFunction={onChangeFunction} />
          </div>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='text' name='contactNumber' placeholder='Contact Number' onChnageFunction={onChangeFunction} />
          </div>
          <Buttons onclick={onSubmitFunction} text="Create College" />
        </div>
      </div>
      {
        colleges.length === 0 ?
          <div className='h-[65vh] w-[100vw] flex justify-center items-center text-2xl font-medium'>
            No Colleges
          </div> :
          <div className='p-7'>
            <div className='text-lg md:text-base md:font-medium font-normal flex gap-4'>
              <div>Already Existed Colleges Are Below</div>
              <div className='font-extralight md:font-light'>( Hover To See College id )</div>
            </div>
            <div className='flex flex-wrap gap-4 mt-7'>
              {
                colleges.map((item, _) => {
                  return (
                    <div key={item.id} className='px-5 py-2 bg-blue text-white rounded-md flex flex-col gap-2'>
                      <div className='flex items-center gap-2'>
                        <div>Name</div>
                        <div>:</div>
                        <input type="text" disabled defaultValue={item.name} className='bg-blue border border-white py-1 px-2 rounded-md' />
                      </div>
                      <div className='flex items-center gap-2'>
                        <div>Email</div>
                        <div>:</div>
                        <input type="text" disabled defaultValue={item.emailId} className='bg-blue border border-white py-1 px-2 rounded-md' />
                      </div>
                      <div className='flex items-center gap-2'>
                        <div>Address</div>
                        <div>:</div>
                        <input type="text" disabled defaultValue={item.address} className='bg-blue border border-white py-1 px-2 rounded-md' />
                      </div>
                      <div className='flex items-center gap-2'>
                        <div>Mo. No.</div>
                        <div>:</div>
                        <input type="text" disabled defaultValue={item.contactNumber} className='bg-blue border border-white py-1 px-2 rounded-md' />
                      </div>
                    </div>
                  )
                })
              }

            </div>
          </div>
      }
    </div>
  )
}
