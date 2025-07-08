import React from 'react'
import Header from '../components/Header/Header'
import LoginContextConsumer from '../contexts/LoginContextConsumer'

const About = () => {
  return (
    <div>
        <Header/>
        <div>
            <h1>About</h1>
            <hr/>
            <h1>소개 페이지</h1>
            <LoginContextConsumer/>
        </div>
    </div>
  )
}

export default About