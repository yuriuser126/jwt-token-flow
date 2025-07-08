import React from 'react'
import './JoinForm.css'

// const JoinForm = () => {
const JoinForm = ({join}) => {
    const onJoin =(e)=>{
        // username,password,name,email 이거 e.target으로 받아서 가져감
        e.preventDefault();

        const form = e.target;
        const userId = form.username.value;
        const userPw = form.password.value;
        const name = form.name.value;
        const email = form.email.value;

        console.log(`@# form data => `,userId,userPw,name,email);

        //Join.jsx에 값 보냄
        join({userId,userPw,name,email});
        

    }
    return (
        
        <div className='form'>
            <h2 className='login-title'>회원가입</h2>
            {/* <form className='login-form'> */}
            <form className='login-form' onSubmit={(e)=>onJoin(e)}>
                {/* 여기에 함수 */}
               
                <div>
                    <label htmlFor='username'>username</label>
                    <input type='text' id='username' name='username' placeholder='username' required />
                </div>
                <div>
                    <label htmlFor='password'>password</label>
                    <input type='password' id='password' name='password' placeholder='password' required />
                </div>
                <div>
                    <label htmlFor='name'>name</label>
                    <input type='text' id='name' name='name' placeholder='name' required />
                </div>
                <div>
                    <label htmlFor='email'>email</label>
                    <input type='text' id='email' name='email' placeholder='email' required />
                </div>
                < button type='submit' className='btn btn--form btn-login'>
                Join
                </button>
            </form>

        </div>
    )
}

export default JoinForm