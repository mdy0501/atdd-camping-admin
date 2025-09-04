# 🏕️ 초록 캠핑장 관리자 시스템

## 📖 프로젝트 소개
초록 캠핑장 체인의 관리자를 위한 통합 관리 시스템입니다. 예약 관리, 사이트 관리, 매출 통계 등의 기능을 제공합니다.

> ⚠️ **주의**: 이 프로젝트는 ATDD 학습을 위한 레거시 시스템 예제입니다. 의도적으로 복잡한 구조와 중복 코드가 포함되어 있습니다.

## 🚀 시작하기

### 필수 요구사항
- Java 17 이상
- Gradle 7.0 이상

### 실행 방법
```bash
# 프로젝트 클론
git clone [repository-url]
cd camping-admin-system

# 빌드
./gradlew build

# 실행
./gradlew bootRun
```

### 접속 정보
- **애플리케이션**: http://localhost:8081
- **H2 콘솔**: http://localhost:8081/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (빈 값)

## 👤 테스트 계정

| 역할 | 이메일 | 비밀번호 | 권한 |
|------|--------|----------|------|
| 슈퍼 관리자 | admin@camping.com | admin123 | 모든 캠핑장 관리 |
| 캠핑장 매니저 | manager1@camping.com | admin123 | 특정 캠핑장 관리 |
| 직원 | staff1@camping.com | admin123 | 예약 승인/반려 |
| 회계사 | accountant@camping.com | admin123 | 통계 조회 |

## 🏗️ 시스템 구조

### 주요 기능
1. **인증 관리**
   - JWT 기반 인증
   - 역할 기반 접근 제어 (RBAC)

2. **예약 관리**
   - 예약 목록 조회 및 필터링
   - 예약 승인/반려 처리
   - 대량 예약 일괄 처리

3. **사이트 관리**
   - 캠핑 사이트 CRUD
   - 사이트 상태 관리
   - 시설 정보 관리

4. **매출 통계**
   - 일별/월별 매출 집계
   - 사이트별 매출 분석
   - 성수기/비수기 비교

### 기술 스택
- **Backend**: Spring Boot 3.2.0, Spring Security, JWT
- **Database**: H2 (In-Memory)
- **Frontend**: Thymeleaf, JavaScript
- **Build**: Gradle

## 📝 API 엔드포인트

### 인증
- `POST /api/auth/login` - 로그인
- `GET /api/auth/validate` - 토큰 검증
- `POST /api/auth/logout` - 로그아웃

### 예약 관리
- `GET /api/admin/reservations` - 예약 목록 조회
- `PUT /api/admin/reservations/{id}/approve` - 예약 승인
- `PUT /api/admin/reservations/{id}/reject` - 예약 반려
- `POST /api/admin/reservations/bulk-approve` - 대량 승인

### 사이트 관리
- `GET /api/admin/sites` - 사이트 목록 조회
- `POST /api/admin/sites` - 사이트 추가
- `PUT /api/admin/sites/{id}` - 사이트 수정
- `DELETE /api/admin/sites/{id}` - 사이트 삭제

### 통계
- `GET /api/admin/statistics/daily` - 일별 매출
- `GET /api/admin/statistics/monthly` - 월별 매출
- `GET /api/admin/statistics/site-ranking` - 사이트별 매출 순위

## 🧪 테스트 작성 가이드

### Cucumber 테스트 추가 방법

1. Feature 파일 작성 (`src/test/resources/features/`)
```gherkin
Feature: 관리자 인증
  Scenario: 올바른 자격증명으로 로그인
    Given 등록된 관리자 계정이 존재함
    When 올바른 자격증명으로 로그인을 시도하면
    Then JWT 토큰이 발급됨
```

2. Step Definition 구현 (`src/test/java/com/camping/admin/steps/`)
```java
@Given("등록된 관리자 계정이 존재함")
public void 등록된_관리자_계정이_존재함() {
    // 테스트 데이터 설정
}
```

### 테스트 실행
```bash
# 전체 테스트 실행
./gradlew test

# Cucumber 테스트만 실행
./gradlew test --tests CucumberTestRunner
```

## 🐛 알려진 이슈

### 의도적인 레거시 패턴
- **중복 코드**: 컨트롤러와 서비스 레이어에 의도적인 중복
- **복잡한 비즈니스 로직**: ReservationService의 스파게티 코드
- **비효율적인 쿼리**: N+1 문제가 있는 통계 조회
- **수동 매핑**: DTO-Entity 간 수동 변환

### 개선 대상
- [ ] 테스트 커버리지 향상
- [ ] Step Definition 재사용성 개선
- [ ] Page Object 패턴 적용
- [ ] 테스트 데이터 관리 전략

## 📚 학습 자료
- [Week 2 미션 가이드](../week2/temp/mission-overview.md)
- [Step 1: Cucumber 기본](../week2/temp/step1.md)
- [Step 2: AI 활용 테스트 확장](../week2/temp/step2.md)
- [Step 3: 엔터프라이즈 테스트](../week2/temp/step3.md)

## 📄 라이선스
교육 목적으로만 사용 가능합니다.