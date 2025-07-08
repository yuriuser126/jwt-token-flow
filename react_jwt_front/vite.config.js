import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server:{
    proxy:{
      '/api':{
        target:'http://localhost:8585'//(port)서버주소
        ,changeOrigin:true // 요청헤더의 Host 도 변경
        ,secure:false // https 사용 x
        ,rewrite:(path)=>path.replace(/\/api/,'') //api.js로 가는 형식
      }
    }
  }
})
