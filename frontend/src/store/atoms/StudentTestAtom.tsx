import { atom } from "recoil";
import {  TestInterface } from "../../data/Interface";
import { StudentTest } from "../selectors/StudentTestSelector";



// Check if the atom already exists to handle HMR
const existingAtom = typeof window !== "undefined" && (window as any).__recoilAtoms?.StudentTestAtom;

export const StudentTestAtom = existingAtom || atom<TestInterface[]>({
    key: "uniqueAtomColleges",
    default: StudentTest,
});

// Store atom in a global variable to prevent redefinition
if (typeof window !== "undefined") {
    (window as any).__recoilAtoms = { ...((window as any).__recoilAtoms || {}), StudentTestAtom };
}