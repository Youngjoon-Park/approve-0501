import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

import LoginPage from '../pages/LoginPage';
import SignupPage from '../pages/SignupPage';
import AdminHomePage from '../pages/AdminHomePage';
import AdminMenuPage from '../pages/AdminMenuPage';
import AdminMenuAddPage from '../pages/AdminMenuAddPage';
import AdminMenuDetailPage from '../pages/AdminMenuDetailPage';
import AdminMenuEditPage from '../pages/AdminMenuEditPage';
import CartPage from '../pages/CartPage';
import PaymentPage from '../pages/PaymentPage'; // ✅ 결제 페이지
import PaymentSuccessPage from '../pages/PaymentSuccessPage'; // ✅ 결제 완료 페이지
import PaymentCancelPage from '../pages/PaymentCancelPage'; // ✅ 결제 취소 페이지 추가
import PaymentFailPage from '../pages/PaymentFailPage'; // ✅ 결제 실패 페이지 추가
import Header from '../components/Header';

function MainRouter({
  cartItems,
  //setCartItems,
  addToCart,
  updateQuantity,
  clearCart,
}) {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        {/* 기본 경로: 로그인 페이지로 이동 */}
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        {/* 관리자 페이지 */}
        <Route path="/admin" element={<AdminHomePage />} />
        <Route path="/admin/menus" element={<AdminMenuPage />} />
        <Route path="/admin/menus/new" element={<AdminMenuAddPage />} />
        <Route path="/admin/menus/:id" element={<AdminMenuDetailPage />} />
        <Route path="/admin/menus/edit/:id" element={<AdminMenuEditPage />} />
        {/* 사용자 장바구니 */}
        <Route
          path="/cart"
          element={
            <CartPage
              cartItems={cartItems}
              //setCartItems={setCartItems}
              addToCart={addToCart}
              updateQuantity={updateQuantity}
              clearCart={clearCart}
            />
          }
        />
        {/* 결제 페이지 흐름 */}
        <Route path="/payment/:orderId" element={<PaymentPage />} />
        <Route path="/payment/success" element={<PaymentSuccessPage />} />
        <Route path="/payment/cancel" element={<PaymentCancelPage />} />{' '}
        {/* ✅ 추가 */}
        <Route path="/payment/fail" element={<PaymentFailPage />} />{' '}
        {/* ✅ 추가 */}
      </Routes>
    </BrowserRouter>
  );
}

export default MainRouter;
