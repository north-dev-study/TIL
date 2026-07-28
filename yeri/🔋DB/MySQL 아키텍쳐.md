
참조 : 테코톡 https://www.youtube.com/watch?v=w27fZGbtvZ0


## MySQL 서버 흐름

MySQL 서버 : **MySQL 엔진 + 스토리지 엔진**
- MySQL Engine : 요청 처리 역할
- Storage Engine : 필요한 데이터를 하드웨어에서 가져옴

![](../../assets/Pasted%20image%2020260728143723.png)


- 요청은 shell or API 로 들어옴


### 동작 1. 쿼리 파서
- SQL 문 입력 > 쿼리 파서 동작
- 요청 들어온 SQL문을 단어 단위의 토큰으로 분리 / 문법 오류 체크
- 파서 트리 형태로 구성
![](../../assets/Pasted%20image%2020260728144114.png)