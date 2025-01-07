import { useEffect, useState } from 'react'
import { languageInterface } from '../../../../data/Interface';
import { backendCall } from '../../../../helperFunctions/backendCall';
import { ApiEndPoints } from '../../../../data/ApiEndPoints';

export const Languages = ({onchange} : any) => {
    const [language , setLanguage] = useState<languageInterface[]>([]);
    const getLanguages = async () => {
        try{
            const response = await backendCall({url : ApiEndPoints.getLanguage , method : 'GET' ,  header : localStorage.getItem("token") || "" , fields : [{ languages : [] }]});
            if(!response || !response.data || !response.data.languages || response.err != ""){
                throw new Error("something went wrong");
            }
            setLanguage(response.data.languages);
        }
        catch(err){
            console.log(err);
        }
    }

    useEffect(()=>{
        getLanguages();
    },[]);

  return (
    <select name='languageId' onChange={(event : any) =>  onchange(event.target.value?.split(' ')?.[0]?.toLowerCase())} className='p-2 text-base bg-blue text-white rounded-md focus:outline-none h-[5vh]'>
        {
            language?.map((item)=>{
                return(
                    <option value={item.languageName}>{item.languageName}</option>
                )
            })
        }
    </select>
  )
}
