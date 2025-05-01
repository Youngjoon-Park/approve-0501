// 📁 src/api/axiosInstance.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://kiosktest.shop', // ✅ Nginx가 프록시해주므로 포트 제거
  withCredentials: true, // ✅ 쿠키 인증 포함
});

export default api;
