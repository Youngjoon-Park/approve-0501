// 📁 src/pages/AdminMenuDetailPage.jsx
import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axiosInstance';

function AdminMenuDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [menu, setMenu] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('adminToken');
    api
      .get(`/api/admin/menus/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => setMenu(res.data))
      .catch((err) => {
        console.error(err);
        alert('❌ 메뉴 불러오기 실패');
      });
  }, [id]);

  const handleDelete = async () => {
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
      navigate('/admin/menus');
    } catch (err) {
      console.error(err);
      alert('❌ 삭제 실패');
    }
  };

  if (!menu) return <div>로딩 중...</div>;

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-2">🍽 {menu.name}</h1>
      <p className="mb-1">💰 가격: {menu.price}원</p>
      <p className="mb-4">📝 설명: {menu.description}</p>
      <button
        onClick={() => navigate(`/admin/menus/edit/${id}`)}
        className="bg-yellow-400 text-white px-4 py-2 rounded mr-2"
      >
        수정
      </button>
      <button
        onClick={handleDelete}
        className="bg-red-500 text-white px-4 py-2 rounded"
      >
        삭제
      </button>
    </div>
  );
}

export default AdminMenuDetailPage;
