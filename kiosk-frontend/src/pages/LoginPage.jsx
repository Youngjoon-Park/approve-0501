import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosInstance';

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault(); // ✅ form 기본 제출 방지

    try {
      const response = await api.post(
        '/api/admin/login', // ✅ 관리자 전용 JWT 로그인
        {
          email: email,
          password: password,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      const token = response.data.token;
      localStorage.setItem('adminToken', token); // ✅ 토큰 저장

      setErrorMsg('');
      alert('✅ 관리자 로그인 성공!');
      navigate('/admin'); // 👉 관리자 페이지로 이동 (원하시면 경로 수정 가능)
    } catch (err) {
      if (err.response?.status === 401) {
        setErrorMsg('❌ 관리자 이메일 또는 비밀번호가 틀렸습니다.');
      } else {
        setErrorMsg('❌ 서버 오류가 발생했습니다.');
      }
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100">
      <h1 className="text-3xl font-bold mb-4">관리자 로그인</h1>

      {errorMsg && <div className="text-red-500 mb-4">{errorMsg}</div>}

      {/* ✅ form 구조로 감싸기 */}
      <form onSubmit={handleLogin} className="flex flex-col items-center">
        <input
          type="email" // ✅ 수정
          placeholder="이메일"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="border p-2 mb-2 w-64"
          autoComplete="username" // ✅ 자동완성 추가
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="border p-2 mb-4 w-64"
          autoComplete="current-password" // ✅ 자동완성 추가
        />
        <button
          type="submit"
          className="bg-blue-500 text-white px-4 py-2 rounded"
        >
          로그인
        </button>
      </form>
    </div>
  );
}
// 항목	    수정 전	                                수정 후
// 요청 URL	/login	                                 /api/admin/login
// 전송 방식	qs.stringify + x-www-form-urlencoded	  JSON ({ email, password })
// 토큰 저장	없음	                                  localStorage.setItem('adminToken', ...)
// form 구조	없음	                                  form으로 감싸고 e.preventDefault() 처리

export default LoginPage;
