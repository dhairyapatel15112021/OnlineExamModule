interface data {
    type: string,
    name: string,
    placeholder: string,
    onChnageFunction(event: any): any
}

export const Input = (input: data) => {
    return (
        <input type={input.type} name={input.name} placeholder={input.placeholder} className='text-black w-[100%] h-fit p-2 focus:outline-none border rounded-md text-base md:text-lg md:placeholder:tracking-wider' onChange={input.onChnageFunction} />
    )
}
