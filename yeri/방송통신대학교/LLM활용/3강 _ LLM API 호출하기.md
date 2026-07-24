## REST Request
- 요청 대상 주소, 메소드, 인증 대상 키

## API key
- request와 함께 전송해 인증을 대체
- 이걸로 사용량 측정
- OpenAI는 충전 후 크레딧 차감 형식


## OpenAI key 발급

https://platform.openai.com/api-keys
여기서 API keys > 권한 All 로 해서 발급. 발급 후 생성된 시크릿 키는 개인소장

windows 변수 저장 (나중에 colab에서 가져다가 쓰기위해)
```
setx OPENAI_API_KEY "<mykey>"
```


## OpenAI API 라이브러리
- python, GO 등 5개 언어로 라이브러리 지원


## Responses API

- 텍스트/이미지 입력을 받아 텍스트 출력을 반환하는 기본 API

