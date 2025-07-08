import { BrowserRouter, Route, Routes } from "react-router-dom"
import Home from "./pages/Home"
import Login from "./pages/Login"
import Join from "./pages/Join"
import User from "./pages/User"
import About from "./pages/About"
import LoginContextProvider, { LoginContext } from "./contexts/LoginContextProvider"


// LoginContextProvider : 모든 라우터안에 로그인 여부 제공 consumer로 쓸수있음

function App() {

  return (
    <BrowserRouter>
    <LoginContextProvider>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/login" element={<Login/>} />
        <Route path="/join" element={<Join/>} />
        <Route path="/user" element={<User/>} />
        <Route path="/about" element={<About/>} />
      </Routes>
      </LoginContextProvider>
    </BrowserRouter>
  )
}

export default App
