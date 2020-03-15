package sjt.http.client.clone;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface Dns {

    Dns DEFAULT = InetAddress::getAllByName;

    InetAddress[] getAllByName(String host) throws UnknownHostException;

}
