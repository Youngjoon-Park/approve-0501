import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axiosInstance';

function ArticleListPage() {
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    api
      .get('/api/articles', { withCredentials: true })
      .then((res) => setArticles(res.data))
      .catch((err) => {
        console.error('❌ 글 목록 불러오기 실패:', err);
        if (err.response?.status === 401 || err.response?.status === 403) {
          window.location.href = '/login';
        }
      });
  }, []);

  return (
    <div className="p-4 max-w-2xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">📝 글 목록</h1>

      {/* ✅ 글 작성 버튼 추가 */}
      <div className="flex justify-end mb-4">
        <Link to="/articles/new">
          <button className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
            글 작성
          </button>
        </Link>
      </div>

      {articles.map((article) => (
        <Link key={article.id} to={`/articles/${article.id}`}>
          <div className="border-b py-3 hover:bg-gray-100 cursor-pointer">
            <h2 className="font-semibold">{article.title}</h2>
            <p className="text-gray-600 text-sm">{article.content}</p>
          </div>
        </Link>
      ))}
    </div>
  );
}

export default ArticleListPage;
