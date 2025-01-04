import axios, { AxiosRequestConfig } from 'axios';
import { backendData, backendResponse } from '../data/Interface';


export const backendCall = async (data: backendData): Promise<backendResponse> => {

    const responseData = { data: {}, err: "" };

    for (let fields of data.fields) {
        responseData.data = { ...responseData.data, fields };
    }

    const abortController = new AbortController();
    const axiosConfig: AxiosRequestConfig = {
        url: data.url,
        method: data.method,
        signal: abortController.signal,
    }

    if (data.method !== 'GET' && data.data) {
        axiosConfig.data = data.data;
    }

    if (data.header && data.header.trim() != "") {
        axiosConfig.headers = { Authorization: data.header};
    }

    try {
        console.log(axiosConfig);
        const response = await axios(axiosConfig);
        responseData.data = response.data;
    }
    catch (err: any) {
        responseData.err = err.response?.data || err.message;
    }

    return { ...responseData, abort: () => abortController.abort() };
}

/*
When to Use the abort() Function?
You only need the abort() function if:
1) You need the ability to cancel the request programmatically at a later time.
2) You're in a scenario like a React component unmounting and want to cancel pending requests.
*/