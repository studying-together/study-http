# OK HTTP 1.0 오픈소스  분석

![OkHttp Architecture](https://github.com/Study-Java-Together/study-http/blob/master/documents/member/sungminhong/image/okhttp-logic.png)

## 1. SoketOutputStream을 통해 데이터를 쓰는 시점은 언제일까요?

socket write가 실행되는 지점이 어디인지 찾기 위해 디버깅을 해봤습니다. (쉽지 않습니다. 타임아웃이 계속 발생하기 때문에..)
결론 부터 말하자면 실제 write 시점은 readResponse()가 호출될 때 일수 도 있고 그 전일 수도 있습니다.
실제 구현을 살펴보다 보면 [네이글 알고리즘](https://en.wikipedia.org/wiki/Nagle%27s_algorithm)이 구현된 사실을 알 수 있습니다.
추가적으로 아래와 같이 socket 객체를 setNoDealy 메서드를 사용해 초기화 않으면 네이글 알고리즘이 동작하게 됩니다. 

~~~java
socket.setNoDelay(true);
~~~

setNoDealy가 설정하는 SocketOptions는 [TCP_NODELAY](https://docs.oracle.com/javase/8/docs/api/java/net/SocketOptions.html#TCP_NODELAY)입니다.

ok-http에서도 또한 소켓을 생성할 경우 추가 적으로 setNoDealy를 통한 설정을 하지 않으므로 네이글 알고리즘이 동작합니다. (실시간성이 중요한 통신을 할 경우 이 옵션을 true 설정하면 됩니다. Ex: FPS 게임)
그리고 native를 통해 받아온 [MTU](https://ko.wikipedia.org/wiki/%EC%B5%9C%EB%8C%80_%EC%A0%84%EC%86%A1_%EB%8B%A8%EC%9C%84)(maximum transmission unit: 최대 전송 단위) 값으로 버퍼 사이즈를 설정하게 됩니다.

### ok-http에서 어떻게 동작할까요?
테스트를 위해 output을 보내도록 해야합니다. 아래 예시와 같이 설정하면 됩니다. POST 메서드로 요청할 서버로 tcpschool을 사용했습니다.
~~~java
        String param = "city=Seoul&zipcode=06141";
        HttpURLConnection connection = client.open(new URL("http://tcpschool.com/examples/media/request_ajax.php"));
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setFixedLengthStreamingMode(param.getBytes().length);  //이렇게 fixed 해줘도 되고 안 해줘도 괜찮습니다.
        OutputStream outputStream = connection.getOutputStream();         //여기서 outputStream을 가져오며 소켓 생성과 연결이 이루어졌습니다.
        outputStream.write(param.getBytes()); //여기서 write 요청을 하고 네이글 알고리즘에 맞게 동작합니다.
~~~

1. MTU가 100인 경우
    1. 보내는 header, body 데이터를 다 합쳤는데 byte 길이가 99인 경우
        1. 데이터를 다 넣었는데 버퍼 사이즈를 넘기지 않았습니다. 계속 넣어두고 기다립니다.
        2. readResponse() 내부 로직에서 flush 하는 로직이 있습니다. 여기서 socketInputStream으로 보내게 됩니다.
    2. 보내는 header는 100이고 body가 100인 경우
        1. header 데이터를 socketInputStream으로 보내게 됩니다.
        2. 이후 body 데이터를 write하는 요청을 하는 경우 socketInputStream으로 보내게 됩니다.
        3. readResponse() 때 flush를 하려고 해도 남아 있는 데이터가 없습니다.
    3. 보내는 header는 99이고 body가 100인 경우
        1. 버퍼 크기를 넘지 않았으니 header 데이터를 버퍼에 넣어두고 기다립니다.
        2. 바디 write 요청을 하는 경우 버퍼크기를 넘기면서 header를 먼저 socketInputStream으로 보내고 body 데이터를 바로 이어서 보냅니다.
        3. readResponse() 때 flush를 하려고 해도 남아 있는 데이터가 없습니다.

[BufferedOutputStream](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedOutputStream.html)의 flush 메서드를 호출하면 현재 버퍼에 있는 데이터를 wrtie하고 out.flush() 요청에 의해 SocketOutputStream구현체 부모인 추상클래스 OutputStream의 flush()가 호출됩니다. 그리고 내부 구현을 보면 아무런 구현이 없습니다. 이 말은 즉 네이글 알고리즘에 의존하기 때문에 flush를 딱히 재구현할 필요가 없다는 것과 같은 의미입니다.

~~~java
    //BufferedOutputStream.flushBuffer()
    /** Flush the internal buffer */
    private void flushBuffer() throws IOException {
      if (count > 0) {
        out.write(buf, 0, count);
        count = 0;
      }
    }
~~~

~~~java
     //OutputStream의.flush()
     /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out. The general contract of <code>flush</code> is
     * that calling it is an indication that, if any bytes previously
     * written have been buffered by the implementation of the output
     * stream, such bytes should immediately be written to their
     * intended destination.
     * <p>
     * If the intended destination of this stream is an abstraction provided by
     * the underlying operating system, for example a file, then flushing the
     * stream guarantees only that bytes previously written to the stream are
     * passed to the operating system for writing; it does not guarantee that
     * they are actually written to a physical device such as a disk drive.
     * <p>
     * The <code>flush</code> method of <code>OutputStream</code> does nothing.
     *
     * @exception  IOException  if an I/O error occurs.
     */
    public void flush() throws IOException {
    }
~~~

## 2. HTTPS는 어떻게 구현됐을까요?

## 3. ok-http 에서는 컨넥션풀을 사용 못하는 경우가 발생할까요? 
