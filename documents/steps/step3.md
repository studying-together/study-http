# :zap: 세번째 모임

<hr>

#### HTTP Client 가 무엇인가요

##### YHJ : HTTP Server 와 통신하도록 도와주는 라이브러리, 통신을 효율적으로 사용할 수 있도록 내부적으로 여러 기능을 제공한다.
##### KYS : HTTP 서버와 통신을 할 수 있도록 기능을 제공하는 라이브러리, 사용자는 프로토콜의 변할 수 있는 부분만 결정해주면, 클라이언트가 서버와 소통할 수 있는 형태로 변환하여 요청 해주고, 응답 역시 사용자가 쉽게 해석하고 그에 맞는 처리를 **쉽게** 할 수 있도록 변환하여 제공해 준다.
##### CHH : 클라이언트 측에서 동시에 일어나는 HTTP 서버 통신을 제어하고, 효율적으로 리소스를 관리함으로서 개발자에게 어플리케이션 단 개발에만 집중할 수록 도와준다.
##### KDH : server에게 요청하기 위해 요청 메시지를 만들고, 커넥션의 시작의 역할을 맡는다. 서버에서 받은 응답을 적절한 방식으로 사용자에게 표현한다.
##### KDH : 필요한 정보를 위해 Server에 요청을 보내고 받는 역할을 담당한다. Client가 Http 프로토콜을 기반으로 통신하는 과정에서 필요한 포맷과 기능을 지원하는 범용 라이브러리인 okHttp, RestTemplate(Spring), Apache의 HttpClient 등이 있다. 
##### JMS : HTTP Client는 HTTP 프로토콜에 맞게 request를 보내고 response를 받는 작업을 도와주는 라이브러리다.
##### HSM : 추상적으로 생각해보면 HTTP Client는 HTTP 통신으로 외부에 있는 누군가와 의사소통을 하려는 당사자라고 생각합니다. 구현에 초점을 두고 생각해본다면 HTTP 제약에 맞춰 목적지 서버와 통신을 위해 관련 요청을 하고 수신 처리를 하도록 도와주는 라이브러리라고 생각합니다.

<hr>

## 스터디 진행 내용

1. 두 번째 과제 공유

2. 재밌거나 신기하거나 어려웠거나

3. URLConnection ?

4. HTTP Client 의 역할

5. HTTP Client 에는 어떤게 있을까

6. HTTP Client 를 왜 사용할까? 왜 선택했나?

## :flashlight: 코드 디버깅하기

- 오픈 소스(OkHTTP)를 이용해 클라이언트가 HTTP 로 통신하는 과정을 디버깅해보자
- 높은 버전은 어려우므로 1 버전으로 진행 !

- OkHttp 를 **디버깅**하며 정리한 내용을 한 장의 문서로 만들어 **공유**해보자 !
   - YHJ [Http 구조 정리](https://github.com/Study-Java-Together/study-http/blob/master/documents/member/homelus/what-okHttp.md)! 
   - KYS
   - CHH
   - KDH [OkHttp디버깅 & 헤더 뜯어보기](../member/kimdahyeee/what-okHttp.md)
   - KDH [Http 구조 정리](https://github.com/Study-Java-Together/study-http/blob/master/documents/member/heedi/what-okHttp.md)!
   - JMS
   - HSM [Http 구조 정리](https://github.com/Study-Java-Together/study-http/blob/master/documents/member/sungminhong/what-okHttp.md)! 

#### 다음을 참고하자

> 괄호() 는 클래스를 의미

1. 클라이언트 생성 (OkHttpClient)
2. 클라이언트 URL 설정 및 초기화 (OkHttpClient -> HttpURLConnectionImpl)
3. 요청 및 응답 처리 (HttpURLConnectionImpl)
   1. httpEngine 초기화
   2. 요청 보내기 (HttpEngine)
      1. 요청 헤더 준비하기
      2. 캐시 판별하기
      3. Socket 생성 (TCP 연결 맺기)
      4. Https 확인하고 SSL 설정하기
      5. RouteSelector 를 이용해 Connection 을 생성
      6. **socket 생성** (Connection)
      7. **socket 연결** (Connection)
   3. 응답 읽기 (HttpEngine)
