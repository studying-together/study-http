### okHttp 와 redirect 🙂

서버에서 ssl 설정을 하는 경우, `http`로 요청하는 경우 `https`로 redirect 시켜준다.
okHttp는 redirect를 어떻게 처리할까 ?

#### redirect의 전반적인 프로세스

- http 요청 ("http://publicobject.com/helloworld.txt")
    - httpEngine 생성
    - request 를 보내고, 응답 헤더를 받는다.
- 응답 헤더 확인
    ![](./image/response-headers.PNG)
- Retry 여부 확인
    - MAX_REDIRECT = 20
- https 요청 ("https://publicobject.com/helloworld.txt")
    - httpsEngine 생성
    - request 보내고, 응답 헤더를 받음
- Retry = Retry.NONE