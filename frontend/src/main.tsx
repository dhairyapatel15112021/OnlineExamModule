import { StrictMode, Suspense } from 'react'
import { createRoot } from 'react-dom/client'

import './index.css'
import App from './App.tsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import { Login } from './pages/Login.tsx'
import { Home } from './pages/Home.tsx'
import { Signup } from './pages/Signup.tsx'
import { AdminDashBoard } from './pages/admin/AdminDashBoard.tsx'
import { Students } from './pages/admin/Students.tsx'
import { Tests } from './pages/admin/Tests.tsx'
import { Results } from './pages/admin/Results.tsx'
import { Batch } from './pages/admin/Batch.tsx'
import { College } from './pages/admin/College.tsx'
import { Test } from './pages/admin/Test.tsx'
import { Mcq } from './pages/admin/Test/Mcq.tsx'
import { Programme } from './pages/admin/Test/Programme.tsx'
import { Testcases } from './pages/admin/Test/Testcases.tsx'

// toast library for proper error and success msg for everything,
// if needed regex for email , password phonenumber etc?.
// full proof guide of .txt or something else so another random user also can see our project.
// create excel file and create modal to show response of uploading file (excel file).
// create search bar , filter (by name, clg name ete etc) , pagination, order by etc in all pages.
// students page : get students or already existed students show's implemetation is not done.
// mcqs page : get mcqs or already existed mcqs show's implemetation is not done.
// mcqs or programme excel file feature only applicable for one testId not multiple testId, so please configure it
// crud operations or APIS for every page like update , delete etc.
// change loader
// form reset at every succesful form submission

export const Loader = () => {
  return(
    <div>loading...</div>
  )
}

const router = createBrowserRouter([
  {path : "/" , element : <Suspense fallback={<Loader/>}><App/></Suspense> , children : [
    {path : "" ,index : true, element : <Home/>},
    {path : "login" , element : <Login/>},
    {path : "signup" , element : <Signup/>},
    {path : "admin" , children : [
      {path : "dashboard" , element : <Suspense fallback={<Loader/>}><AdminDashBoard/></Suspense> },
      {path : "students" , element : <Suspense fallback={<Loader/>}><Students/></Suspense>},
      {path : "tests" , element : <Suspense fallback={<Loader/>}><Tests/></Suspense>  },
      {path : "test/:id" , element : <Suspense fallback={<Loader/>}><Test/></Suspense> , 
        children : [
          {path : "mcq" , element : <Suspense fallback={<Loader/>}><Mcq/></Suspense>},
          {path : "programme",element:<Suspense fallback={<Loader/>}><Programme/></Suspense>},
          {path : "programme/:programmeId",element:<Suspense fallback={<Loader/>}><Testcases/></Suspense>}
        ]},
      {path : "results" , element : <Suspense fallback={<Loader/>}><Results/></Suspense> },
      {path : "college" , element : <Suspense fallback={<Loader/>}><College/></Suspense> },
      {path : "batch",element : <Suspense fallback={<Loader/>}> <Batch/></Suspense>}
    ]}
  ]}
]
)

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
