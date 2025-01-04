import { atom } from "recoil";

export const login = atom({
    key : "email",
    default : {emailId : "" , isAdmin : false}
});