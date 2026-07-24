## 핵심 파일

### 1. Apache SSL 설정 파일 

`/etc/httpd/conf.d/ssl.conf`

여기에 Apache가 사용할 인증서/개인키 지정
```
SSLCertificateFile /etc/pki/tls/research-lab/research-2026.crt
SSLCertificateKeyFile /etc/pki/tls/research-lab/research-2026.key
```


교체 이후엔 재시작 or reload 필요

