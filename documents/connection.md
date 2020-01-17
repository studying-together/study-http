# Connection

HTTP Client 에서 Connection 의 역할은?

여러 HTTP request/response 교환을 위해 사용될 HTTP, HTTPS 커넥션에 대한 socket 과 streams 을 가지고 있다.
커넥션은 직접 서버에 접근하거나 프록시를 통해 접근한다.

HTTP Client 에 의해 인스턴스가 자동적으로 생성되고 연결되고 실행된다.
Application 들은 ConnectionPool 의 일부로 HTTP connection 들을 관찰하는데 사용됩니다.


