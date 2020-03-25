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

저장소는 전역에서 사용되는 ConnectionPool 그리고 그 내부에서 Host 별로 사용되는 HostConnectionPool 이 존재한다.<br>
(Map 으로 사용한다.)

Network 정보가 들어오면 HostConnectionPool 에서 Connection 을 조회한다. 없다면 새로운 Connection 을 만든다.
언제 Connection 이 Pool 에 들어가게 될까? <br>
ResponseBody 가 모두 처리된 이후에 ConnectionPool 에 진입하게 된다.

만약 ConnectionPool 내 HostConnectionPool 이 모두 사용 중이고 (최대로 만들 수 있는 커넥션을 모두 사용중이라면)
해당 풀을 Object 의 wait 메서드를 이용해 blocking 하고 작업중이던 Connection 이 반환되면 interrupt 메서드를 통해 재사용하게 도와준다.

```text
maxHostConnections : Host 내에 사용할 수 있는 최대 커넥션 수
maxTotalConnections : 전체에서 사용할 수 있는 최대 커넥션 수
```







