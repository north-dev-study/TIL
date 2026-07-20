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

### 4. 통신 세팅

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


