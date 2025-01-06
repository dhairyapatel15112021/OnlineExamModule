import { selector } from "recoil"
import { backendCall } from "../../helperFunctions/backendCall";
import { ApiEndPoints } from "../../data/ApiEndPoints";
import { TestIdAtom } from "../atoms/TestIdAtom";
// fetch programme
export const Programmes = selector({
    key: "uniqueProgrammesSelector",
    get: async ({get} : any) => {
        try {
            const testId = get(TestIdAtom);
            const response = await backendCall({ url: `${ApiEndPoints.getStudentProgrammes}${testId}` ,  method: "GET", fields: [{ programmes : [] }], header: localStorage.getItem("token") || "" });
            if (!response || !response.data || response.data === null || response.err != "" || !response.data.programmes) {
                throw new Error(response.err);
            }
            return response.data.programmes;
        }
        catch (err) {
            console.log(err);
            return [];
        }
    }
})