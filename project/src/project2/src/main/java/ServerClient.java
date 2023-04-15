import com.google.gson.JsonObject;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient {
    public static void main(String[] args) {
        Thread thread=new Thread(() ->{
            try(
                    ServerSocket serverSocket = new ServerSocket(8080);
                     Socket socket = serverSocket.accept();
                     DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                     BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in))
            ){
                 //接受数据
                     while(true){
                          String s = in.readUTF();
                          //解码

//                         JSONObject jsonObject = new JSONObject();
                         System.out.println("服务端接受的数据："+s);
                         //发送数据
                          String s1 = buffer.readLine();
                          if(s1.equals("bye")){
                              break;
                          }
                      JSONObject   jsonObject = new JSONObject();
                         jsonObject.put("message",s1);
                         jsonObject.put("userid",8080);
                         jsonObject.put("time","20:57");
                         jsonObject.put("name","服务端");
                         out.writeUTF(jsonObject.toString());
                         out.flush();
                     }
                System.out.println("结束");

            }catch (Exception e){
                System.out.println("出问题了");
            };
        });
        thread.start();

    }
}
