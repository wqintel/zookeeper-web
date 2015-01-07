package com.zk.read;

import java.util.List;

import org.apache.zookeeper.data.Stat;

import com.github.zkclient.ZkClient;
import com.zk.ZkData;

public class ZkReader {
   // 192.168.161.61:2181,192.168.161.83:2181
   private ZkClient client;

   public boolean exists(String path) {
      if (path == null || path.trim().equals("")) {
         throw new IllegalArgumentException("path can not be null or empty");
      }
      return getClient().exists(path);
   }

   public ZkData readData(String path) {
      ZkData zkdata = new ZkData();
      Stat stat = new Stat();
      zkdata.data = client.readData(getPath(path), stat);
      zkdata.stat = stat;
      return zkdata;
   }

   public List<String> getChildren(String path) {
      return getClient().getChildren(getPath(path));
   }

   public ZkReader(String cxnString) {
      this.client = new ZkClient(cxnString);
   }

   public ZkClient getClient() {
      return client;
   }

   public void setClient(ZkClient client) {
      this.client = client;
   }

   private String getPath(String path) {
      return path == null ? "/" : path.trim();
   }

   public static void main(String[] args) {
      List<String> a = new ZkReader("192.168.161.61:2181,192.168.161.83:2181").getChildren("/tops");
      for (String s : a) {
         System.out.println(s);
      }
   }

}
