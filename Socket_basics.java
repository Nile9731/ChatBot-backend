import java.net.InetAddress;
import java.net.UnknownHostException;

public class Socket_basics {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress=InetAddress.getByName("time-a.nist.gov");
        byte[] addressBytes=inetAddress.getAddress();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : addressBytes) {
            // Convert the byte value to an unsigned integer
            int unsignedByte = b & 0xFF;
            stringBuilder.append(unsignedByte).append(".");
        }

        // Remove the trailing dot and print the IP address
        String ipAddress = stringBuilder.substring(0, stringBuilder.length()-1 );
        System.out.println(ipAddress+ " "+ipAddress.length());
        //384

    }
}
