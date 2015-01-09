package com.zk.read;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.zkclient.ZkClient;
import com.zk.ZkData;

public class ZkReader {
   private static final Logger LOGGER = LoggerFactory.getLogger(ZkReader.class);
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
      zkdata.setData(client.readData(getPath(path), stat));
      zkdata.setStat(stat);
      return zkdata;
   }

   public List<String> getChildren(String path) {
      return getClient().getChildren(getPath(path));
   }

   public ZkReader(String cxnString) {
      LOGGER.info("cxnString:{}", cxnString);
      this.client = new ZkClient(cxnString);
   }

   public ZkClient getClient() {
      return client;
   }

   public void setClient(ZkClient client) {
      this.client = client;
   }

   private String getPath(String path) {
      path = path == null ? "/" : path.trim();
      if(!StringUtils.startsWith(path, "/")) {
         path = "/" + path;
      }
      return path;
   }

}
