## OKHttp 1.0.0의 Cache 


1. RFC의 명세를 전적으로 따른다. 
<br>
당연한 얘기겠지만 OkHttp 1.0에서는 Http 1.1를 기준으로 구현되어 있다. <br>
OkHttp 코드를 따라가다보면 RFC의 링크를 주석으로 달아둔 경우가 많은데, 디버깅 전후로 cache 관련된 로직이 이해가 되지 않다면, RFC를 참고하는게 좋다. 


ex)
``` java
// only-if-cached가 선언되었을 경우

// The raw response source may require the network, but the request
// headers may forbid network use. In that case, dispose of the network
// response and use a GATEWAY_TIMEOUT response instead, as specified
// by http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4.
if (requestHeaders.isOnlyIfCached() && responseSource.requiresConnection()) {
  if (responseSource == ResponseSource.CONDITIONAL_CACHE) {
    Util.closeQuietly(cachedResponseBody);
  }
  this.responseSource = ResponseSource.CACHE;
  this.cacheResponse = GATEWAY_TIMEOUT_RESPONSE;
  RawHeaders rawResponseHeaders = RawHeaders.fromMultimap(cacheResponse.getHeaders(), true);
  setResponse(new ResponseHeaders(uri, rawResponseHeaders), cacheResponse.getBody());
}
```
only-if-cached 헤더는 새로운 데이터를 내려받지 않겠다는 것을 나타낸다. <br>
클라이언트는 서버에게 캐쉬된 응답만을 요구하며 
(어쩌면 pool network 상태로 인해) 최신의 응답값이 있는지 확인하는 수고스러움을 원하지 않을 때, 해당 헤더 값을 포함한다. 

<br>
2. Cache 관련 헤더 정보를 얻는 방법
<br>
HttpEngine이 생성될 때, requestHeader에 필요한 Header정보를 담는데  
HttpURLConnectionImpl#initHttpEngine()에서 다음과 같이 HttpEngine을 생성한다.

> httpEngine = newHttpEngine(method, rawRequestHeaders, null, null); 
``` java
if (url.getProtocol().equals("http")) {
  return new HttpEngine(this, method, requestHeaders, connection, requestBody);
} 
```
이를 따라가다보면 requestHeaders는 OkHttp의 RequestHeaders 객체로 생성된다는 것을 알 수 있다. </br>
RequestHeaders의 생성자를 보면 handle(HeaderParser.CacheControlHandler)이라는 메소드를 구현해 Cache-Control에 해당하는 데이터를 파싱한다. </br>
그 외의 헤더 정보는 if문을 이용해 RawHeaders 객체에서 헤더 정보를 하나씩 살펴가며 **request Header** 값을 대입한다. 
> RawHeaders를 살펴보면 헤더 정보(헤더 name, value)를 List에 담아 관리한다는 것을 알 수 있다. 
> ``` java
> private final List<String> namesAndValues = new ArrayList<String>(20);
> ```
> namesAndValues[2n] : 헤더 name, namesAndValues[2n+1] : 해당 name에 대응하는 value  <br>
> 보기 쉽게 [n]로 표현했습니다.

RequestHeaders는 Rawheaders값으로부터 대입한 헤더 정보들을 활용하기 좋게 boolean, String, Date와 같은 데이터 타입로 저장해두고 데이터 타입에 따라 파싱 메소드도 구현을 해두었다. isPublish나 no-store, no-cache와 같이 여부 정보를 따지는 헤더값일 경우 boolean 필드에 담는다. 

<br>
3. 고민중입니다. 
<br>
<br>

> OkHttp Cache 디버깅 따라가기 <br>
> [notion에서 확인하기](https://www.notion.so/Cache-e15edce0cc274539b27facca2b5465dd)


