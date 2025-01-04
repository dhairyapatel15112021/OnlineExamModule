import { selector } from "recoil";
import { backendCall } from "../../helperFunctions/backendCall";
import { ApiEndPoints } from "../../data/ApiEndPoints";


export const TestsSelector = selector({
    key : "uniqueTestsSelector",
    get : async () => {
        try{
            const response = await backendCall({url : ApiEndPoints.getTests,    method : 'GET',header : localStorage.getItem("token") || "" ,fields : [{tests : []}]});
            if (!response || !response.data || response.data === null || response.err != "" || !response.data.tests) {
                throw new Error(response.err);
            }
            return response.data.tests;
        }
        catch(err){
            console.log(err);
            return [];
        }
    }
});