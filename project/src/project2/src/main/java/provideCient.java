import com.google.gson.Gson;
import net.sf.json.JSONObject;

import javax.json.JsonObject;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class provideCient {
    public static void main(String[] args) {
        Thread thread=new Thread(
                ()->{
                    try(//先创建客户端的socket
                            Socket socket = new Socket("127.0.0.1",8080);
                         DataInputStream in = new DataInputStream(socket.getInputStream());
                         DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                      BufferedReader data=  new BufferedReader(new InputStreamReader(System.in))
                    ){
                     //发送数据
                        while (true){
                            String s = data.readLine();
                            //然后进行编码
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("message",s);
                            jsonObject.put("userid",9090);
                            jsonObject.put("time","20:33");
                            jsonObject.put("name","客户端");

                            if(s.equals("bye")){
                                break;
                            }
                            //然后发送
                            out.writeUTF(jsonObject.toString());
                            out.flush();
                            //接受数据
                             String s1 = in.readUTF();
//                              jsonObject = new JSONObject(s1);
                            System.out.println("客户端接收的数据："+s1);
                        }
                    }catch (Exception e) {
                        System.out.println("出错了");
                  }
                    System.out.println("结束");
                }
        );
        thread.start();
    }
}
