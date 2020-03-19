# HTTP 공부하기

## :trolleybus: 스터디 진행상황
1. :checkered_flag: [2020-02-18-첫번째 걸음](/documents/steps/step1.md)
2. :house: [2020-03-05-두번째 걸음](/documents/steps/step2.md)
3. :zap: [2020-03-12-세번째 걸음](/documents/steps/step3.md)
4. :fearful: [2020-03-19-네번째 걸음](/documents/steps/step4.md)

### :walking: [RFC-2616 (HTTP/1.1) 번역](https://github.com/Study-Java-Together/study-http/blob/master/rfc-2616-HTTP1.1/ko/context.md )

## 스터디

- 방식
  - 과제한 내용을 서로 나누고 다음주 과제 학습하기
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

### :art: 공부 목표
- HttpClient / HttpServer 의 핵심을 이해하자 
- open source 에 PR 을 날려보자

### 더 알아보기
- Java 의 Socket 은 어떻게 동작하는가, In/Out putStream 은 어떻게 사용할까
- HTTP 버전 히스토리 1.0 -> 1.1 -> 2.0 -> 3.0

## 일정

0. 들어가며
<br> 왜 HTTP 를 공부해야 할까? [참고자료](https://www.slideshare.net/deview/d2-campus-http)

1. HttpClient 와 HttpServer 이해하기 (2주)
<br> 직접 Client 와 Server 를 만들어보자 !

2. 오픈소스는 HttpClient 를 어떻게 만들었을까 (2주)
<br> OkHttp 오픈 소스 1.0 버전 분석하기 !
