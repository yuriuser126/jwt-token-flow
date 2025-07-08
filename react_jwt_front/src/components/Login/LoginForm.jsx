import React, { useContext } from 'react'
import './LoginForm.css'
import { LoginContext } from '../../contexts/LoginContextProvider'

const LoginForm = () => {
    //로그인값 받아내기위해
    const {login} = useContext(LoginContext);
    
    return (
        <div className='form'>
            <h2 className='login-title'>로그인</h2>
            <form className='login-form'>
                {/* required : 필수값 / 아이디 비밀번호 */}
                <div>
                    <label htmlFor='username'>username</label>
                    <input type='text' id='username' name='username' placeholder='username' required />
                </div>
                <div>
                    <label htmlFor='password'>password</label>
                    <input type='password' id='password' name='password' placeholder='password' required />
                </div>
                < button type='submit' className='btn btn--form btn-login'>
                Login
                </button>
            </form>

        </div>
    )
}

export default LoginForm