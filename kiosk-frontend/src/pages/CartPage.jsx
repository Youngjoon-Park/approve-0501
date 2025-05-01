// 📁 src/pages/CartPage.jsx
import React, { useEffect } from 'react';
import api from '../api/axiosInstance';

function CartPage({ cartItems, addToCart, clearCart }) {
  // ✅ 하드코딩된 메뉴 3개
  const menus = [
    { id: 1, name: '아메리카노', price: 3000 },
    { id: 2, name: '까페라떼', price: 8000 },
    { id: 3, name: '카푸치노', price: 8500 },
  ];

  useEffect(() => {
    console.log('🧪 현재 cartItems 상태:', cartItems);
  }, [cartItems]);

  const calculatedTotalPrice = cartItems.reduce(
    (sum, item) =>
      sum + (Number(item.price) || 0) * (Number(item.quantity) || 0),
    0
  );

  const handleCheckout = async () => {
    if (!calculatedTotalPrice || calculatedTotalPrice <= 0) {
      alert('총 금액이 없습니다. 메뉴를 추가하세요.');
      return;
    }

    const orderItems = cartItems.map((item) => ({
      menuId: item.id, // ✅ 하드코딩된 메뉴의 ID 사용
      quantity: item.quantity,
    }));

    if (orderItems.length === 0) {
      alert('❌ 유효한 메뉴가 없습니다.');
      return;
    }

    console.log('🧾 주문 요청 데이터:', orderItems);

    try {
      const token = localStorage.getItem('adminToken');

      // ✅ 주문 생성
      const orderRes = await api.post(
        '/api/orders',
        { items: orderItems },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        }
      );

      const { orderId } = orderRes.data;
      sessionStorage.setItem('orderId', orderId);

      // ✅ 결제 준비
      const paymentRes = await api.post(
        '/api/payment/ready',
        {
          orderId,
          totalAmount: calculatedTotalPrice,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        }
      );

      const mobileUrl = paymentRes.data.next_redirect_mobile_url;
      if (mobileUrl) {
        window.location.href = mobileUrl;
      } else {
        alert('❌ 결제 URL 생성 실패');
      }
    } catch (err) {
      console.error('❌ 주문 또는 결제 실패:', err);
      alert('❌ 주문 또는 결제 실패했습니다.');
    }
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">🛒 장바구니</h2>

      <div className="space-y-2 mb-6">
        {menus.map((menu) => (
          <button
            key={menu.id}
            onClick={() =>
              addToCart({
                id: menu.id,
                name: menu.name,
                price: menu.price,
                quantity: 1,
              })
            }
            className="block bg-purple-500 text-white px-4 py-2 rounded w-full"
          >
            ➕ {menu.name} ({menu.price.toLocaleString()}원) 추가
          </button>
        ))}
      </div>

      {cartItems.length === 0 ? (
        <p className="text-gray-500">장바구니가 비어 있습니다.</p>
      ) : (
        <ul className="space-y-4">
          {cartItems.map((item) => (
            <li key={item.id} className="border p-4 rounded shadow">
              <div className="flex justify-between">
                <div>
                  <p className="font-semibold">{item.name}</p>
                  <p className="text-sm text-gray-600">
                    {item.price.toLocaleString()}원 × {item.quantity}개
                  </p>
                </div>
              </div>
            </li>
          ))}
        </ul>
      )}

      <div className="mt-6">
        <h3 className="text-lg font-bold">
          총 금액: {calculatedTotalPrice.toLocaleString()}원
        </h3>
        <div className="flex gap-2 mt-4">
          <button
            onClick={handleCheckout}
            className="bg-blue-600 text-white px-4 py-2 rounded w-full"
          >
            💳 주문하기
          </button>
          <button
            onClick={clearCart}
            className="bg-gray-500 text-white px-4 py-2 rounded w-full"
          >
            ❌ 비우기
          </button>
        </div>
      </div>
    </div>
  );
}

export default CartPage;
