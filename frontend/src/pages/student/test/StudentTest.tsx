import { NavLink, Outlet, useParams } from 'react-router-dom'
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { StudentTestAtom } from '../../../store/atoms/StudentTestAtom';
import { TestInterface } from '../../../data/Interface';
import { useEffect } from 'react';
import { TestIdAtom } from '../../../store/atoms/TestIdAtom';

export const StudentTest = () => {
    const tests = useRecoilValue<TestInterface[]>(StudentTestAtom);
    const setTestId = useSetRecoilState<number>(TestIdAtom);
    const { id } = useParams();
    const test = tests?.filter((item) => item.id == parseInt(id || ""))?.[0];

    useEffect(() => {
        setTestId(parseInt(id || ""));
    }, []);

    return (
        <div>
            <div className='h-[7vh] flex justify-between px-7 items-center'>
                <div className='flex gap-2'>
                    <NavLink className={({ isActive }) => `${isActive ? 'text-white bg-blue' : 'text-black bg-white'} flex items-center justify-center p-2 rounded-md`} to={`/student/tests/${id}/mcq`} >MCQ's</NavLink>
                    <NavLink to={`/student/tests/${id}/programme`} className={({ isActive }) => `${isActive ? 'text-white bg-blue' : 'text-black bg-white'} flex items-center justify-center p-2 rounded-md`}>Programme</NavLink>
                </div>
                <div className='text-white bg-blue flex items-center justify-center p-2 rounded-md'>{test?.time}</div>
            </div>
            <Outlet />
        </div>
    )
}
