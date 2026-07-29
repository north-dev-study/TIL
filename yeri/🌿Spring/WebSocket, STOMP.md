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


