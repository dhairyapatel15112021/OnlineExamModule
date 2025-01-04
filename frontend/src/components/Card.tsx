import { Link } from "react-router-dom"

interface data {
    to: string,
    text: string
}
export const Card = (input: data) => {
    return (
        <div className="w-[90vw] lg:w-[29.5vw] h-[30vh] lg:h-[33vh] bg-[#f2f1f1] shadow-md rounded-md hover:bg-white hover:scale-105 transition-all duration-500">
            <Link to={input.to} className="flex justify-center items-center h-full w-full text-3xl font-bold tracking-widest">
                {input.text}
            </Link>
        </div>
    )
}
