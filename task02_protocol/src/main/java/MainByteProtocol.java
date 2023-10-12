import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * byte[0] - версия протокола (1)
 * byte[1-4] - тип сообщения (txt , xml , json, mp3 , jpeg)
 * byte[5-8] - размер сообщения
 * byte[9 ...] - тело сообщения
 */


public class MainByteProtocol {

    public static void send(OutputStream os, String message) throws IOException {
        byte vers = 1;
        os.write(vers);

        String messageType = "txt ";
        os.write(messageType.getBytes("UTF8"));

        byte[] msgBytes = message.getBytes("UTF8");
        int msgLength = msgBytes.length;

        os.write(intToByteArray(msgLength));
        os.write(msgBytes);
        os.flush();
    }

    public static void read(InputStream is) throws IOException{

        int version = is.read();
        System.out.println("version: " + version);

        byte[] msgTypeBuf = new byte[4];
        is.read(msgTypeBuf);

        String msgType = new String(msgTypeBuf);
        System.out.println("message type: " + msgType);

        byte[] msgLengthBuf = new byte[4];
        is.read(msgLengthBuf);

        int msgLength = byteArrayToInt(msgLengthBuf);
        System.out.println("message length: " + msgLength);

        byte[] msgBuf = new byte[msgLength];
        is.read(msgBuf);
        System.out.println("message: " + new String(msgBuf, "UTF8"));
    }

    public static byte[] intToByteArray(int x) {
        byte[] result = new byte[4];

        // 00010111 10110000 11001000 10100001
        // 00000000 00000000 00000000 11111111 = 0xFF
        //                            10100001
        result[0] = (byte)((x >> 24) & 0xFF);
        result[1] = (byte)((x >> 16) & 0xFF);
        result[2] = (byte)((x >> 8) & 0xFF);
        result[3] = (byte)((x) & 0xFF);

        return result;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int result;

        // 00010111 10110000 11001000 10100001
        // 00010111 00000000 00000000 00000000
        //          10110000 00000000 00000000
        //                   11001000 00000000
        //                            10100001

        result = bytes[0] << 24;
        result = (bytes[1] << 16) | result;
        result = (bytes[2] << 8) | result;
        result = (bytes[3]) | result;
        return result;
    }
}
