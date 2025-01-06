import { selector } from "recoil"
import { backendCall } from "../../helperFunctions/backendCall";
import { ApiEndPoints } from "../../data/ApiEndPoints";
import { TestIdAtom } from "../atoms/TestIdAtom";
// fetch mcqs to diaply 
export const Mcqs = selector({
    key: "uniqueMcqsSelector",
    get: async ({get} : any) => {
        try {
            const testId = get(TestIdAtom);
            const response = await backendCall({ url: `${ApiEndPoints.getStudentMcqs}${testId}` ,  method: "GET", fields: [{ mcqs : [] }], header: localStorage.getItem("token") || "" });
            if (!response || !response.data || response.data === null || response.err != "" || !response.data.mcqs) {
                throw new Error(response.err);
            }
            return response.data.mcqs;
        }
        catch (err) {
            console.log(err);
            return [];
        }
    }
})