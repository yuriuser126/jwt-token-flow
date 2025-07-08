import React from 'react'
import axios from 'axios'

//axios 객체 생성
const api = axios.create();

//기본 url 생성
api.defaults.baseURL="/api"

export default api