interface data {
    text : string,
    onclick () : any
}

export const Buttons = (input : data) => {
  return (
    <button className='bg-blue text-white px-4 md:px-5 py-2 w-fit h-fit self-end border border-blue text-base md:text-xl tracking-widest hover:bg-white hover:text-blue rounded-md text-nowrap' onClick={input.onclick}>{input.text}</button>
  )
}
