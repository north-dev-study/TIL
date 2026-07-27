참조 : 테코톡 https://www.youtube.com/watch?v=HIGc93pqTAc&list=PLgXGHBqgT2TvpJ_p9L_yZKPifgdBOzdVH&index=53


Request가 동시다발적으로 서버에 들어왔을때, 서버가 처리하는 방법은?
순차 처리로는 x, 병렬처리로 하기 위해 각 요청마다 스레드를 할당

## Thread
- 프로세스 안에서 실행되는 작업 흐름의 단위
- 하나의 프로세스 안에서 다른 스레드와 메모리 공간 공유
- CPU 코어 하나는 한 번에 하나의 스레드만 실행 / CPU 코어를 여러 스레드가 사용하기 위해 **컨텍스트 스위칭** 과정이 필요함

![409](../../assets/Pasted%20image%2020260727173742.png)



