import { selector } from "recoil";
import { backendCall } from "../../helperFunctions/backendCall";
import { ApiEndPoints } from "../../data/ApiEndPoints";
// fetch batches
export const batch = selector({
    key : "uniqueBatchesSelector",
    get : async () => {
        try{
            const response = await backendCall({url : ApiEndPoints.getBatch, method : "GET" , fields : [{batch : []}] , header : localStorage.getItem("token") || ""});
            if(!response || !response.data || response.data === null || response.err != "" || !response.data.batch ){
                throw new Error(response.err);
            }
            return response.data.batch;
        }
        catch(err){
            console.log(err);
            return [];
        }
    },
})