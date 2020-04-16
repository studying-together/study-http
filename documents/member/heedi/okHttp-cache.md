## OkHttp 1.0의 Cache (수정중)


1. RFC의 명세를 전적으로 따른다.
2. HttpEngine이 생성될 때, requestHeader에 필요한 Header정보를 담는데  
HttpURLConnectionImpl#initHttpEngine()에서 다음과 같이 HttpEngine을 생성한다. 
> httpEngine = newHttpEngine(method, rawRequestHeaders, null, null); 
``` java
if (url.getProtocol().equals("http")) {
  return new HttpEngine(this, method, requestHeaders, connection, requestBody);
} 
```
이를 따라가다보면 requestHeaders는 OkHttp의 RequestHeaders 객체로 생성된다. 
해당 클래스의 생성자를 보면 HeaderParser의 CacheControlHandler라는 익명 클래스를 이용해 handle이라는 메소드를 구현해 **request Header**의 값을 대입한다. 
handle 메소드는 수많은 if문으로 이루어져 cacheControl 데이터를 파싱한다. 
그 외 헤더 정보는 List에 담긴 헤더를 하나씩 살펴가며 설정한다. 
    
    
