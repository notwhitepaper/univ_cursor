# Univ Cursor 학사 시스템

Spring Boot 기반으로 구현한 학사 관리 백엔드입니다. 세션 인증을 기반으로 학생과 관리자의 핵심 흐름(공지 조회, 강의 조회, 장바구니, 본 수강신청, 이월, 강의/강의실/수강신청 기간 관리)을 제공합니다.

## 기술 스택
- Java 17
- Spring Boot 3
- Spring Data JPA (H2 메모리 데이터베이스)
- Spring Security (세션 로그인)
- Springdoc OpenAPI (Swagger UI)

## 실행 방법
```bash
# Gradle Wrapper JAR는 저장소에 포함되지 않으므로 최초 1회 로컬 Gradle로 래퍼를 재생성합니다.
gradle wrapper

# 이후부터는 래퍼를 통해 실행할 수 있습니다.
./gradlew bootRun
```
> 네트워크 제한 환경에서는 Gradle Wrapper 다운로드가 실패할 수 있습니다. 이 경우 로컬에 설치된 Gradle 8.x로 `gradle bootRun`을 실행하거나, 필요한 의존성을 사전 캐싱해 주세요.

애플리케이션 실행 후 `http://localhost:8080/swagger-ui`에서 API 문서를 확인할 수 있습니다.

## 기본 계정 (data.sql 시드)
| 역할 | 아이디 | 비밀번호 |
| --- | --- | --- |
| 학생 | `student1` | `password` |
| 관리자 | `admin1` | `password` |

## 주요 기능 요약
- **공지/학사일정**: 페이징/기간 필터로 조회 (읽기 전용)
- **강의 조회**: 검색 및 정렬 지원, 시간표 정보 포함
- **장바구니**: 학점(20학점) 및 시간표 중복 검증
- **본 수강신청**: 수강기간 검증, 정원/학점/시간표 제약, 장바구니와 연동
- **이월 처리**: 관리자 트리거 API(`/admin/registration/rollover`)
- **관리자 기능**: 강의실/강의 CRUD, 수강신청 기간 설정

## 환경 변수
특별한 외부 연동은 없으며, 필요 시 `application.yml`을 수정해 데이터베이스나 로그 레벨을 조정할 수 있습니다.
