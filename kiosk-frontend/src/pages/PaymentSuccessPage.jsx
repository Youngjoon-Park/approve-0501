// 📁 src/pages/PaymentSuccessPage.jsx
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
// import api from '../api/axiosInstance'; // ✅ 실제 요청은 주석 처리

function PaymentSuccessPage() {
  const [searchParams] = useSearchParams();
  const [message, setMessage] = useState('결제 승인 중...');

  useEffect(() => {
    const pgToken = searchParams.get('pg_token');
    const orderId = searchParams.get('orderId'); // ✅ 쿼리에서 직접 받음

    if (!pgToken || !orderId) {
      setMessage('❌ 결제 정보가 없습니다. 다시 시도해주세요.');
      return;
    }

    const approvePayment = async () => {
      try {
        // ✅ 실제 서버 요청 주석 처리
        /*
        await api.post('/api/payment/approve', {
          pgToken,
          orderId,
        });
        */

        console.log(
          '✅ [가상] 결제 승인 성공 - orderId:',
          orderId,
          'pgToken:',
          pgToken
        );
        setMessage('✅ [가상] 결제가 완료되었습니다!');
      } catch (err) {
        console.error('❌ 결제 승인 실패:', err);
        setMessage('❌ 결제 승인에 실패했습니다. 관리자에게 문의하세요.');
      }
    };

    approvePayment();
  }, [searchParams]);

  return (
    <div className="p-4 text-center">
      <h2 className="text-2xl font-bold mb-4">{message}</h2>
      <p>이용해 주셔서 감사합니다.</p>
    </div>
  );
}

export default PaymentSuccessPage;
