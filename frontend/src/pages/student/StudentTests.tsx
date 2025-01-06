import { TestInterface } from '../../data/Interface';
import { Card } from '../../components/Card';
import { useRecoilValue } from 'recoil';
import { StudentTestAtom } from '../../store/atoms/StudentTestAtom';

export const StudentTests = () => {
    const tests = useRecoilValue<TestInterface[]>(StudentTestAtom);

    return (
        <div className='flex flex-col w-[100vw]'>
            <div className='h-[10vh] flex items-center pl-[5vw] lg:pl-[3vw] text-xl font-medium tracking-wide'>
                Below Are Tests :
            </div>
            {
                tests?.length != 0 ?
                    <div className='w-[100vw] flex justify-start flex-wrap pl-[5vw] lg:pl-[3vw] lg:gap-x-[3vw] gap-y-[2vh] lg:gap-y-[1.5vh]'>
                        {
                            tests?.map((item, index) => {
                                return (
                                    <Card to={`/student/tests/${item.id}/mcq`} key={item.id} text={item.title}></Card>
                            )
                            })
                        }
                    </div>
                    :
                    <div className='h-[80vh] w-[100vw] flex justify-center items-center text-xl font-medium tracking-wide'>
                        No Tests
                    </div>
            }
        </div>
    )
}
