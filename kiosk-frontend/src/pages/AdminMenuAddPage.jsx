// 📁 src/pages/AdminMenuAddPage.jsx
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosInstance';

function AdminMenuAddPage() {
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [description, setDescription] = useState('');
  const [imageFile, setImageFile] = useState(null); // ✅ 이미지 상태 추가
  const navigate = useNavigate();

  const handleCreate = async () => {
    try {
      const token = localStorage.getItem('adminToken');

      const formData = new FormData(); // ✅ FormData 객체 사용
      formData.append('name', name);
      formData.append('price', parseInt(price));
      formData.append('description', description);
      if (imageFile) {
        formData.append('image', imageFile); // ✅ 이미지 파일 추가
      }

      await api.post('/api/admin/menus', formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          // 'Content-Type': 'multipart/form-data', // ❌ 삭제해야 함 (설정하지 말기)
        },
      });

      alert('✅ 메뉴가 등록되었습니다!');
      navigate('/admin/menus');
    } catch (err) {
      console.error(err);
      alert('❌ 메뉴 등록 실패');
    }
  };

  return (
    <div className="p-4 max-w-xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">🍽 메뉴 등록</h1>

      <input
        className="border p-2 w-full mb-2"
        placeholder="메뉴 이름"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <input
        className="border p-2 w-full mb-2"
        type="number"
        placeholder="가격"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
      />
      <textarea
        className="border p-2 w-full mb-2 h-40"
        placeholder="설명"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      {/* ✅ 파일 업로드 input 추가 */}
      <input
        type="file"
        onChange={(e) => setImageFile(e.target.files[0])}
        className="border p-2 w-full mb-4"
      />

      <button
        onClick={handleCreate}
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        등록하기
      </button>
    </div>
  );
}

export default AdminMenuAddPage;
