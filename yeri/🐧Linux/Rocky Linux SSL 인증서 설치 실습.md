### 0. 구성형태
```
Windows 11
 ├─ 웹 브라우저 / curl
 └─ Hyper-V 가상머신
      └─ Rocky Linux 9
           ├─ Apache httpd
           ├─ mod_ssl
           ├─ OpenSSL
           └─ 테스트 인증서
```

**Windows 11 안에 Rocky Linux 가상머신을 만들고**, 그 안에 Apache와 `mod_ssl`을 설치한 뒤 **자체 인증기관(CA)으로 발급한 인증서 두 세트**를 번갈아 적용해 봄

### 1. rocky linux 다운로드
https://rockylinux.org/download
Minimal ISO 다운로드 (실습용) - 서버에 필요한 최소 환경을 설치하는 이미지

### 2. windows 세팅

2-1. windows 버전 확인

```
PS C:\WINDOWS\system32> Get-ComputerInfo | Select-Object WindowsProductName, WindowsVersion                             
WindowsProductName WindowsVersion
------------------ --------------
Windows 10 Pro     2009
```

가상화 활성화 체크
```
systeminfo.exe
```


2-2. Hyper-V 관리자 열기

```
virtmgmt.msc
```

![](../../assets/Pasted%20image%2020260720205932.png)



2-3. 가상머신 만들기

|항목|권장값|
|---|--:|
|이름|Rocky-SSL-Lab|
|세대|2세대|
|메모리|4096MB|
|동적 메모리|사용|
|네트워크|Default Switch|
|가상 디스크|30GB|
|설치 미디어|Rocky Linux Minimal ISO|

생성완료 화면
![](../../assets/Pasted%20image%2020260720210610.png)


2-4. Rocky Linux 실행

여기서 연결을 누르면 SSO 이미지 연동해놓은거로 열림

![](../../assets/Pasted%20image%2020260720211208.png)



### 3. Rocky Linux 설치 및 세팅
- 유저 정보
- 네트워크 정보
- DB 동적 할당 세팅
-> 이후 재부팅 / VM 에 ISO 이미지 없는거 확인

### 4. Apach 세팅

IP  주소 확인
```
ip addr
hostname -I

> 172.21.199.101
```

인터넷 연결 확인
```
ping -c 4 8.8.8.8
```
![](../../assets/Pasted%20image%2020260720212616.png)


Apache와 SSL 패키지 설치
```
sudo dnf install -y httpd mod_ssl openssl
```

설치 후 버전 확인
![](../../assets/Pasted%20image%2020260720212923.png)


Apach 띄우고, 부팅시 자동실행 세팅
- 아파치 시작
  ```
  sudo systemctl start httpd
  ```

- 부팅시 자동실행
```
sudo systemctl enable httpd
```

- 세팅 상태 확인
```
sudo systemctl status httpd
```

![](../../assets/Pasted%20image%2020260720213340.png)



HTTP와 HTTPS 방화벽 허용
```
sudo firewall-cmd --permanent --add-service=http
sudo firewall-cmd --permanent --add-service=https
sudo firewall-cmd --reload
```

허용 확인
```
sudo firewall-cmd --list-services
```


Rocky Linux 내부에서 Apache 확인
```
curl -I http://localhost

정상응답 : 
HTTP/1.1 200 OK
Server: Apache
```

만약 403 뜨면, `/var/www/html` 하위에 index.html 파일 하나 만들고 다시 콜하면됨


로컬 파워쉘에서 Rocky Linux hostname 확인해서 curl 날려보면 나옴! 통신성공!

```
curl.exe -I http://[hostname]
```

![](../../assets/Pasted%20image%2020260720214354.png)




### 5. HTTPS 실행

5-1. hosts 파일에 도메인 추가
위치 : `C:\Windows\System32\drivers\etc\hosts`
추가 : `172.21.199.101 research.lab.local`

도메인 적용 확인
```
PS C:\Users\poikl> ipconfig /flushdns

Windows IP 구성

DNS 확인자 캐시를 플러시했습니다.
PS C:\Users\poikl> ping research.lab.local

Ping research.lab.local [172.21.199.101] 32바이트 데이터 사용:
172.21.199.101의 응답: 바이트=32 시간<1ms TTL=64
172.21.199.101의 응답: 바이트=32 시간<1ms TTL=64
172.21.199.101의 응답: 바이트=32 시간=1ms TTL=64
172.21.199.101의 응답: 바이트=32 시간=1ms TTL=64

172.21.199.101에 대한 Ping 통계:
    패킷: 보냄 = 4, 받음 = 4, 손실 = 0 (0% 손실),
왕복 시간(밀리초):
    최소 = 0ms, 최대 = 1ms, 평균 = 0ms
PS C:\Users\poikl> curl.exe -I http://research.lab.local
HTTP/1.1 200 OK
Date: Tue, 21 Jul 2026 12:51:11 GMT
Server: Apache/2.4.63 (Rocky Linux) OpenSSL/3.5.5
Last-Modified: Mon, 20 Jul 2026 12:41:09 GMT
ETag: "24-6570a3887fdb0"
Accept-Ranges: bytes
Content-Length: 36
Content-Type: text/html; charset=UTF-8
```



5-2. Rocky Linux에서 첫번째 SSL 인증서 생성

dir 생성
```
sudo mkdir -p /etc/pki/tls/research-lab
cd /etc/pki/tls/research-lab
```

개인키 / 자체 서명 인증서 생성
```
sudo openssl req -x509 -nodes -newkey rsa:2048 \
  -keyout research-2026.key \
  -out research-2026.crt \
  -days 365 \
  -subj "/C=KR/O=SSL-Lab/CN=research.lab.local" \
  -addext "subjectAltName=DNS:research.lab.local,IP:172.21.199.101"
```

생성 성공 