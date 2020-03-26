
![Class Diagram](https://github.com/Study-Java-Together/study-http/blob/master/documents/member/misudev/image/classdiagram.png)

## Delegate pattern이 적용
	| Delegate pattern은 상속을 대체할 때 사용한다.
	| 어떤 메서드의 처리를 다른 인스턴스의 메서드에 맡긴다.
  
![OkHttp Architecture](https://github.com/Study-Java-Together/study-http/blob/master/documents/member/misudev/image/HTTPS.png)

## 차이점
1. OKHttpClient.open(String url) —> HttpsURLConnectionImpl 리턴, delegate는 반대로 HttpURLConnectionImpl를 리턴한다.
2. connection.getContentType() —> delegate.getContentType(); —> HttpURLConnectionImpl의 getHeaderField()메소드를 사용한다.
3. HttpEngine을 초기화 할 때 (initHttpEngine()) newHttpEngine() 에서 HttpsEngine을 리턴한다.
4. connect() 메소드 내에서  https인 경우 sslSocketFactory , hostnameVerifier를 초기화해준다.
    - sslSocketFactory : SSLsocket을 생성해준다.
    - homenameVerifier : SSL 를 사용하여 서버와의 연결을 성립하기 전에 연결하고자 하는 서버, 그리고 공개 키와 함꼐 제공된 인증서에 대한 신뢰 여부를 검증하는 작업을 담당한다.
5. upgradeToTls()
    - SSLSocket 생성
    - sslSocket.startHandshake()
    - inputStream, outputStream을 다시 설정
