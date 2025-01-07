import { useRecoilValue } from "recoil"
import { McqInterface } from "../../../data/Interface";
import { Mcqs } from "../../../store/selectors/McqSelector";
import { useState } from "react";

export const StudentMcq = () => {
  const mcqs = useRecoilValue<McqInterface[]>(Mcqs);
  const [questionIndex, setQuestinIndex] = useState<number>(0);

  const changeIndex = (change : number) => {
    if(questionIndex + change < 0 || questionIndex + change >= mcqs.length) return;
    setQuestinIndex(questionIndex + change);
  }

  return (
    <div className="flex">
      <div className='h-[83vh] w-[70vw] p-7'>
        <div className='h-full w-full border border-black rounded-md p-3 flex flex-col justify-between overflow-y-scroll'>
          <div className="flex flex-col gap-3">
            <div className="font-semibold text-lg">{mcqs?.[questionIndex]?.questionDescription}</div>
            <div className="flex flex-col gap-2 text-lg font-normal">
                {mcqs?.[questionIndex]?.option1 && 
                <div className="flex gap-3 ml-2">
                  <input type="radio" id="response" name="response" className="focus:outline-none" />
                   <div>{mcqs?.[questionIndex]?.option1}
                </div>
                </div>} 

                {mcqs?.[questionIndex]?.option2 && 
                <div className="flex gap-3 ml-2">
                  <input type="radio" id="response" name="response" className="focus:outline-none" />
                   <div>{mcqs?.[questionIndex]?.option2}
                </div>
                </div>} 

                {mcqs?.[questionIndex]?.option3 && 
                <div className="flex gap-3 ml-2">
                  <input type="radio" id="response" name="response" className="focus:outline-none" />
                   <div>{mcqs?.[questionIndex]?.option3}
                </div>
                </div>} 

                {mcqs?.[questionIndex]?.option4 && 
                <div className="flex gap-3 ml-2">
                  <input type="radio" id="response" name="response" className="focus:outline-none" />
                   <div>{mcqs?.[questionIndex]?.option4}
                </div>
                </div>}
            </div>
          </div>
          <div className="flex gap-2">
                <button onClick={() => changeIndex(-1)} className='px-4 py-2 bg-blue text-white rounded-md h-fit w-fit'>prev</button>
                <button onClick={() => changeIndex(1)} className='px-4 py-2 bg-blue text-white rounded-md h-fit w-fit'>next</button>
          </div>
        </div>
      </div>
      <div className='w-[30vw] h-[83vh] p-7'>
        <div className='h-full w-full border border-black rounded-md p-3 overflow-y-scroll'>
          <div className="flex flex-wrap gap-2">
            {
              mcqs?.map((_, index) => {
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
