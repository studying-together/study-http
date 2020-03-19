
1. 클라이언트 생성 (OkHttpClient)
2. 클라이언트 URL 설정 및 초기화 (OkHttpClient -> HttpURLConnectionImpl)
3. 요청 및 응답 처리 (HttpURLConnectionImpl)
    connection.getContentType() —> connection.getHeaderField(“content-type”) —>RawHeaders rawHeaders = getResponse().getResponseHeaders().getHeaders();
    * httpEngine 초기화
    * 요청 보내기 (HttpEngine)
        * getResponse() —> execute() <request를 보내고 선택적으로 response를 읽는다> —> httpEngine.sendRequest()
        * 요청 헤더 준비하기
            * httpEngine.prepareRawRequestHeaders() : requestHeaders를 기본값과 쿠키로 채운다.
                * 기본값  : User-agent (java8) , host (url) , connection(Keep-Alive), accept-encoding (gzip), ..)
                * 쿠키 
        * 캐시 판별하기
            * httpEngine.initResponseSource() — 응답소스 초기화 —NETWORK
            * 
        * Socket 생성 (TCP 연결 맺기)
            * responseSource.requiresConnection() <NETWORK면 true>
            * httpEngine.sendSocketRequest()
                * Connection == null —> connect()
                * httpEngine.connect()=========================
                    * New RouteSelector(address, uri, policy.proxySelector, policy.connectionPool, Dns.DEFAULT, policy.getFailedRoutes());
                        * routeSelector.resetNextProxy()
                    * <<RouteSelector 를 이용해 Connection 을 생성>>
                    * connection = routeSelector.next(); ———————————
                        * Connection pooled = pool.get(address); —> connectionPool에 사용 가능한 connection이 있으면 받아온다.(Returns a recycled connection) 새로운  connection보다 풀링된 connection이 우선된다.

                        * routeSelector.resetNextInetSocketAddress() —> socketHost로 socket name을 읽어온다. (socketAddresses = dns.getAllByName(socketHost); )
                        * routeSelectoresetNextTlsMode() —> 이건 뭔지 모르겠당.
                        * Route route = new Route(address, lastProxy, lastInetSocketAddress, modernTls);
                        * Return new Connection(route)
                    * connection.connect(policy.getConnectTimeout(), policy.getReadTimeout(), getTunnelConfig());
                        * <<socket 생성 (Connection)>>
                        * Socket 생성 <<  proxyType (DIRECT, HTTP, SOCKS) >>
                        * In = socket.getInputStream();
                        * Out = socket.getOutputStream();
                        * <<socket 연결 (Connection)>>
                        * socekt.connect()
                        * 
                    * policy.connectionPool.maybeShare(connection); ??
                    * 
                    * connected(connection); (소켓 연결이 풀에서 생성되거나 검색된 후에 호출)
                * transport = (Transport) connection.newTransport(this);
                *  



            * 
        * Https 확인하고 SSL 설정하기
        * RouteSelector 를 이용해 Connection 을 생성
        * socket 생성 (Connection)
        * socket 연결 (Connection)
    * 응답 읽기 (HttpEngine)
        * getResponse() —> execute() —> httpEngine.readResponse() 
        * (나머지 요청 헤더 및 본문을 flush하고 HTTP 응답 헤더를 구문 분석하고 HTTP 응답 본문이있는 경우 읽기를 시작합니다.)
            * hasResponse() — responseHeaders != null
            * responseSource.requiresConnection
                * ResponseSource : enum으로 CACHE, NETWORK, CONDITIONAL_CACHE 세 종류가 있다. 기본값은 NETWORK
            * responseSource.requiresConnection() —> NETWROK or CONDITIONAL_CACHE인 경우 true // false이면 리턴
            * sentRequestMillis == -1 이면 ->if (requestBodyOut instanceof RetryableOutputStream)
            * requestBodyOut != null 이면 close, writerequestbody( 처음 로직에서는 null)
            * transport.flushRequest();
