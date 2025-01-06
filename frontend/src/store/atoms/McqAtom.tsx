import { atom } from "recoil";
import { McqInterface } from "../../data/Interface";


export const McqAtom = atom<McqInterface[]>({
    key : "uniqueMcqAtom",
    default : []
})