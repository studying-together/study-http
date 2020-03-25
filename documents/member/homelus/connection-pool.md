# Connection Pool 

Connection Pool 흔히 데이터베이스에서 쓰이는데 HttpClient 에서도 Connection 재사용으로 효율을 높이기 위해 비슷하게 구현한다.

이 방법은 크게 두가지의 기능을 제공하는데 getConnection 과 releaseConnection 이다.

```java

  // 네트워크 정보를 키로 Connection 을 조회한다.
  Connection getConnection(NetworkInfo networkInfo);
  
  // 다른 요청들을 위해 연결을 해제한다.
  void releaseConnection(Connection connection);
  
```

보통 이러한 Connection 을 관리하기 위해 List 타입의 Connection 목록의 저장소를 가진다.

### 자세하게 알아보기 위해 HttpClient 에서 제공하는 ConnectionPool 관리자인 MultiThreadedHttpConnectionManager 를 참고해 설명한다.

> HttpClient 는 우리가 스프링에서 흔히 사용하는 RestTemplate 에 사용된다.
> RestTemplate 에서 제공하는 옵션들을 살펴보며 ConnectionPool 을 이해해보자








