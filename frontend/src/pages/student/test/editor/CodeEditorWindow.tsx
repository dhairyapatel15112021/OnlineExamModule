import Editor from "@monaco-editor/react";
import { useState } from "react";

interface editorInterface{
    language : string,
    code : string,
    theme : string,
    onChange : any
}
export const CodeEditorWindow = (data : editorInterface) => {
    const [value, setValue] = useState(data.code || "");
    const handleEditorChange = (value : any) => {
        setValue(value);
        data.onChange(value);
    };
  return (
    <div className='overlay rounded-md overflow-hidden w-full h-full shadow-4xl'>
        <Editor
        height="64vh"
        width={`100%`}
        language={data.language || "cpp"}
        value={value}
        theme={data.theme}
        defaultValue="// some comment"
        onChange={handleEditorChange}
      />
    </div>
  )
}
