import React from 'react';

function PaymentFailPage() {
  return (
    <div className="p-4 text-center">
      <h2 className="text-2xl font-bold mb-4">❌ 결제에 실패했습니다.</h2>
      <p>잠시 후 다시 시도해 주세요.</p>
    </div>
  );
}

export default PaymentFailPage;
