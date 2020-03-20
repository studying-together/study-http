## OK HTTP 1.0 오픈소스 분석

![OkHttp Architecture](https://github.com/Study-Java-Together/study-http/blob/master/documents/member/homelus/image/okhttp1.0-architecture.png)

OkHttpClient 는 open() 메서드를 이용해 HttpURLConnection 을 생성하고 기본값들을 정의하고 전달하는 시작점의 역할을 한다.

HttpURLConnection 에서 결과값을 얻어오는 getResponse() 메서드를 실행할 때 실제 커넥션 연결과 데이터를 주고 받게 된다.

> 미리 필요한 정보를 세팅해놓고 실제 결과값을 사용할 때 네트워크 커넥션이 연결된다. Lazy-loading 의 개념과 일치한다고 생각한다.

HttpURLConnection 은 HttpEngine 을 이용해 네트워크 통신을 한다.

> HTTP 네트워크 통신은 Stateless 하므로 요청과 응답으로 종료된다.
> 요청은 sendRequest() 메서드로 응답은 readResponse() 로 대응된다.

#### 두 가지로 나누어 살펴보기

##### sendRequest()

1. 통신을 위해 RequestLine + 기본적인 헤더 정보(Host, User-Agent, Connection, Cookie ...)를 설정한다.
2. 캐시 사용여부를 체크하고 캐시 사용이 가능하고 캐시가 있다면 캐시를 사용한다. **(종료)**
3. 서버와 커넥션을 맺는다. (Socket 을 이용해 TCP 커넥션만 맺는다)
  - (https 라면 SSLSocketFactory 와 HostnameVerifier 를 설정한다)
  - 커넥션 객체의 재사용을 위한 RouteSelector 를 이용해 커넥션(Connection) 객체를 가져온다.
  - Connection 객체를 이용해 서버와 소켓 통신을 맺는다.
  - (https 라면 socket 을 sslSocket 으로 변경한다)
  - InputStream 과 OutputStream 을 보관한다.

##### readResponse()
  - 캐시를 사용한다면 종료한다 **(종료)**
  - Request 전송!
    - 헤더를 OutputStream 으로 전송한다.
    - 바디가 있다면 OutputStream 으로 전송한다.
    - OutputStream 을 Flush 한다
  - Response 받기 !
    - StatusLine 과 Header 를 InputStream 으로 읽는다
    - responseBody 를 위한 InputStream 을 보관한다.


  
