## OkHttpClient 

#### OkHttpClient 객체 생성
 1. open 메소드 호출
    - copyWithDefaults() 메소드 호출
    - OkHttpClient 멤버 변수 초기화 작업
        - ProxySelector 
        - CookieHandler
        - ResponseCache
        - HttpsURLConnection
        - OkHostnameVerifier
        - HttpAuthenticator
        - ConnectionPool
        - followProtocolRedirects
        - transports
    - 프로토콜 값에 따라서 
        - http => HttpURLConnectionImpl 객체 리턴
        - https => HttpsURLConnectionImpl 객체 리턴
        - 이외 => IllegalArgumentException 예외 발생

#### HttpURLConnectionImpl 객체 리턴
 1. getContentType() 메소드 호출
 2. getHeaderField("content-type") 메소드 호출
     - getResponse 메소드 호출
        - initHttpEngine() 메소드 호출
            - httpEngineFailure 인스턴스가 null이 아니면 IOException 발생
            - httpEngine 인스턴스가 null이 아니면 해당 객체 리턴
            - connected 값 true 로 변경
            - doOutput 값이 true이면 httpMethod 값이 GET이면 POST로 변환 / httpMethod 값이 PUT이나 POST가 아니면 ProtocolException 발생
            - newHttpEngine(httpMethod, rawRequestHeaders, null, null) 메소드 호출
                - 프로토콜이 http이면 new HttpEngine(this, httpMethod, requestHeaders, connection, requestBody) 생성자 호출
                    - policy, httpMethod, connection, requestBodyOut, uri 인스턴스 초기화
                    - RequestHeaders 객체 생성
                        - uri, headers 값 초기화
                        - HeaderParser.CacheControlHandler 객체 생성
                        
                - 프로토콜이 https이면 new HttpsURLConnectionImpl.HttpsEngine(this, httpMethod, requestHeaders, connection, requestBody); 생성자 호출
            - httpEngine.hasResponse()의 값이 true이면 httpEngine 리턴
            - while(true) 코드블럭 진입
                - execute() 메소드 실행
                    - HttpEngine 객체에 sendRequst() 메소드 실행
                        - ResponseSource 가 있으면 리턴
                        - prepareRawRequestHeaders() 메소드 실행
                            - requestHeaderLine 만들기
                            - simpleRequestHeader 만들기
                        - initResponseSource() 메소드 실행
                            - 생략...
                        - OkResponseCache 객체가 있으면 policy.responseCache.trackResponse(responseSource); 메소드 호출
                        - requestHeaders.isOnlyIfCached() && responseSource.requiresConnection() 값이 true 이면,
                            - 생략...
                        - responseSource.requiresConnection() 이 true 이면,
                            - sendSocketRequest() 메소드 실행
                                - HttpEngine 객체에 connection 인스턴스가 없으면 connect() 메소드 실행
                                    - routeSelector 인스턴스 결정
                                    - 결정된 routeSelector 로 Connection 객체 초기화
                                    - connected(connection); 메소드 실행
                                        - HttpsURLConnectionImlp의 connected 메소드 실행하며 SSLSocket 생성
                                    - connection.getRoute().getProxy() != policy.requestedProxy true 이면 requestLine 서버로 보냄
                                - transport 객체 생성
                                    - transport.createRequestBody() 메소드 실행
                                        - 생략... 
                                
                - processResponseHeaders()를 통해 Retry enum 값 결정
                - Retry.NONE 이면 httpEngine.automaticallyReleaseConnectionToPool(); 호출 및 httpEngine 리턴
                - 아래는 Retry를 하기위한 작업 진행
            
     - getResponseHeaders 메소드 호출
        - 위에 getResponse() 작업을 통해서 HttpEngine에 세팅된 responseHeaders 객체 리턴
        
     - getHeaders 메소드 호출
        - 위에 reponseHeaders 객체로 부터 rawHeader 객체 리턴
 
 
#### RawHeaders 객체 생성
 1. fieldName에 해당하는 String 값 리턴
    - fieldName이 null 이면, statusLine() 메소드 호출
    - fieldName이 null 이 아니면, rawHeader.get() 메소드 호출
        - namesAndValues for문을 돌면서 맞는 값 리턴