// 📁 src/pages/AdminMenuPage.jsx
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosInstance';

function AdminMenuPage() {
  const [menus, setMenus] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState('');
  const navigate = useNavigate();

  // ✅ 메뉴 목록 불러오기 함수
  const fetchMenus = async () => {
    try {
      const token = localStorage.getItem('adminToken');
      const url = searchKeyword
        ? `/api/admin/menus?search=${encodeURIComponent(searchKeyword)}`
        : '/api/admin/menus';

      const res = await api.get(url, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMenus(res.data);
    } catch (err) {
      console.error(err);
      alert('❌ 메뉴 조회 실패');
    }
  };

  // ✅ 화면 진입 또는 검색어 바뀔 때 메뉴 불러오기
  useEffect(() => {
    fetchMenus();
  }, [searchKeyword]);

  // ✅ 메뉴 삭제 함수
  const handleDelete = async (id) => {
    const confirmDelete = window.confirm('정말 삭제하시겠습니까?');
    if (!confirmDelete) return;

    try {
      const token = localStorage.getItem('adminToken');
      await api.delete(`/api/admin/menus/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      alert('✅ 삭제 완료');
      fetchMenus(); // 삭제 후 목록 다시 불러오기
    } catch (err) {
      console.error(err);
      alert('❌ 삭제 실패');
    }
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">🍽 메뉴 목록</h2>

      {/* ✅ 검색창 */}
      <input
        type="text"
        value={searchKeyword}
        onChange={(e) => setSearchKeyword(e.target.value)}
        placeholder="메뉴 이름 검색"
        className="border p-2 mb-4 w-full"
      />

      <ul className="space-y-4">
        {menus.map((menu) => (
          <li
            key={menu.id}
            className="border p-4 rounded shadow flex justify-between items-center"
          >
            <div>
              {/* ✅ 이미지 출력 */}
              {menu.imageFilename && (
                <img
                  src={`http://kiosktest.shop/uploads/${menu.imageFilename}`} // ✅ 도메인 수정
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src = 'http://kiosktest.shop/uploads/duke.png'; // ✅ duke.png 대체
                  }}
                  alt="메뉴 이미지"
                  className="w-32 h-32 object-cover mb-2"
                />
              )}

              {/* ✅ 이름, 가격, 설명 */}
              <p className="font-semibold">📌 이름: {menu.name}</p>
              <p className="text-sm text-gray-600">💰 가격: {menu.price}원</p>
              <p className="text-sm text-gray-600">
                📝 설명: {menu.description}
              </p>
            </div>

            {/* ✅ 수정/삭제 버튼 */}
            <div className="flex gap-2">
              <button
                onClick={() => navigate(`/admin/menus/edit/${menu.id}`)}
                className="bg-yellow-400 text-white px-3 py-1 rounded hover:bg-yellow-500"
              >
                수정
              </button>
              <button
                onClick={() => handleDelete(menu.id)}
                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
              >
                삭제
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default AdminMenuPage;
