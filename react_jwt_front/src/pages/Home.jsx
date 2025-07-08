import React from 'react'
import Header from '../components/Header/Header'
import LoginContextConsumer from '../contexts/LoginContextConsumer'

const Home = () => {
  return (
    <div>
        <Header/>
        <div>
            <h1>Home</h1>
            <hr/>
            <h1>메인페이지</h1>
            <LoginContextConsumer/>
        </div>
    </div>
  )
}

export default Home