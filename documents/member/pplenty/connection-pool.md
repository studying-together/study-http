# ok-http connection pool

### pool 생성 시점 
클라이언트 객체 생성 후 open method 가 호출될 때  생성된다.

### 11
RouterSelector.next() 에서 pool.get()