import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axiosInstance';

function AdminMenuEditPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [description, setDescription] = useState('');
  const [imageFile, setImageFile] = useState(null);
  const [imageFilename, setImageFilename] = useState(''); // ✅ 기존 이미지 파일명 상태

  // ✅ 메뉴 데이터 불러오기
  useEffect(() => {
    const fetchMenu = async () => {
      try {
        const token = localStorage.getItem('adminToken');
        const res = await api.get(`/api/admin/menus/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setName(res.data.name);
        setPrice(res.data.price);
        setDescription(res.data.description);
        setImageFilename(res.data.imageFilename); // ✅ 받아온 이미지 파일명 저장
      } catch (err) {
        console.error('❌ 메뉴 불러오기 실패:', err);
        alert('❌ 메뉴 불러오기 실패');
      }
    };
    fetchMenu();
  }, [id]);

  // ✅ 수정 요청 함수
  const handleUpdate = async () => {
    try {
      const token = localStorage.getItem('adminToken');

      const formData = new FormData();
      formData.append('name', name);
      formData.append('price', price);
      formData.append('description', description);
      if (imageFile) {
        formData.append('image', imageFile);
      }

      await api.put(`/api/admin/menus/${id}`, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      alert('✅ 메뉴 수정 완료');
      navigate('/admin/menus');
    } catch (err) {
      console.error('❌ 메뉴 수정 실패:', err);
      alert('❌ 메뉴 수정 실패');
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">🍽 메뉴 수정</h1>

      {/* ✅ 기존 이미지 출력 */}
      {imageFilename && (
        <img
          src={`http://kiosktest.shop/uploads/${imageFilename}`} // ✅ 수정된 이미지 경로
          alt="메뉴 이미지"
          className="w-32 h-32 object-cover mb-4"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src = 'http://kiosktest.shop/uploads/duke.png'; // ✅ duke.png로 대체
          }}
        />
      )}

      {/* ✅ 메뉴 이름 입력 */}
      <input
        className="border p-2 w-full mb-2"
        placeholder="메뉴 이름"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      {/* ✅ 가격 입력 */}
      <input
        className="border p-2 w-full mb-2"
        placeholder="가격"
        type="number"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
      />

      {/* ✅ 설명 입력 */}
      <textarea
        className="border p-2 w-full mb-2"
        placeholder="설명"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      {/* ✅ 파일 업로드 입력 */}
      <input
        type="file"
        onChange={(e) => setImageFile(e.target.files[0])}
        className="border p-2 w-full mb-4"
      />

      {/* ✅ 수정 버튼 */}
      <button
        onClick={handleUpdate}
        className="bg-green-500 text-white px-4 py-2 rounded"
      >
        수정
      </button>
    </div>
  );
}

export default AdminMenuEditPage;
