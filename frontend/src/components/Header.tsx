import { Link, useNavigate } from "react-router-dom"
import { useRecoilState } from "recoil"
import { login } from "../store/atoms/login"
import { useEffect, useState } from "react";
import { backendCall } from "../helperFunctions/backendCall";
import { ApiEndPoints } from "../data/ApiEndPoints";

export const Header = () => {
  const [user,setUser] = useRecoilState(login);
  const navigate = useNavigate();
  const [text, setText] = useState("LOGIN");

  useEffect(() => {
    if (user.emailId.trim() != "") {
      setText("LOGOUT");
      return;
    }
    setText("LOGIN");
  }, [user]);

  const logout = async () => {
    try {
      const response = await backendCall({ url: ApiEndPoints.logout, method: 'POST', fields: [{ message: "" }] });
      if(response.err.trim()!=""){
        return;
      }
      setUser({isAdmin : false , emailId : ""});
      localStorage.removeItem("token");
      navigate("/login");
    }
    catch (err) {
      console.log(err);
    }
  }
  return (
    <div className='h-[10vh] p-2 flex justify-center items-center shadow'>
      <div className='h-[100%] w-[100%] flex justify-between items-center'>
        <Link className='px-5 text-3xl tracking-widest' to="/">ROIMA</Link>
        {
          text.trim() === "LOGIN" ?
            <Link className="mx-3 px-3 py-2 tracking-widest text-lg bg-blue text-white rounded-md hover:bg-white hover:text-blue border border-blue" to="/login">{text}</Link>
            :
            <div className="mx-3 px-3 py-2 tracking-widest text-lg bg-blue text-white rounded-md hover:bg-white hover:text-blue border border-blue cursor-pointer" onClick={logout}>{text}</div>
        }
      </div>
    </div>
  )
}
