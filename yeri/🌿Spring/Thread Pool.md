참조 : 테코톡 https://www.youtube.com/watch?v=HIGc93pqTAc&list=PLgXGHBqgT2TvpJ_p9L_yZKPifgdBOzdVH&index=53


Request가 동시다발적으로 서버에 들어왔을때, 서버가 처리하는 방법은?
순차 처리로는 x, 병렬처리로 하기 위해 각 요청마다 스레드를 할당

## Thread
- 프로세스 안에서 실행되는 작업 흐름의 단위
- 하나의 프로세스 안에서 다른 스레드와 메모리 공간 공유
- CPU 코어 하나는 한 번에 하나의 스레드만 실행 / CPU 코어를 여러 스레드가 사용하기 위해 **컨텍스트 스위칭** 과정이 필요함

![409](../../assets/Pasted%20image%2020260727173742.png)



Java는 1:1 스레딩 모델로 Thread를 생성
유저 Thread 하나당 OS Thread 하나가 대응됨(JVM이 요청)
새로운 Thread 생성시마다 Kernel Thread 생성
> Thread 생성은 Kernel 레벨에서 생성하기에 자원이 많이 소모되는 작업

Kernel Thread 생성 단계
1. 커널 스레드 ID(식별자) 및 TCB(Task Control Block) 생성
2. 독립적인 스택 메모리 할당
3. 스레드 상태 설정 (Ready)
4. 스케쥴러의 ready 큐에 등록