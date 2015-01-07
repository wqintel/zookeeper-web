package com.zk;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.zookeeper.data.Stat;

public class ZkData {

   public byte[] data;
   public Stat stat;

   public byte[] getBytes() {
      return data;
   }

   @Override
   public String toString() {
      return "ZkData [data=" + Arrays.toString(data) + ",stat=" + stat + "]";
   }

   String getString() {
      return new String(data, Charset.forName("UTF-8"));
   }
}
