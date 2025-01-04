import * as XLSX from "xlsx";
import { backendCall } from "./backendCall";
import { data } from "../data/Interface";


export const OnFileChange = async (data: data) => {
    const file = data.file;
    if (!file) return;
    const jsondata = await readFile(file);
    if (jsondata) {
       try{
        const response = await backendCall({ url: data.url, data: jsondata, method: "POST", fields: data.fields, header: localStorage.getItem("token") || "" });
        if (!response || !response.data || response.data === null || response.err != "") {
            throw new Error(response.err);
        }
        console.log(response.data);
       }
       catch(err){
        console.log(err);
       }
    }
    else {
        console.log("data is not read properly");
    }
}

const readFile = (file: File): Promise<any> => {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.onload = (e) => {
            const data = e.target?.result;
            if (!data) {
                reject("failed to read file");
            };

            const workbook: XLSX.WorkBook = XLSX.read(data, { type: "array" });
            const firstSheetName: string = workbook.SheetNames[0];
            const firstSheet: XLSX.WorkSheet = workbook.Sheets[firstSheetName];

            const jsonData = XLSX.utils.sheet_to_json(firstSheet);
            return resolve(jsonData);
        }

        reader.onerror = reader.onerror = (error) => {
            reject(error);
        };

        reader.readAsArrayBuffer(file);
    })
}