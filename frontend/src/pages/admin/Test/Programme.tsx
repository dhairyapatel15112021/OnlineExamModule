import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { ProgrammeInterface, TestInterface } from '../../../data/Interface';
import { OnFileChange } from '../../../helperFunctions/OnFileChange';
import { ApiEndPoints } from '../../../data/ApiEndPoints';
import { backendCall } from '../../../helperFunctions/backendCall';
import { validateData } from '../../../helperFunctions/validateData';
import { Input } from '../../../components/Input';
import { Buttons } from '../../../components/Buttons';
import { useRecoilState, useRecoilValue } from 'recoil';
import { ProgrammeAtom } from '../../../store/atoms/ProgrammeAtom';
import { TestAtom } from '../../../store/atoms/TestAtom';

export const Programme = () => {
  const tests = useRecoilValue<TestInterface[]>(TestAtom);
  const { id } = useParams();
  const [programme, setProgramme] = useState<ProgrammeInterface>({ testId: parseInt(id || ""), questionDescription: "", positiveMark: 0, category: "Programming", difficulty: "", id: 0 });
  const [programmes, setProgrammes] = useRecoilState<ProgrammeInterface[]>(ProgrammeAtom);

  const onChangeFunction = (event: any) => {
    setProgramme({ ...programme, [event.target.name]: event.target.value });
  }

  const onFileChangeFunction = (event: any) => {
    const file: File = event.target.files?.[0];
    OnFileChange({ file: file, url: ApiEndPoints.programmeRegister, fields: [{ "success": [] }, { "failure": [] }] });
  }

  const onSubmitFunction = async () => {
    try {
      const validateResponse = validateData({ data: programme, fields: ["testId", "questionDescription", "category", "difficulty", "positiveMark"] });
      if (!validateResponse.isOk) {
        console.log(validateResponse.error);
        return;
      }
      const { id, ...data } = programme;
      const response = await backendCall({ url: ApiEndPoints.programmeRegister, data: [data], method: 'POST', header: localStorage.getItem("token") || "", fields: [{ success: [] }, { failure: [] }] });
      if (!response || !response.data || response.data === null || response.err != "") {
        throw new Error(response.err);
      }
      setProgrammes((prevProgrammes) => [...prevProgrammes, { ...programme, id: response.data.success?.[0].id || 0 }]);
      setProgramme({ testId: id, questionDescription: "", positiveMark: 0, category: "Programming", difficulty: "", id: 0 });
      console.log(response.data);
    }
    catch (err) {
      console.log(err);
    }
  }

  const getProgrammes = async () => {
    try {
      const response = await backendCall({ url: ApiEndPoints.getProgrammes + id, method: 'GET', header: localStorage.getItem("token") || "", fields: [{ programmes: [] }] });
      if (!response || !response.data || !response.data.programmes || response.err != "") {
        throw new Error(response.err);
      }
      setProgrammes(response.data.programmes);
    }
    catch (err) {
      console.log(err);
    }
  }

  useEffect(() => {
    getProgrammes();
  }, []);

  return (
    <div className='flex flex-col'>
      <div className='h-[30vh] md:h-[20vh] flex flex-col justify-evenly'>
        <div className='text-lg md:text-base md:font-medium font-normal flex justify-between'>
          <div>Submit This Form To Create Programme or <span className="underline underline-offset-4">Upload File</span> </div>
          <label htmlFor="programmeFile">
            <div className="px-4 py-2 bg-blue rounded-md border border-blue text-white hover:text-blue hover:bg-white tracking-normal md:tracking-wider text-nowrap">Upload File</div>
            <input onChange={onFileChangeFunction} type="file" name="file" id="programmeFile" accept=".xlsx" className="hidden" />
          </label>
        </div>
        <div className=' w-[100vw] flex flex-wrap items-start gap-3 md:gap-4'>
          <div className='w-[40vw] md:w-[30vw]'>
            <Input type='text' name='questionDescription' placeholder='Question Description' onChnageFunction={onChangeFunction} />
          </div>
          <select onChange={onChangeFunction} name="difficulty" defaultValue="Difficulty" className="w-[40vw] md:w-[20vw] focus:outline-none border h-fit py-[0.60rem] px-2 rounded-md text-base md:text-lg">
            <option value="Difficulty" disabled>Difficulty</option>
            <option value="Easy">Easy</option>
            <option value="Medium">Medium</option>
            <option value="Hard">Hard</option>
          </select>
          <div className='w-[40vw] md:w-[20vw]'>
            <Input type='number' name='positiveMark' placeholder='Marks' onChnageFunction={onChangeFunction} />
          </div>
          <div>
            <Buttons onclick={onSubmitFunction} text="Programme" />
          </div>

        </div>
      </div>
      {
        programmes.length === 0 ?
          <div className='w-[100vw] flex justify-center items-center text-2xl font-medium'>
            No Programmes
          </div> :
          <div>
            <div className='text-lg md:text-base md:font-medium font-normal flex gap-4'>
              <div>Already Existed Programmes Are Below</div>
              <div className='font-extralight md:font-light'>(Hover To See Programmes id)</div>
            </div>
            <div className='grid grid-flow-row grid-cols-1 mt-7 gap-y-1'>
              <div className='grid grid-rows-1 grid-cols-9 place-items-center p-2 bg-blue text-white rounded-md font-extralight md:font-bold'>
                <div className='col-span-6'>Description</div>
                <div>Category</div>
                <div>Mark</div>
                <div>Test</div>
              </div>
              {
                programmes.map((item, index) => {
                  return (
                    <Link to={`/admin/test/${item.testId}/programme/${item.id}`} key={item.id} className='p-2 grid grid-rows-1 grid-cols-9 place-items-center bg-blue text-white rounded-md font-extralight'>
                      <div className='col-span-6'>{item.questionDescription}</div>
                      <div>{item.difficulty}</div>
                      <div>{item.positiveMark}</div>
                      <div>{tests.filter((test)=> test.id == item.testId)[0]?.title}</div>
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
