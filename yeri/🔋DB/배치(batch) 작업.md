> 대량의 데이터를 처리하거나 작업을 자동화하기 위해 여러 개의 작업을 그룹화하여 실행하는 것. 주로 대량 데이터 입력, 업데이트 또는 삭제에서 사용됨


- I/O 부하 _ SQL문 최적화(알고리즘) 체크
- 트랜잭션 크기를 적절히 설정해 메모리 오버헤드 방지


### 실행 방법
```
프로시져 생성 > DBMS_JOB or DBMS_SCHEDULER 로 실행
```


JOB select 쿼리
```
SELECT *
  FROM USER_JOBS / USER_SCHEDULER_JOBS
 ORDER BY JOB;
```

JOB 관련 주요 컬럼
```
JOB          -- 배치 번호
WHAT         -- 실행하는 프로시저 또는 SQL
LAST_DATE    -- 마지막 실행 시간
NEXT_DATE    -- 다음 실행 예정 시간
INTERVAL     -- 반복 주기
FAILURES     -- 연속 실패 횟수
BROKEN       -- 실행 중지 여부
```


