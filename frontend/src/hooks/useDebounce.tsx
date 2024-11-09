import { useEffect, useState } from "react"

interface data {
    emailId? : string,
    password? : string
}

export const useDebounce = (data : data) : any => {
    const [debounceData,setDebounceData] = useState(data);
    useEffect(()=>{
        const timeout = setTimeout(()=>{
            setDebounceData(data);
        },1000);
        return () => clearTimeout(timeout);
    },[data]);
  return (debounceData)
}