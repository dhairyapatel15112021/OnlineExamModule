import axios, { AxiosRequestConfig } from 'axios';

interface backendData{
    url : string,
    method : string,
    data? : any
}

export const backendCall = async (data : backendData) => {
    const responseData = {data : "" , err : ""};
    const abortController = new AbortController();
    const axiosConfig : AxiosRequestConfig = {
        url : data.url,
        method : data.method,
        signal : abortController.signal
    }
    if(data.method !== 'GET' && data.data){
        axiosConfig.data = data.data;
    }
    try{
        const response = await axios(axiosConfig);
        responseData.data = response.data;
    }
    catch(err : any){
        responseData.err = err.response?.data || err.message;
    }
    return {...responseData, abort: () => abortController.abort()};
}

/*
When to Use the abort() Function?
You only need the abort() function if:
1) You need the ability to cancel the request programmatically at a later time.
2) You're in a scenario like a React component unmounting and want to cancel pending requests.
*/