package com.zk.test.o;

import java.io.IOException;
import java.net.ServerSocket;

public class PortUtils {

   public static int checkAvailablePort(int port) {
       while (port < 65500) {
           ServerSocket serverSocket = null;
           try {
               serverSocket = new ServerSocket(port);
               return port;
           } catch (IOException e) {
               //ignore error
           } finally {
               try {
                   if (serverSocket != null)
                       serverSocket.close();
               } catch (IOException e) {
                   //ignore
               }
           }
           port++;
       }
       throw new RuntimeException("no available port");
   }

   public static void main(String[] args) {
       int port = checkAvailablePort(80);
       System.out.println("The available port is " + port);
   }
}
