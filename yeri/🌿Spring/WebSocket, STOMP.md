참조 : 테코톡 https://www.youtube.com/watch?v=ckxmMbwmn98&list=PLgXGHBqgT2TvpJ_p9L_yZKPifgdBOzdVH&index=44


## 채팅을 HTTP로 구현한다면

- 새로운 메시지가 있는지 주기적으로 서버에 물어봐야함

## 채팅을 웹소켓으로 구현한다면

- HTTP의 문제점을 해결. client와 server 사이에 통화가 유지되는 상태
- 서버에 새로운 메세지가 들어올때마다 실시간으로 client에게 안내


## 웹소켓 연결 방법

- client의 브라우저는 서버에 HTTP Request 전송, 해당 Request를 웹소켓으로 업그레이드 요청

```header
GET /ws HTTP/1.1
Host: test.com
Connection: upgrade // 현재 연결 방식을 업그레이드
Upgrade: websocket // 웹소켓 방식으로 업그레이드 요청
Sec-Websocket-Key: TestChatKey123455678==
Sec-Websocket-Version: 13
```


- 웹소켓 생성 후 server 응답 `HTTP/1.1 101 Switching Protocols`

## 웹소켓 메세지 전달

- 웹소켓은 바이너리 or 텍스트 데이터만 전송 /  메세지 형식은 제공 X
- 서버는 정해진 형식이 없기 때문에 client가 보낸 메세지를 어떻게 해석할지 모름
-> **client / server가 이해할 수 있는 구조화된 형식 필요!**


## STOMP (Simple Text Oriented Messaging Protocol)

- 메세지 형식 문제를 해결하기 위해 Spring에서 사용하는 방식
- 간단한 텍스트 기반 프로토콜

### STOMP 형식

```
COMMAND
header1:value1
header2:value2

Body^@
```

COMMAND : 무엇을 할 것인지 지시하는 명령어. 메세지 전송, 연결, 해제와 같은 명령어
header1 : 명령어에 대한 추가 정보나 옵션 / 메세지 경로나 내용 형식
Boday : 실제 전송할 메시지 내용

**예시**
```
SEND
destination:/topic/room1
content-type:application/json

{"sender":"Pub","message":"안녕 친구들!"}
```


### Pub / Sub 구조
- 이 구조를 통해 하나의 메세지를 여러 사용자에게 동시 전달 가능
- Publisher (발행자), Subscriber(대상자) - 여러명 가능, Topic(메세지 분류 경로)
- Subscriber들이 관심있는 Topic을 구독하면, 해당 토픽의 메세지를 모두 받을 수 있음

![](../../assets/Pasted%20image%2020260729112231.png)


### Spring 내부 프로세스
![](../../assets/Pasted%20image%2020260729112526.png)

- inboundChannel : 텍스트 형태의 메세지를 STOMP 형식으로 파싱, 헤드에서 경로 확인
- Message Handler : 경로의 컨트롤러. 로직 실행 후 새로운 메세지 반환
- brokerChannel : 모든 채널의 구독 경로 확인, 경로 매칭 채널에 메시지 전송
- outboundChannel : 클라이언트 응답 전용 채널. 전달 받은 메세지를 구독한 client에게 전달