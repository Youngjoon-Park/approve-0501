// 📁 src/components/Header.jsx
import { useNavigate, Link } from 'react-router-dom';

function Header() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('adminToken'); // ✅ 토큰 삭제
    alert('로그아웃 되었습니다');
    navigate('/login'); // 로그인 페이지로 이동
  };

  return (
    <header className="p-4 bg-gray-200 flex justify-between items-center">
      <h1 className="text-lg font-bold">Kiosk System</h1>
      <div className="flex gap-4">
        {/* ✅ 여기 추가 */}
        <Link to="/cart" className="bg-green-500 text-white px-4 py-2 rounded">
          장바구니
        </Link>
        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-4 py-2 rounded"
        >
          로그아웃
        </button>
      </div>
    </header>
  );
}

export default Header;
