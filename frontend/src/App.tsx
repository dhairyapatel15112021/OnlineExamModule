import { Outlet } from 'react-router-dom';
import './App.css';
import { Header } from './components/Header';
import { RecoilRoot } from 'recoil';

function App() {

  return (
    <div className='m-0 p-0 h-[100vh] w-[100vw]'>
      <RecoilRoot>
          <Header />
          <Outlet />
      </RecoilRoot>
    </div>
  )
}

export default App
