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

**5-1. hosts 파일에 도메인 추가**
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



**5-2. Rocky Linux에서 첫번째 SSL 인증서 생성**

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

생성 성공 화면
![](../../assets/Pasted%20image%2020260721220043.png)

권한 설정
```
sudo chmod 600 research-2026.key
sudo chmod 644 research-2026.crt
```
`600` : 파일의 소유자(Owner)만 읽고(r) 쓸(w) 수 있고, 그룹(Group)이나 다른 사용자(Other)는 아무런 접근도 할 수 없음
`644` : 소유자만 내용을 수정할 수 있고, 다른 사람들은 내용을 열어보기만 할 수 있도록 허용


**5-3. Apache SSL 설정 파일 확인**
mod_ssl 설치시 기본 설정 파일 생겼는지 확인하는 작업.
```
sudo ls -l /etc/httpd/conf.d/ssl.conf
```

인증서 경로 설정부분도 확인해야함
```
sudo grep -nE 'SSLCertificateFile|SSLCertificateKeyFile' /etc/httpd/conf.d/ssl.conf
```
![](../../assets/Pasted%20image%2020260721220626.png)

Apache는 `mod_ssl`과 OpenSSL을 통해 HTTPS를 제공하며, 인증서와 개인키는 각각 `SSLCertificateFile`, `SSLCertificateKeyFile` 지시어로 지정한다.



**5-4. ssl.conf 에 인증서 경로 연결**
ssl.conf 내용 수정(아까 인증서 만든 경로로 수정)
```
결과 :
SSLCertificateFile /etc/pki/tls/research-lab/research-2026.crt SSLCertificateKeyFile /etc/pki/tls/research-lab/research-2026.key
```


Apache 설정 검사
```
sudo apachectl configtest -> Syntax OK 나오면 정상
```


Apache 재시작
```
sudo systemctl restart httpd

상태체크 : sudo systemctl status httpd
```


HTTPS 연결 테스트
Linux 내부
```
curl -k -I https://localhost
```


로컬
```
curl.exe -k -I https://research.lab.local
```

HTTPS 통신 성공 !!

![](../../assets/Pasted%20image%2020260721222237.png)




## 현재 상황

기존 인증서 : `research-2026.crt/key`

현재 인증서 지정하는 곳 : `/etc/httpd/conf.d/ssl.conf`


### 6. 인증서 교체

**6-1. 교체용 새 인증서 생성**

아까 생성해놓은 인증서 디렉토리로 이동
`cd /etc/pki/tls/research-lab`


새 개인키와 인증서 생성 / 권한설정
```
sudo openssl req -x509 -nodes -newkey rsa:2048 -keyout research-2027.key -out research-2027.crt -days 730 -subj "/C=KR/O=SSL-Lab/CN=research.lab.local" -addext "subjectAltName=DNS:research.lab.local,IP:172.21.199.101"

sudo chmod 600 research-2027.key
sudo chmod 644 research-2027.crt
```


기존 인증서와 새 인증서의 시리얼/유효기간 비교 > 달라야 정상
```
sudo openssl x509 -in research-2026.crt -noout -serial -dates
sudo openssl x509 -in research-2027.crt -noout -serial -dates
```


새 인증서와 개인키가 서로 맞는지 확인. 해시값이 같으면 정상
```
sudo openssl x509 -in research-2027.crt -pubkey -noout | sha256sum
sudo openssl pkey -in research-2027.key -pubout | sha256sum
```



6-2. 기존 설정 백업 후 새 인증서 교체

