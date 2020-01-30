# HTTP 공부하기

## 스터디

- 방식
  - 질문이나 토의 주제는 Issues 등록
- 구조
  - Master branch 는 기능이 없는 뼈대로 사용
  - 개인의 branch 를 만들고 기능 구현

### 공부 방법
- HTTP Client/Server 구현해보기
- HTTP Client open source 를 분석하기 ([참고](https://github.com/square/okhttp/))

### 공부 목적
- HttpClient(브라우저) 에서 [**ConnectionPool** 은 어떻게 동작할까](/documents/connection_pool.md)
- 실제로 어떻게 [**Connection** 이 맺어질까](/documents/connection.md)
- HTTP 1.0 / 1.1 / 2.0 은 어떻게 통신하며 그러기 위해 어떻게 구현해야 할까 

### 공부 목표
- 간단한 HttpClient 를 만들어보자
- 간단한 HttpServer 를 만들어보자
- open source 에 PR 을 날려보자

### 더 알아보기
- Java 의 Socket 은 어떻게 동작하는가, In/Out putStream 은 어떻게 사용할까
- HTTP 버전 히스토리 1.0 -> 1.1 -> 2.0 -> 3.0

## 시작하기
- main 메서드를 이용해서 실행하자

### HTTP Server 를 만들어보자
- ServerSocket을 이용해 이벤트를 감시하고 요청이 왔을 때 소켓을 얻어 ThreadPool 로 처리하자.

### HTTP Client 를 만들어보자
- Socket 을 이용해 접속해보자

## 일정

0. 들어가며
왜 HTTP 를 공부해야 할까? [참고자료](https://www.slideshare.net/deview/d2-campus-http)

1. Socket 과 ServerSocket 이해하기 (1주)
  stream 을 어떻게 처리할 것인가?

2. HTTP Request Line, Response 이해하기 (1주)
  어떻게 파싱하고 관리할 것인가?

3. HTTP Headers 이해하기 (2주)
  어떤 헤더가 있고 어떻게 사용할까?

4. HTTP Body 사용하기 (2주)
  Request/Response Body 를 사용하는 경우는?

5. HTTP 파고들기 (2주)
  어떻게 응용할 것인가?

6. HTTP Version 차이 이해하기
  1.0 -> 1.1 -> 2.0 -> 3.0 무엇이 달라졌는가?
