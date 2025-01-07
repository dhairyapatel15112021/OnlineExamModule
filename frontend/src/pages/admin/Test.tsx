import { Outlet, useParams } from 'react-router-dom'
import { useRecoilValue } from 'recoil';
import { TestAtom } from '../../store/atoms/TestAtom';
import { TestInterface } from '../../data/Interface';
import { NavLink } from 'react-router-dom';


export const Test = () => {
    const {id} = useParams();
    const tests = useRecoilValue<TestInterface[]>(TestAtom);
    const test : TestInterface = tests.filter((item)=> item.id === parseInt(id || ""))?.[0] || null;

  return (
    <div className='p-7 flex flex-col gap-2 w-full h-ful'>
        <div className='flex justify-evenly md:justify-between font-light md:font-normal'> 
            <div>
                <span className='font-medium md:font-bold underline underline-offset-2'>Title</span> : {test?.title}
            </div>
            <div>
            <span className='font-medium md:font-bold underline underline-offset-2'>Total Apptitude Question</span> : {test?.totalApptitudeQuestion}
            </div>
            <div>
            <span className='font-medium md:font-bold underline underline-offset-2'>Total Programming Question</span> : {test?.totalProgrammingQuestion}
            </div>
            <div>
            <span className='font-medium md:font-bold underline underline-offset-2'>Total Technical Question</span> : {test?.totalTechnicalQuestion}
            </div>
        </div>
        <div className='p-2 w-full border border-black rounded-md mt-3'>
            <div className='flex'>
                <NavLink className={({isActive})=>`${isActive ? 'text-white bg-blue' : 'text-black bg-white'} w-[50%] flex items-center justify-center p-2 rounded-md`} to={`/admin/test/${id}/mcq`} >MCQ's</NavLink>
                <NavLink to={`/admin/test/${id}/programme`} className={({isActive})=>`${isActive ? 'text-white bg-blue' : 'text-black bg-white'} w-[50%] flex items-center justify-center p-2 rounded-md`}>Programme</NavLink>
            </div>
            <Outlet/>
        </div>   
    </div>
  )
}
