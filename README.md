인수인계 페이지 : https://rainy-snake-52d.notion.site/15c69520080c4fd09dabb8401ad94c1d?pvs=4
```sql

-- 1. Member 테이블에 데이터 삽입
INSERT INTO Member (name, email, role, auth_provider, provider_id, profile_image_url, created_at, modified_at)
VALUES 
('송하은', 'test1@test.test', '학생', 'local', 'local-001', 'https://example.com/chulsu.png', NOW(), NOW()),
('정지안', 'test2@test.test', '학생', 'local', 'local-002', 'https://example.com/younghee.png', NOW(), NOW());
-- 2. Problem 테이블에 운영체제 관련 데이터 삽입 (15개)
INSERT INTO Problem (question, answer, problem_type, created_at, modified_at)
VALUES 
-- 1번 문제
('프로세스 내에서 실행되는 가장 작은 실행 단위로, 동시에 여러 작업을 수행할 수 있다.', '스레드', 'SUBJECTIVE', NOW(), NOW()),
-- 2번 문제
('하나의 프로세스가 다른 프로세스에 의해 영향을 받지 않고 독립적으로 실행되기 위해 필요한 메모리 관리 기법은 무엇인가?', '가상 메모리', 'SUBJECTIVE', NOW(), NOW()),
-- 3번 문제
('CPU가 하나의 프로세스를 실행하다가 다른 프로세스로 전환될 때 발생하는 것을 무엇이라고 하는가?', '문맥 전환', 'SUBJECTIVE', NOW(), NOW()),
-- 4번 문제
('운영체제가 사용하는 파일 시스템의 기본 단위로, 데이터를 물리적으로 저장하는 최소 단위는 무엇인가?', '블록', 'SUBJECTIVE', NOW(), NOW()),
-- 5번 문제
('프로세스가 자원을 얻기 위해 무한정 대기 상태에 빠지는 현상을 무엇이라고 하는가?', '교착 상태', 'SUBJECTIVE', NOW(), NOW()),
-- 6번 문제
('운영체제의 핵심적인 역할로, CPU를 효율적으로 분배하여 여러 작업을 동시에 처리하는 기능은 무엇인가?', '스케줄링', 'SUBJECTIVE', NOW(), NOW()),
-- 7번 문제
('여러 프로세스가 동시에 접근할 때, 데이터의 무결성을 보장하기 위해 사용하는 기법은 무엇인가?', '임계 구역', 'SUBJECTIVE', NOW(), NOW()),
-- 8번 문제
('운영체제가 메모리를 효율적으로 관리하기 위해 페이지 단위로 나누는 기법은 무엇인가?', '페이징', 'SUBJECTIVE', NOW(), NOW()),
-- 9번 문제
('운영체제에서 프로세스 간 통신을 위해 사용되는 메커니즘은 무엇인가?', 'IPC (Inter-Process Communication)', 'SUBJECTIVE', NOW(), NOW()),
-- 10번 문제
('운영체제에서 메모리 할당을 동적으로 변경하고 메모리 파편화를 해결하는 기법은 무엇인가?', '힙 메모리 관리', 'SUBJECTIVE', NOW(), NOW()),
-- 11번 문제
('CPU가 유휴 상태에서 발생하지 않도록 효율적으로 사용하는 방법은 무엇인가?', '시간 분할', 'SUBJECTIVE', NOW(), NOW()),
-- 12번 문제
('운영체제가 프로그램을 실행하기 위해 메모리에 적재할 때, 필요한 부분만 로드하는 기법은 무엇인가?', '지연 로딩', 'SUBJECTIVE', NOW(), NOW()),
-- 13번 문제
('운영체제에서 시스템 호출을 통해 커널에 접근할 수 있는 인터페이스는 무엇인가?', '시스템 콜', 'SUBJECTIVE', NOW(), NOW()),
-- 14번 문제
('운영체제에서 디스크 I/O 성능을 최적화하기 위해 사용하는 스케줄링 알고리즘은 무엇인가?', '디스크 스케줄링', 'SUBJECTIVE', NOW(), NOW()),
-- 15번 문제
('여러 사용자가 하나의 컴퓨터를 동시에 사용하기 위해 필요한 운영체제의 기능은 무엇인가?', '다중 사용자 지원', 'SUBJECTIVE', NOW(), NOW());

```
