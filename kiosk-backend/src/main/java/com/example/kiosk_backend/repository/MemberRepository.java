package com.example.kiosk_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kiosk_backend.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
// DB에서 데이터를 가져오는 퍼시스턴스 계층 역활을 하게된다
// 기본적인 CRUD(Create, Read, Update, Delete) 작업을 지원하는 인터페이스입니다.
// save(): 엔티티를 저장하거나 수정합니다.
// findById(): 주어진 ID로 엔티티를 찾습니다.
// findAll(): 모든 엔티티를 조회합니다.
// deleteById(): 주어진 ID로 엔티티를 삭제합니다.

// 컨트롤로 프리젠테이션 계층 HTTP 요청을 받아 비즈니스 계층으로 전송

// 서비스 비즈니스 계층 비즈니스 로직을 처리(통상적인 앱 기능)

// 리포지토리 퍼시스턴스 계층 <--> 데이터베이스
// 리포지토리 : 퍼시스턴스 계층을 실제 구현한 것
// main
// 실제 코드를 작성하는 공간
// test
// 프로젝트 테스트 코드를 작성하는 공간
// build.gradle
// 빌 드 설정 파일
// settings.gradle
// 빌드할 프로젝트의 정보 설정 파일
// 스프링과 스프링부트 차이점
// 스프링은 개발에 필요한 환경을 수동 구성, 스프링 부트는 자동 구성
// 내장 WAS의 유무 : 스프링 부트는 처음부터 WAS를 가지고 있음(tomcat)
// 스프링 컨테이너
// 스프링 안에서 동작하는 빈 생성 및 관리 주체
// 빈
// 스프링 컨테이너가 생성하고 관리하는 객체
// @RestController : 클래스가 라우터 역할을 할 수 있게 해주는 애너테이션
// @GetMapping(/test) : /test GET 요청을 처리
// @RestController의 근본은 @Component 애너테이션
// @Component 어노테이션은 Spring 프레임워크에서 Bean으로 등록할 클래스를
// 표시하는 데 사용됩니다.
// @Component 어노테이션은 @Repository, @Service, @Controller 등의
// 다른 스프링 어노테이션들도 내부적으로 포함하고 있습니다
