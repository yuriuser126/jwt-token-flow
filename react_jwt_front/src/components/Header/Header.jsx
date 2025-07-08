import React from 'react'
import { Link } from 'react-router-dom'
import reactLogo from '../../assets/react.svg'
import './Header.css'
import { useContext } from 'react'
import { LoginContext } from '../../contexts/LoginContextProvider'

const Header = () => {
    //✅isLogin : 로그인 여부 -Y(true) F(false)
    const { isLogin } = useContext(LoginContext);

    return (
        <header>
            <div>
                <Link to="/">
                    <img src={reactLogo} className="logo react" alt="React logo" />
                </Link>
            </div>
            <div className='util'>
                {/* 로그인 전 */}
                {/* { !isLogin ? 로그인 전 : 로그인후 */}
                {
                       
                    !isLogin
                        ?
                        <ul>
                            <li>
                                <Link to={"/login"}>로그인</Link>
                            </li>
                            <li>
                                <Link to={"/join"}>회원가입</Link>
                            </li>
                            <li>
                                <Link to={"/about"}>소개</Link>
                            </li>
                        </ul>

                        :
                        <ul>
                            <li>
                                <Link to={"/user"}>마이페이지</Link>
                            </li>
                            <li>
                                <button className='link'>로그아웃</button>
                            </li>
                        </ul>
                }

                {/* 로그인 후 */}
            </div>

        </header>
    )
}

export default Header