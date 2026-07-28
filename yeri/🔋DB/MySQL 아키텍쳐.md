
참조 : 테코톡 https://www.youtube.com/watch?v=w27fZGbtvZ0


## MySQL 서버 흐름

MySQL 서버 : **MySQL 엔진 + 스토리지 엔진**
- MySQL Engine : 요청 처리 역할 (요청 쿼리에 대해 어떻게 실행할지 결정, 실행)
- Storage Engine : 필요한 데이터를 하드웨어에서 가져옴 (디스크와 통신하며 요청에 따라 데이터를 읽거나 가공)

![](../../assets/Pasted%20image%2020260728143723.png)


- 요청은 shell or API 로 들어옴


## MySQL 엔진 실행흐름
### 동작 1. 쿼리 파서
- SQL 문 입력 > 쿼리 파서 동작
- 요청 들어온 SQL문을 단어 단위의 토큰으로 분리 / 문법 오류 체크
- 파서 트리 형태로 구성
![](../../assets/Pasted%20image%2020260728144114.png)


### 동작 2. 전처리기
- 파서 트리의 논리적인 오류 체크(테이블이 존재하는지, 사용자 접근권한 등)

### 동작 3. 옵티마이저
- 최적화된 실행 계획 생성
- 인덱싱이 있다면 최적화 용이



## Storage 엔진 실행흐름

- MySQL은 여러 Storage 엔진을 지원한다 (디폴트는 InnoDB)