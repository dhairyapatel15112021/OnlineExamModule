import { atom } from "recoil";
import { TestsSelector } from "../selectors/TestsSelector";
import { TestInterface } from "../../data/Interface";



// Check if the atom already exists to handle HMR
const existingAtom = typeof window !== "undefined" && (window as any).__recoilAtoms?.TestAtom;

export const TestAtom = existingAtom || atom<TestInterface[]>({
    key: "uniqueAtomTest",
    default: TestsSelector,
});

// Store atom in a global variable to prevent redefinition
if (typeof window !== "undefined") {
    (window as any).__recoilAtoms = { ...((window as any).__recoilAtoms || {}), TestAtom };
}