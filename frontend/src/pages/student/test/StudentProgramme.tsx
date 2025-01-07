import { useRecoilValue } from 'recoil';
import { Programmes } from '../../../store/selectors/ProgrammeSelector';
import { ProgrammeInterface, testcasesInterface } from '../../../data/Interface';
import { useEffect, useState } from 'react';
import { backendCall } from '../../../helperFunctions/backendCall';
import { ApiEndPoints } from '../../../data/ApiEndPoints';
import { EditorMain } from './editor/EditorMain';

export const StudentProgramme = () => {
  const programmes = useRecoilValue<ProgrammeInterface[]>(Programmes);
  const [questionIndex, setQuestinIndex] = useState<number>(0);
  const [testscases, setTestcases] = useState<testcasesInterface[]>([]);

  const changeIndex = (change: number) => {
    if (questionIndex + change < 0 || questionIndex + change >= programmes.length) return;
    setQuestinIndex(questionIndex + change);
  }
  const getTestcases = async () => {
    try {
      const response = await backendCall({ url: ApiEndPoints.getTestcases + programmes?.[questionIndex].id, header: localStorage.getItem("token") || "", fields: [{ testscases: [] }], method: 'GET' });
      if (!response || !response.data || response.data === null || response.err != "" || !response.data.testcases) {
        throw new Error(response.err);
      }
      setTestcases(response.data.testcases);
    }
    catch (err) {
      console.log(err);
    }
  }

  useEffect(() => {
    getTestcases();
  }, [questionIndex]);


  return (
    <div className="flex">
      <div className='h-[83vh] w-[20vw] p-7'>
        <div className='h-full w-full border border-black rounded-md p-3 flex flex-col justify-between overflow-y-scroll'>
          <div className="flex flex-col gap-3">
            <div className="font-semibold text-lg">{questionIndex + 1}. {programmes?.[questionIndex]?.questionDescription}</div>
            <div className="flex flex-col gap-2 text-base font-normal">
              {testscases?.map((testcase, index) => {
                return (
                  <div className='flex flex-col' key={testcase.id}>
                    <div className='font-semibold'>Example : {index + 1}</div>
                    <div className='font-light ml-2 mt-2'> <span className='font-medium'>Input :</span> {testcase.input}</div>
                    <div className='font-light ml-2'> <span className='font-medium'>Output :</span> {testcase.output}</div>
                  </div>
                )
              })}
            </div>
          </div>
          <div className="flex gap-2">
            <button onClick={() => changeIndex(-1)} className='px-4 py-2 bg-blue text-white rounded-md h-fit w-fit'>prev</button>
            <button onClick={() => changeIndex(1)} className='px-4 py-2 bg-blue text-white rounded-md h-fit w-fit'>next</button>
          </div>
        </div>
      </div>
      <EditorMain/>
      <div className='w-[20vw] h-[83vh] p-7'>
        <div className='h-full w-full border border-black rounded-md p-3'>
          <div className="flex flex-wrap gap-2">
            {
              programmes?.map((_, index) => {
                return (
                  <button onClick={() => setQuestinIndex(index)} className='px-4 py-2 bg-blue text-white rounded-md h-fit w-fit'>{index + 1}</button>
                )
              })
            }
          </div>
        </div>
      </div>
    </div>
  )
}
