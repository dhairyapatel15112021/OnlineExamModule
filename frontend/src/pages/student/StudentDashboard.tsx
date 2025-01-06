import { Card } from '../../components/Card';

export const StudentDashboard = () => {
    const data = [ 
        {to : "/student/tests" , text : "Tests"},
        {to : "/student/results" , text : "Results"}
    ]
  return (
   <div className='flex flex-col h-[75vh] lg:h-[90vh] w-[100vw]'>
          <div className='h-[10vh] flex items-center pl-[5vw] lg:pl-[3vw] text-xl font-medium tracking-wide'>
              Student Dashboard 
          </div>
          <div className='h-[65vh] lg:h-[75vh] w-[100vw] flex justify-start flex-wrap pl-[5vw] lg:pl-[3vw] lg:gap-x-[3vw] gap-y-[2vh] lg:gap-y-[1.5vh]'>
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
