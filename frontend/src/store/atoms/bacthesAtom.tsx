import { atom } from "recoil";
import { batch } from "../selectors/batchesSelector";
import { BatchInterface } from "../../data/Interface";


// Check if the atom already exists to handle HMR
const existingAtom = typeof window !== "undefined" && (window as any).__recoilAtoms?.batchAtom;

export const batchAtom = existingAtom || atom<BatchInterface[]>({
    key: "uniqueAtomBatches",
    default: batch,
});

// Store atom in a global variable to prevent redefinition
if (typeof window !== "undefined") {
    (window as any).__recoilAtoms = { ...((window as any).__recoilAtoms || {}), batchAtom };
}