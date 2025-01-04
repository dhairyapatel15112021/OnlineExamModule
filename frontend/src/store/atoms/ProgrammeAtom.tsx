import { atom } from "recoil";
import { ProgrammeInterface } from "../../data/Interface";



// Check if the atom already exists to handle HMR
const existingAtom = typeof window !== "undefined" && (window as any).__recoilAtoms?.ProgrammeAtom;

export const ProgrammeAtom = existingAtom || atom<ProgrammeInterface[]>({
    key: "uniqueAtomProgramme",
    default: [],
});

// Store atom in a global variable to prevent redefinition
if (typeof window !== "undefined") {
    (window as any).__recoilAtoms = { ...((window as any).__recoilAtoms || {}), ProgrammeAtom };
}