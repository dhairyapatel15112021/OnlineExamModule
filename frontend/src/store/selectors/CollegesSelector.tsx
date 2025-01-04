import { selector } from "recoil"
import { backendCall } from "../../helperFunctions/backendCall";
import { ApiEndPoints } from "../../data/ApiEndPoints";

export const Colleges = selector({
    key: "uniqueCollegeSelector",
    get: async () => {
        try {
            const response = await backendCall({ url: ApiEndPoints.getCollege ,  method: "GET", fields: [{ clg: [] }], header: localStorage.getItem("token") || "" });
            if (!response || !response.data || response.data === null || response.err != "" || !response.data.clg) {
                throw new Error(response.err);
            }
            return response.data.clg;
        }
        catch (err) {
            console.log(err);
            return [];
        }
    }
})