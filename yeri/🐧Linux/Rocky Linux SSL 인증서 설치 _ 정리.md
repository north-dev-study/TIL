## 핵심 파일

### 1. Apache SSL 설정 파일 

`/etc/httpd/conf.d/ssl.conf`

여기에 Apache가 사용할 인증서/개인키 지정
```
SSLCertificateFile /etc/pki/tls/research-lab/research-2026.crt
SSLCertificateKeyFile /etc/pki/tls/research-lab/research-2026.key
```

교체 이전 백업
교체 이후 재시작 or reload 필요
```
sudo apachectl configtest
sudo systemctl reload httpd
```


### 2. 인증서 저장 디렉토리 (임의 설정)
`/etc/pki/tls/research-lab/`

.crt, .key 파일 쌍으로 생성
```
.crt : 공개 가능한 서버 인증서 
.key : 외부에 공개하면 안 되는 개인키
```

생성 후 권한 설정 필요. 

