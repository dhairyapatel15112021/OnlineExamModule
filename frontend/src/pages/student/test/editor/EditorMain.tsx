import { useState } from 'react';
import { Buttons } from '../../../../components/Buttons'
import { CodeEditorWindow } from './CodeEditorWindow'
import { Languages } from './Languages'

const defaultCodeEditorContent = "\n\n//  select the language and code here";

export const EditorMain = () => {
    const [code, setCode] = useState(defaultCodeEditorContent);
    //const [outputDetails, setOutputDetails] = useState(null);
    const [language, setLanguage] = useState("java");
    //const [processing, setProcessing] = useState(null);
    const [theme, _] = useState("vs-dark"); // we can create atom for it and use it in this and CodeEditorWindow Component

    return (
        <div className='h-[83vh] w-[60vw] p-2'>
            <div className='border border-black rounded-md h-full w-full'>
                <div className='flex justify-end items-center gap-2'>
                    <Languages onchange={setLanguage} />
                </div>
                <div className='p-2'>
                    <CodeEditorWindow code={code} onChange={setCode} theme={theme} language={language} />
                </div>
                <div className='flex justify-center items-center gap-2 h-[10vh] p-2'>
                    <div className='w-full border border-black h-full p-1'>
                        <div className='font-medium'>code status :</div>
                    </div>
                    <Buttons onclick={() => console.log("hi")} text='Run' />
                </div>
            </div>
        </div>
    )
}
