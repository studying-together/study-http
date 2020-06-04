# HTTPS 살펴보기

## 개요

### 기본(Basic)
Authenticate Header: (ID:PASSWORD -> Base64) with realm
### 다이제스트(Digest) 인증
Authenticate Header: digest with nonce, realm

## HTTPS
SSL vs TLS (https://www.globalsign.com/en/blog/ssl-vs-tls-difference)

### 개념

##### 암호(Cipher),
##### 키(Key)
##### 대칭키 암호체계(DES, Triple-DES, RC2, RC4)
##### 비대칭키 암호체계
##### 공개키 암호법(X509, RSA)
##### 디지털 서명(checksum)
##### 디지털 인증서(X509 V3 인증서, 서명 검증)

### 세부사항

##### SSL handshake
##### [통신 과정 참고](https://www.ssl.com/article/ssl-tls-handshake-overview/)
