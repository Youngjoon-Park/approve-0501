# ✅ 11장 - 키오스크 프로젝트 도메인 배포 강의 (실제 문제 해결 중심)

> 📦 목적: React + Spring Boot 프로젝트를 **AWS Lightsail + 도메인(Nginx)** 으로 배포할 때 실제 발생한 문제를 순서대로 해결하고, **학생들도 그대로 따라할 수 있도록 실제 상황 중심으로** 기록한 실전형 README입니다.
> 🧑‍🏫 강사 팁 포함, Git Bash / MobaXterm / VSCode (PowerShell) 환경 모두 기준으로 설명합니다.

---

## 📌 상황 개요 (2025.04.30 기준)
- 리포지토리: `approve-0501`
- GitHub에는 kiosk-frontend / kiosk-backend가 잘 올라가 있음
- **문제는 서버에 dist 복사 경로가 Nginx root와 불일치했던 것**
- 그래서 실제 웹에서는 이전 화면이 계속 보이고, QR 결제 등도 실패
- 초기에 `localhost:5173`에서 개발하다가 → `192.168.x.x` 로 외부 테스트 시도 → 이후 **가비아 도메인 구매** → `AWS Lightsail` 인스턴스 생성 + 고정 IP + 도메인 연결 진행

---

## ✅ AWS Lightsail + 도메인 설정 절차

### 1단계: AWS Lightsail 인스턴스 생성
- 플랫폼: Ubuntu 22 LTS
- 포트 열기: 80, 443, 22, 8081 등
- 정적 IP 연결: Lightsail > 네트워킹 > 고정 IP 생성 > 인스턴스 연결

### 2단계: 가비아에서 도메인 구매
- 예: `kiosktest.shop`
- 가비아 DNS 설정으로 이동
- A 레코드 설정:
  - 호스트: `@`
  - 값: AWS Lightsail의 고정 IP 주소

### 3단계: 도메인 적용 확인
```bash
ping kiosktest.shop
```
> 응답이 AWS IP로 오면 연결 완료

---

## ✅ 카카오 결제 연동 요약

### 카카오 개발자 등록
- https://developers.kakao.com
- 카카오페이 CID: `TC0ONETIME`
- 관리자 키: 발급받은 키 복사 → `application.properties`에 설정

### 리다이렉트 URL 설정
- 승인 주소:
  ```
kakao.approve-url=http://kiosktest.shop/payment/success
kakao.cancel-url=http://kiosktest.shop/payment/cancel
kakao.fail-url=http://kiosktest.shop/payment/fail
```
- 카카오페이 설정 페이지에서도 동일한 리다이렉트 주소 등록 필요

---

## ✅ MobaXterm 세션 구성 방법 (3개)

### 1. MobaXterm 실행 후 "Session" 클릭
### 2. `SSH` 선택 → 다음과 같이 입력

| 항목 | 입력값 예시 |
|------|------------|
| Remote host | `3.38.xx.xxx` (서버 IP 주소) |
| Specify username | `ubuntu` |
| Use private key | `LightsailDefaultKey.pem` 경로 지정 |

### 3. 세션 3개로 복제 (우측에 있는 Clone 버튼 활용)
- 첫 번째 세션 이름: `backend-session` → 백엔드 jar 실행용
- 두 번째 세션 이름: `frontend-session` → React 정적 파일 확인 및 복사
- 세 번째 세션 이름: `mysql-session` → MySQL DB 확인 및 접속용

### 4. 각 세션에서 경로 이동
```bash
# backend-session
cd /home/ubuntu/kiosk-system

# frontend-session
cd /home/ubuntu/kiosk-frontend

# mysql-session
mysql -u kiosk_user -p
# 패스워드: 1234
```

---

## ✅ 전체 배포 순서 (25단계)

1. VSCode PowerShell: `cd kiosk-frontend`
2. `npm install`
3. `npm run build` → dist 생성
4. MobaXterm 접속: `sudo rm -rf /home/ubuntu/kiosk-frontend/*`
5. Git Bash: `scp -i 키.pem -r dist/* ubuntu@서버:/home/ubuntu/kiosk-frontend/`
6. 서버에서 권한 수정: `sudo chown -R ubuntu:ubuntu /home/ubuntu/kiosk-frontend`
7. React 파일 존재 확인: `ls /home/ubuntu/kiosk-frontend`
8. Nginx 설정 확인: `sudo nano /etc/nginx/sites-available/default`
9. root, index, proxy 설정 확인 후 저장
10. `sudo systemctl restart nginx`
11. 브라우저에서 `Ctrl + F5`
12. 백엔드 빌드: `./gradlew clean build`
13. Moba에서 jar 파일 드래그: `/home/ubuntu/kiosk-system`
14. 실행: `java -jar kiosk-backend-0.0.1-SNAPSHOT.jar`
15. 카카오 결제 요청 → QR 생성 확인
16. pg_token → /payment/success 응답 확인
17. 메뉴 정상 조회 확인 (/api/menus)
18. 주문 생성, 결제 흐름 성공

---

## 🧩 주요 실수와 원인 요약

| 증상 | 원인 | 해결 방법 |
|------|------|-------------|
| 이전 화면만 계속 나옴 | dist/* 파일을 다른 곳에 복사함 | `/home/ubuntu/kiosk-frontend`로 복사해야 함 |
| index.html 못 찾음 | Nginx root 경로와 복사 위치 불일치 | `nginx config`에서 root 정확히 설정 |
| 권한 오류 (Permission denied) | 서버 복사 후 퍼미션 설정 안 됨 | `sudo chown -R` 명령 실행 |
| QR 결제 실패 | 옛날 프론트가 보여지고 있음 | 빌드 후 복사, 강제 새로고침 필요 |
| 카카오 승인 오류 | CID 또는 리다이렉트 주소 오타 | properties + 카카오 설정 양쪽 점검 |

---

## ✅ 핵심 요약
- **React 정적 파일은 반드시 Nginx root 경로에 복사해야 한다**
- **도메인을 쓰려면 가비아 DNS → Lightsail IP 연결 필요**
- **카카오 결제 연동 시 approval_url, fail_url 모두 실제 도메인으로 등록해야 한다**
- **로컬 개발(`localhost:5173`) → `192.168.0.x` 실험 → 도메인 구매 → Lightsail 연결이라는 흐름이 실전 순서**

---

> 작성자: Youngjoon Park  
> 실전 기반 키오스크 도메인 배포 강의용 리드미 정리

