import { atom } from "recoil";
import { Colleges } from "../selectors/CollegesSelector";
import { CollegeInterface } from "../../data/Interface";



// Check if the atom already exists to handle HMR
const existingAtom = typeof window !== "undefined" && (window as any).__recoilAtoms?.ColllegeAtom;

export const ColllegeAtom = existingAtom || atom<CollegeInterface[]>({
    key: "uniqueAtomColleges",
    default: Colleges,
});

// Store atom in a global variable to prevent redefinition
if (typeof window !== "undefined") {
    (window as any).__recoilAtoms = { ...((window as any).__recoilAtoms || {}), ColllegeAtom };
}