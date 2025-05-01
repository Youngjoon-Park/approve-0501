// 📁 src/pages/AdminHomePage.jsx
import { Link } from 'react-router-dom';

function AdminHomePage() {
  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">✅ 관리자 홈입니다</h1>
      <Link
        to="/admin/menus"
        className="bg-blue-500 text-white px-4 py-2 rounded block mb-2"
      >
        🍽 메뉴 목록 보기
      </Link>
      <Link
        to="/admin/menus/new"
        className="bg-green-500 text-white px-4 py-2 rounded block"
      >
        ➕ 메뉴 추가하기
      </Link>
    </div>
  );
}

export default AdminHomePage;
