참조 : 테코톡 https://www.youtube.com/watch?v=HIGc93pqTAc&list=PLgXGHBqgT2TvpJ_p9L_yZKPifgdBOzdVH&index=53


Request가 동시다발적으로 서버에 들어왔을때, 서버가 처리하는 방법은?
순차 처리로는 x, 병렬처리로 하기 위해 각 요청마다 스레드를 할당

## Thread
- 프로세스 안에서 실행되는 작업 흐름의 단위
- 하나의 프로세스 안에서 다른 스레드와 메모리 공간 공유
- CPU 코어 하나는 한 번에 하나의 스레드만 실행 / CPU 코어를 여러 스레드가 사용하기 위해 **컨텍스트 스위칭** 과정이 필요함

![409](../../assets/Pasted%20image%2020260727173742.png)



## Thread의 생성 과정

Java는 1:1 스레딩 모델로 Thread를 생성
유저 Thread 하나당 OS Thread 하나가 대응됨(JVM이 요청)

새로운 Thread 생성시마다 Kernel Thread 생성
-> Thread 생성은 Kernel 레벨에서 생성하기에 자원이 많이 소모되는 작업

**Kernel Thread 생성 단계**
1. 커널 스레드 ID(식별자) 및 TCB(Task Control Block) 생성
2. 독립적인 스택 메모리 할당
3. 스레드 상태 설정 (Ready)
4. 스케쥴러의 ready 큐에 등록

때문에 스레드 생성이 많아진다면 부하가 걸릴 수 있다


## Thread를 무작정 많이 만들어놓는다면?

컨텍스트 스위칭
: CPU가 이전에 실행중인 스레드의 상태를 저장하고 새로운 스레드의 상태를 불러옴 / 사용자에게 멀티태스킹 환경을 제공하기 위한 작업 /  해당 과정에서 CPU 오버헤드 발생


컨텍스트 스위칭이 빈번하게 발생 -> 왜?
> CPU가 8개인데, 요청이 1000개라면 1000개의 요청에 대한 1000개의 쓰레드가 생성이 되면서, 8개의 CPU를 1000개의 스레드가 병렬작업을 하게됨. 병렬처리되는 스레드의 수가 커지면 커질수록 오버헤드도 커짐


## Thread Pool

- 스레드를 제한된 수만큼 생성해 관리
- 요청이 들어오면 작업 큐에 넣어 빈 스레드를 꺼내서 쓰고, 요청이 끝난 스레드는 반환되어 재사용

- Spring Boot에서는 내장 tomcat에서 Thread Pool을 설정해 사용 가능

### Tomcat Thread pool setting

![418](../../assets/Pasted%20image%2020260727180007.png)

> - accept-count : 처리 중인 요청이 너무 많을 때. 요청을 잠시 대기시키는 대기열의 크기
> - max-threads : 스레드 풀에 만들 수 있는 스레드의 최대 개수
> - max-connections : 톰캣이 최대로 처리할 수 있는 커넥션의 수. 요청이 들어오면 톰캣의 Connector가 Connection을 생성하면서 요청된 작업을 ThreadPool의 Thread와 연결
> - min-spare : 항상 유지하는 최소 예비 스레드 수 (서버 시작히 이정도는 무조건 생성)
> - max-queue-capacity : Tomcat 내부 ThreadPool이 사용하는 작업 큐의 크기
	( 위 예시는 무제한 큐)


### Thread pool의 갯수 설정
스레드 풀 적정 크기 = CPU 코어 개수 * ( 1 / 평균 CPU 사용 시간 비율)

- 만약 DB 등 외부 자원 커넥션 풀을 스레드 풀 내부에서 요청해 사용하는 경우에는 스레드풀이 DB 커넥션 풀 갯수보다 많다면 쓰레드가 놀기때문에 커넥션 풀 크기를 확인해봐야함