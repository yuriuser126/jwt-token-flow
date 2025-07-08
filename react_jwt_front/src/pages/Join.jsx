import React from 'react'
import Header from '../components/Header/Header'
import LoginContextConsumer from '../contexts/LoginContextConsumer'
import JoinForm from '../components/Join/JoinForm'
import { useNavigate } from 'react-router-dom'
import * as auth from '../apis/auth' // auth파일 함수사용.(url)
import * as Swal from '../apis/alert' // alert Swal사용 알림 디자인



const Join = () => {
    const navigate = useNavigate();


    //회원가입 요청 ! !
    const join = async (form) => {
        console.log(`@# Join.jsx join():`);
        console.log(`@# form:`, form);
        
        let response;
        let data;
        
        try {//response const로 안에서 한번더 선언해서 오류남 ;;
            // const response = await auth.join(form);
            response = await auth.join(form);
            console.log(`response:${response}`)
            
            
        } catch (error) {
            console.error(`${error}`);
            console.error(`회원가입 요청중 에러발생 🔴`);
            return;
        }
        
        data = response.data;
        const status = response.status;
        console.log(`@# data:`, data);
        console.log(`@# status:`, status);
        
        //분기처리 성공실패
        if (status == 200) {//크롬콘솔 + 알림창
            console.log(`회원 가입 성공!`);
            Swal.alert("회원 가입 성공","로그인이동","success",()=>{navigate("/login")});
        } else {
            console.log(`회원 가입 실패!`);
            Swal.alert("회원 가입 실패","회원 가입 실패하였습니다.","error");
        }



    }

    // <JoinForm join={join} /> join함수 join값으로 넘김
    return (
        <div>
            <Header />
            <div> 
                <JoinForm join={join} />
                <LoginContextConsumer />
            </div>
        </div>
    )
}

export default Join