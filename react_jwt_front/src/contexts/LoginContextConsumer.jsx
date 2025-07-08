import React, { Children, useContext } from 'react'
// LoginContextProvider 에서 export한 LoginContext 이름을 사용
import { LoginContext } from './LoginContextProvider';



const LoginContextConsumer = () => {
    // isLogin으로 useContext할건데 LoginContext서 가져온다/ 참:로그인 x:로그아웃
    // useContext :컨텍스트를 쉽게 사용하는 훅
    const {isLogin} = useContext(LoginContext);
  return (
    <div>
        <h3>로그인 여부:{isLogin ? "로그인" : "로그아웃"}</h3>

    </div>
  )
}

export default LoginContextConsumer