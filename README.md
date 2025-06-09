# ForLong-backend

## 프로젝트 개요

희귀 동물(파충류, 소동물 등)의 정보와 의료서비스는 구하기 어렵다는 문제를 해결하기 위해, ForLong-backend 프로젝트는 희귀 동물 보호자들이 관련 병원 정보를 쉽게 찾고 예약할 수 있도록 지원하는 API 서버입니다.  
Spring Boot와 Spring Security로 개발했으며, AWS EC2 서버에 배포하여 운영합니다.

- **주요 기술 스택:**  
  - Spring Boot  
  - Spring Security  
  - AWS EC2  
  - MySQL  
  - GitHub, Slack, Git flow 전략

---

## 멤버 구성 및 역할

| 이름      | 역할 및 담당 기능 |
|-----------|------------------|
| **이현우** | - 소셜 로그인, 기본 로그인, 회원가입, 회원정보 수정, 회원 탈퇴 등 **회원 관련 API** 개발<br>- 게시물 수정, 삭제, 조회, 페이징 등 **게시물 관련 API** 개발<br>- AWS EC2 서버를 활용한 **서버 배포** 담당 |
| **박세현** | - **AWS**를 활용한 **DB 서버 관리**<br>- 병원 필터링, 병원 상세 정보 검색, 현재 위치 기반 병원 위치 찾기 API 개발 |
| **김현호** | - 예약별 상태 조회, 날짜에 따른 예약 가능 시간 조회, 예약 가능한 날짜 조회 등 **예약 관련 API** 개발 |

---

## 협업 및 개발 전략

- **협업 도구:** GitHub, Slack  
- **브랜치 전략:** Git flow를 적용하여 효율적으로 협업

---

## 프로젝트 목적

- 주류 애완동물(강아지, 고양이 등) 외의 희귀 동물 보호자들이 신뢰할 수 있는 의료 정보를 제공받을 수 있도록 지원
- 병원 정보, 예약, 게시물 등 다양한 기능 제공을 통해 사용자 경험 향상

---

## 기술 스택

- **Backend:** Spring Boot, Spring Security
- **Infrastructure:** AWS EC2, MySQL
- **Collaboration:** GitHub, Slack
- **Branching:** Git flow

---
