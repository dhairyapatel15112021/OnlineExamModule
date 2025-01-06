import { atom } from "recoil";

// Check if the atom already exists to handle HMR
const existingAtom = typeof window !== "undefined" && (window as any).__recoilAtoms?.TestIdAtom;

export const TestIdAtom = existingAtom || atom<number>({
    key: "uniqueAtomTestId",
    default: 0,
});

// Store atom in a global variable to prevent redefinition
if (typeof window !== "undefined") {
    (window as any).__recoilAtoms = { ...((window as any).__recoilAtoms || {}), TestIdAtom };
}