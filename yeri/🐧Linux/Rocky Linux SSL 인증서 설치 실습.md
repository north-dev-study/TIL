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



