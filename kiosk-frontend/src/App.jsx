import React, { useState } from 'react';
import MainRouter from './routes/MainRouter';

function App() {
  const [cartItems, setCartItems] = useState([]);

  // ✅ 메뉴 추가
  const addToCart = (menu) => {
    const existingItem = cartItems.find((item) => item.id === menu.id);

    if (existingItem) {
      setCartItems((prev) =>
        prev.map((item) =>
          item.id === menu.id ? { ...item, quantity: item.quantity + 1 } : item
        )
      );
    } else {
      const newItem = {
        id: menu.id, // ✅ DB ID 그대로 사용
        name: menu.name,
        price: menu.price,
        quantity: 1,
      };
      setCartItems((prev) => [...prev, newItem]);
    }
  };

  // ✅ 수량 업데이트
  const updateQuantity = (menuId, amount) => {
    setCartItems((prev) =>
      prev
        .map((item) =>
          item.id === menuId
            ? { ...item, quantity: item.quantity + amount }
            : item
        )
        .filter((item) => item.quantity > 0)
    );
  };

  // ✅ 장바구니 초기화
  const clearCart = () => {
    setCartItems([]);
  };

  return (
    <MainRouter
      cartItems={cartItems}
      addToCart={addToCart}
      updateQuantity={updateQuantity}
      clearCart={clearCart}
    />
  );
}

export default App;
