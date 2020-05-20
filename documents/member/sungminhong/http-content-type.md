# 14.18 Content-Type
Content-Type entity-header 필드는 수신측에 발송한 entity-body 의 media type 을 표시하거나, 요구가 GET 이었으면 발송되었을 media type 을 표시한다.
<br/>
<br/>
&nbsp; &nbsp; &nbsp; Content-Type = "Content-Type" ":" media-type
<br/>
<br/>
Media types 은 [section 3.7](https://tools.ietf.org/html/rfc2616#section-3.7)에 규정되어 있으며 이 필드의 사용 예는 다음과 같다. 
<br/>
<br/>
&nbsp; &nbsp; &nbsp; Content-Type: text/html; charset=ISO-8859-4
<br/>
<br/>
엔터티의 media type 을 식별하는 method 에 관한 토의는 [section 7.2.1](https://tools.ietf.org/html/rfc2616#section-7.2.1) 에 기술되어 있다.

# Content-Type에 대해 고민 했던 부분
- text/html; charset=utf-8
  - 인코딩 방법이 charset에 정의된다.
  - 하지만 현재 team2 httpClient 프로젝트에는 데이터를 String 으로 반환 받는다.
    - 디버깅을 통해 알아본 결과 InputStreamReader 생성시 생성자에 charset 파람을 넘겨주지 않으면 디폴트로 utf-8 사용했다.
    - 우리 프로젝트에서는 생성자에 파람을 넘기지 않아 utf-8로 모두 디코딩하고 있었다.
  - 정확한 인코딩을 위해 body데이터를 "byte[]" 자료형에서 String으로 변환 후 그 값을 다시 인코딩된 "byte[]"로 송신해야 할 것 같다.
  - 정확한 디코딩을 위해 body데이터를 "byte[]" 자료형으로 받고 Content-Type 헤더 값에 존재하는 charset을 통해 다시 디코딩해야할 것 같다.
- multipart/form-data; boundary=something
  - 메시지 및 파일을 전송할 때 너무 큰 경우 여러 패킷으로 나눠서 보내야 한다.
  - 이를 위해 Content-Type에 multipart/form-data로 지정하고 boundary를 넣어줘야 한다.
  - boundary에 지정되어 있는 문자열을 이용하여 전송되는 파일 데이터의 구분자를 사용해야 한다. 
  - 예시는 아래에 명시했다.
  
  ~~~
  POST /foo HTTP/1.1
  Content-Length: 68137
  Content-Type: multipart/form-data; boundary=---------------------------974767299852498929531610575
  Content-Disposition: form-data; name="description"
  ---------------------------974767299852498929531610575
  
  some text
  
  ---------------------------974767299852498929531610575
  Content-Disposition: form-data; name="myFile"; filename="foo.txt" 
  Content-Type: text/plain 
  
  (content of the uploaded file foo.txt)
  
  ---------------------------974767299852498929531610575--
  ~~~
  출처: https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Content-Type
  
