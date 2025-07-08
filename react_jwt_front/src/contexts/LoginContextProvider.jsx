import React, { createContext, useEffect, useState } from 'react'
import Login from '../pages/Login'
import api from '../apis/api';
import Cookies from 'js-cookie';
import * as auth from '../apis/auth' // auth파일 함수사용.(url)
import * as Swal from '../apis/alert' // alert Swal사용 알림 디자인


//export로 이름지정, createContext : 컨텍스트 생성(객체정보들어감)
export const LoginContext = createContext();

//children : Provider에 내려줄 자식 요소들
const LoginContextProvider = ({children}) => {
    console.log("@# LoginContextProvider()")

    /*------------------ State begin -------------------------*/
    
    //🔑1.상태함수 로그인 여부 
    //false :로그인전 기본값, isLogin : 로그인 여부
    const [isLogin,setLogin] = useState(false)
    
    //2.🧑사용자 정보(조회) / 사용자정보 초기에 없음 ({})
    const [userInfo,setUserInfo] = useState({});

    //4.💥권한 정보 / 초기값 isUser:false = ROLE_ADMIN,isAdmin:false = ROLE_USER
    const [roles,setRoles] = useState({isUser:false, isAdmin:false });

    /*------------------ State fin -------------------------*/

    /*
    6.로그인 체크
    쿠키에 jwt 있는지 확인 jwt로 사용자 정보요청
    */
    const loginCheck= async()=> {
        console.log("@# loginCheck");
        
        //➡️jwt 토큰 / 쿠키에서 가져옴
        const accessToken = Cookies.get("accessToken");
        console.log(`userData:${accessToken}`)
        

        if (!accessToken) {//쿠키 없을시
            console.log('쿠키에 jwt(accessToken) 없음');
            return;
        }

        //쿠키 있음
        //💣헤더 허가쪽 ->`Bearer`+jwt / accessToken이거 값이 jwt💣
        //💣header에 jwt담음💣
        api.defaults.headers.common.Authorization = `Bearer ${accessToken}`

        //🧑사용자 정보 요청
        let response;
        let data;

        try {//사용자 조회 -> 응답받음
            response = await auth.info();
            
        } catch (error) {
            console.log(`error:${error}`);
            console.log(`status:${response.status}`);
            return;
        }
        //응답 데이터 담아놓음
        data = response.data;
        console.log(`data:${data}`);
        
        //data가 401 실패 인증실패❌
        if (data == 'UNAUTHORIZED' || response.status ==401 ) {
            console.error("accessToken (jwt)가 만료되거나 실패하였습니다.");
            return;
            
        }
        
        //인증성공
        console.log("accessToken (jwt)가 사용자 인증정보 요청 성공.");

        //5번 로그인세팅 호출
        loginSetting(data,accessToken);


    }


    //🔓🔓로그인🔓🔓
    const login = async(username,password)=>{

        console.log(`username:${username}`)
        console.log(`password:${password}`)
        
        //호출
        try {
            //response로 받는다
            const response = await auth.login(username,password);
            const data = response.data;
            const status = response.status;
            const headers = response.headers;
            const authorization = headers.authorization;

            //🐻Bearer 공백으로 없앰 -> 순수토큰 생성(JWT)
            const accessToken = authorization.replace("Bearer ","");

            console.log(`login data:${data}`);
            console.dir(data);
            console.log(`login status:${status}`);
            console.log(`login headers:${headers}`);
            console.log(`login authorization:${authorization}`);
            console.log(`login accessToken:${accessToken}`);
            
        } catch (error) {//title,text,icon 사용 , 로그인 실패
            Swal.alert("로그인실패","아이디 또는 비밀번호 일치하지 않습니다.","error");
        }
    }


    //5.🧨로그인 세팅
    //userData=(사용자정보),accessToken=(jwt) .. +userData해도 됨
    const loginSetting=(userData,accessToken)=>{
        console.log("@# loginSetting")
        
        //백틱처리 로그
        console.log(`userData:${userData}`)
        console.log(`userData:${accessToken}`)
        
        //토큰 생성시 지정했던거 지정. 데이터 가져오기위해
        const [no,userId,authList] = userData;
        console.log(`userData:${no}`)
        console.log(`userData:${userId}`)
        console.log(`userData:${authList}`)
        
        //roleList : 데이터가오면 auth통해서 권한 리스트 가져오게끔
        const roleList = authList.map((auth)=>auth.auth)
        console.log(`userData:${roleList}`)
        
        //💣헤더 허가쪽 ->`Bearer`+jwt / accessToken이거 값이 jwt💣
        //💣header에 jwt담음💣
        api.defaults.headers.common.Authorization = `Bearer ${accessToken}`

        //🔓setLogin 로그인 여부 : true
        setLogin(true);

        //🧑유저정보 세팅 setUserInfo를 updateUserInfo 담음
        const updateUserInfo={no,userId,roleList};
        setUserInfo(updateUserInfo);
    

        //💥👮권한정보 세팅 setRoles를 updateRoles에 담음
        const updateRoles={isUser:false, isAdmin:false }

        roleList.forEach((role) => {
            if (role == 'ROLE_USER')updateRoles.isUser=true;
            if (role == 'ROLE_ADMIN')updateRoles.isAdmin=true;
        });

        setRoles(updateRoles);

    }


    //3.🔓로그아웃세팅
    const logout =()=>{
        console.log("@# logout()")

        //🔒❌로그인여부 false 
        setLogin(false);

        //🧑❌유저정보 초기화
        setUserInfo(null);

        //👮❌권한정보 초기화
        setRoles(null);

        //🍪❌쿠키 초기화
        Cookies.remove("accessToken");

        //📲❌api 헤더 초기화
        api.defaults.headers.common.Authorization=undefined;



    }

    //2.기본 함수 / 시작하자마자 / 3초뒤 로직 자동로그인 / setLogin(true) : 로그인 
    //초기시작실행, 상태값 변경
    // useEffect(() => {
    //     setTimeout(() => {
    //         setLogin(true)
    //     }, 3000);
    // },[]); -> 이거근데 작동안함 ;;;;

  return (
    <div>
        {/* setLogin , logout : 함수의 값을 내려줌 (like 전역변수)*/}
        <LoginContext.Provider value={{isLogin, logout}}>
            {children}
        </LoginContext.Provider>
    </div>
  )
}

export default LoginContextProvider