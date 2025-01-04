import { useParams } from 'react-router-dom'
import { useRecoilValue } from 'recoil';
import { ProgrammeAtom } from '../../../store/atoms/ProgrammeAtom';
import { ProgrammeInterface, testcasesInterface } from '../../../data/Interface';
import { Input } from '../../../components/Input';
import { Buttons } from '../../../components/Buttons';
import { useState } from 'react';
import { OnFileChange } from '../../../helperFunctions/OnFileChange';
import { ApiEndPoints } from '../../../data/ApiEndPoints';
import { validateData } from '../../../helperFunctions/validateData';
import { backendCall } from '../../../helperFunctions/backendCall';

export const Testcases = () => {
    const { programmeId } = useParams();
    const programmes = useRecoilValue<ProgrammeInterface[]>(ProgrammeAtom);
    const programme = programmes.filter((item) => item.id === parseInt(programmeId || ""))?.[0];
    const [testcase, setTestcases] = useState<testcasesInterface>({ id: 0, programmeId: parseInt(programmeId || ""), input: "", output: "" });

    const onChangeFunction = (event: any) => {
        setTestcases({ ...testcase, [event.target.name]: event.target.value });
    }

    const onFileChangeFunction = (event: any) => {
        const file: File = event.target.files?.[0];
        OnFileChange({ file: file, url: ApiEndPoints.testcasesRegister, fields: [{ "success": [] }, { "failure": [] }] });
    }

    const onSubmitFunction = async () => {
        try {
            const validateResponse = validateData({ data: testcase, fields: ["programmeId", "input", "output"] });
            if (!validateResponse.isOk) {
                console.log(validateResponse.error);
                return;
            }
            const { id, ...data } = testcase;
            const response = await backendCall({ url: ApiEndPoints.testcasesRegister, data: [data], method: 'POST', header: localStorage.getItem("token") || "", fields: [{ success: [] }, { failure: [] }] });
            if (!response || !response.data || response.data === null || response.err != "") {
                throw new Error(response.err);
            }
            //setTestcases((prevTestcases) => [...prevTestcases, { ...testcase, id: response.data.success?.[0].id || 0 }]);
            setTestcases({ id: 0, programmeId: parseInt(programmeId || ""), input: "", output: "" });
            console.log(response.data);
        }
        catch (err) {
            console.log(err);
        }
    }

    return (
        <div className='flex flex-col'>
            <div className='h-[35vh] md:h-[25vh] flex flex-col justify-evenly'>
                <div className='flex justify-evenly md:justify-between font-light md:font-normal'>
                    <div>
                        <span className='font-medium md:font-bold underline underline-offset-2'>Question</span> : {programme?.questionDescription}
                    </div>
                    <div>
                        <span className='font-medium md:font-bold underline underline-offset-2'>Difficulty</span> : {programme?.difficulty}
                    </div>
                </div>
                <div className='text-lg md:text-base md:font-medium font-normal flex justify-between'>
                    <div>Submit This Form To Create Testcases or <span className="underline underline-offset-4">Upload File</span> </div>
                    <label htmlFor="testcaseFile">
                        <div className="px-4 py-2 bg-blue rounded-md border border-blue text-white hover:text-blue hover:bg-white tracking-normal md:tracking-wider text-nowrap">Upload File</div>
                        <input onChange={onFileChangeFunction} type="file" name="file" id="testcaseFile" accept=".xlsx" className="hidden" />
                    </label>
                </div>
                <div className=' w-[100vw] flex flex-wrap items-start gap-3 md:gap-4'>
                    <div className='w-[40vw] md:w-[30vw]'>
                        <Input type='text' name='input' placeholder='Input' onChnageFunction={onChangeFunction} />
                    </div>
                    <div className='w-[40vw] md:w-[30vw]'>
                        <Input type='text' name='output' placeholder='Output' onChnageFunction={onChangeFunction} />
                    </div>
                    <div>
                        <Buttons onclick={onSubmitFunction} text="Testcases" />
                    </div>
                </div>
            </div>
            {/* {
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
                  <div  key={item.id} className='p-2 grid grid-rows-1 grid-cols-9 place-items-center bg-blue text-white rounded-md font-extralight'>
                    <div className='col-span-6'>{item.questionDescription}</div>
                    <div>{item.difficulty}</div>
                    <div>{item.positiveMark}</div>
                    <div>{tests.filter((test)=> test.id == item.testId)[0]?.title}</div>
                  </div>
                )
              })
            }

          </div>
        </div>
    } */}
        </div>
    )
}
