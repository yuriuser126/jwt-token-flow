import React from 'react'
import Header from '../components/Header/Header'
import LoginContextConsumer from '../contexts/LoginContextConsumer'

const User = () => {
    return (
        <div>
            <Header />
            <div>
                <h1>User</h1>
                <hr />
                <h1>마이 페이지</h1>
                <LoginContextConsumer/>
            </div>
        </div>
    )
}

export default User