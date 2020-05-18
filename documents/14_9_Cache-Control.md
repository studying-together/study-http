## 14 Header Field Definitions

### 14.9 Cache-Control

Cache-Control의 일반 헤더 필드는 요청과 응답 체인에 따라서 모든 캐싱 메카니즘에 의해 준수되어야만 하는 특별한 지시문으로 사용됩니다. 그 지시자는 캐시가 요청 또는 응답을 방해하지 않도록 합니다. 이러한 지시자는 일반적으로 기본적인 캐싱 알고리즘을 재정의 합니다. 캐시 지시자는 요청에서 캐시 지시자가 있다는 것이 주어진 응답에 동일한 캐시 지시자가 제공되는 것을 의미하지 않습니다. 이런 점에서 캐시는 단방향입니다. ( 서버 -> 클라 )

> NOTE : HTTP/1.0 캐시는 Cache-Control 구현하지 않을 수 있고, Pargma: no-cache (섹션14.32 참고) 로 구현할 수 있습니다.

캐시 지시자는 어플리케이션에 대한 중요성에 상관 없이 반드시 프록시나 게이트웨이 어플리케이션에 의해서 통과되어야 합니다. 왜냐하면 요청과 응답 체인에 따라서 모든 수신자에 그 지시자가 적용될 수 있기 때문입니다.

``` text
Cache-Control   = "Cache-Control" ":" 1#cache-directive

cache-directive = cache-request-directive | cache-response-directive

cache-request-directive =
          "no-cache" 			; Section 14.9.1
         | "no-store"                                	; Section 14.9.2
         | "max-age" "=" delta-seconds         	; Section 14.9.3, 14.9.4
         | "max-stale" [ "=" delta-seconds ]    	; Section 14.9.3
         | "min-fresh" "=" delta-seconds        	; Section 14.9.3
         | "no-transform"                           	; Section 14.9.5
         | "only-if-cached"                         	; Section 14.9.4
         | cache-extension                         	; Section 14.9.6

     cache-response-directive =
           "public"                                           	; Section 14.9.1
         | "private" [ "=" <"> 1#field-name <"> ]     	; Section 14.9.1
         | "no-cache" [ "=" <"> 1#field-name <"> ]	; Section 14.9.1
         | "no-store"                             		; Section 14.9.2
         | "no-transform"                         		; Section 14.9.5
         | "must-revalidate"                      		; Section 14.9.4
         | "proxy-revalidate"                     		; Section 14.9.4
         | "max-age" "=" delta-seconds            		; Section 14.9.3
         | "s-maxage" "=" delta-seconds           		; Section 14.9.3
         | cache-extension                        		; Section 14.9.6

    cache-extension = token [ "=" ( token | quoted-string ) ]
```

지시자가 1#field-name 매개 변수 없이 나타났을때, 그 지시자는 요청 또는 응답 전체에 적용된다. 지시자가 1#field-name 매개 변수와 함께 나타났을 때, 이름이 지정된 필드 혹은 필드들에 적용이되고 나머지 요청이나 응답에는 적용되지 않는다. 이러한 메카니즘은 확장성을 지원하는데, HTTP 프로토콜의 나중 버전을 구현한다고 할 때, 이러한 지시자들이 HTTP/1.1에 정의되어있지 않은 헤더 필드에 적용될 수 있기 때문이다.

cache-control 지시자들은 아래 일반적인 카테고리로 분류할 수 있다. :
- 캐시 가능한 것에 대한 제한 : 이것은 오리진 서버에 의해서 부과될지도 모른다.
- 캐시에 의해서 저장 될 수있는 것에 대한 제한 : 오리진 서버나 유저 에이전트에 둘 중 하나에 의해서 부과될지도 모른다.
- 기본적인 만료 매카니즘의 수정 : 오리진 서버나 유저 에이전트에 둘 중 하나에 의해서 부과될지도 모른다.
- 캐시 재검증과 재로드 제어 : 유저 에이전트에 의해서 부과될지도 모른다.
- 엔티티들의 변형 제어
- 캐시 시스템의 확장