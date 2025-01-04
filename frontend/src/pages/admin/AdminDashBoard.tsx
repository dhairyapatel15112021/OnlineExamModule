import { Card } from '../../components/Card'

export const AdminDashBoard = () => {
    const data = [ { to : "/admin/students" , text : "Students"}, 
        {to : "/admin/tests" , text : "Tests"},
        {to : "/admin/results" , text : "Results"},
        {to : "/admin/college" , text : "College"},
        {to : "/admin/batch" , text : "Batch" }
    ]
  return (
    <div className='flex flex-col h-[175vh] lg:h-[90vh] w-[100vw]'>
        <div className='h-[10vh] flex items-center pl-[5vw] lg:pl-[3vw] text-xl font-medium tracking-wide'>
            Dashboard 
        </div>
        <div className='h-[160vh] lg:h-[75vh] w-[100vw] flex justify-start flex-wrap pl-[5vw] lg:pl-[3vw] lg:gap-x-[3vw] gap-y-[2vh] lg:gap-y-[1.5vh]'>
           {
            data.map((item,index)=>{
                return(
                    <Card key={index} to={item.to} text={item.text}  />
                )
            })
           }
        </div>
    </div>
  )
}
