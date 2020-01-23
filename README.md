# HTTP 공부하기

### 공부 방법
- HTTP Client/Server 구현해보기
- HTTP Client open source 를 분석하기 ([참고](https://github.com/square/okhttp/))

### 공부 목적
- HttpClient(브라우저) 에서 [**ConnectionPool** 은 어떻게 동작할까](/documents/connection_pool.md)
- 실제로 어떻게 [**Connection** 이 맺어질까](/documents/connection.md)
- HTTP 1.0 / 1.1 / 2.0 은 어떻게 구현해야 할까

### 공부 목표
- 간단한 HttpClient 를 만들어보자
- open source 에 PR 을 날려보자

### 더 알아보기
- HTTP 버전 히스토리 1.0 -> 1.1 -> 2.0 -> 3.0

## 시작하기
- main 메서드를 이용해서 실행하자

### HTTP Server 를 만들어보자
- ServerSocket을 이용해 이벤트를 감시하고 요청이 왔을 때 소켓을 얻어 ThreadPool 로 처리하자.

### HTTP Client 를 만들어보자
- Socket 을 이용해 접속해보자
