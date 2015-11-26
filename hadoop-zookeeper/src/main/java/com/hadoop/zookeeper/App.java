package com.hadoop.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Hello world!
 *
 */
public class App {
	private static final Logger logger = LogManager.getLogger(App.class);
//	private static final String connectString = "123.57.77.89:2181,123.57.77.89:2182,123.57.77.89:2183";
	private static final String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private static final int sessionTimeout = 5*60*1000;
	public static void main( String[] args ) throws IOException, KeeperException, InterruptedException {
		generalTest();
      
      
    }
	
	public static void generalTest() throws IOException, KeeperException, InterruptedException {
		//添加watch，当Znode status changed( add、update、delete)监听通知(该通知仅能收到一次提醒)，并打印
		ZooKeeper zk = new ZooKeeper(connectString, sessionTimeout,(event)->{logger.debug("watch:"+event);});
		String createResult= zk.create("/zk_test", "junk".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		logger.debug("create result:{}",createResult);
		String createChildResult= zk.create("/zk_test/child", "child".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		logger.debug("create child result:{}",createChildResult);
		List<String> children = zk.getChildren("/zk_test", true);
		if(children!=null){
			children.forEach((child)->{ logger.debug("get children:{}",child);});
		}else{
			logger.debug("get children:{}",children);
		}
		byte[] child = zk.getData("/zk_test/child", true, null);
		logger.debug("get child data:{}",new String(child));
		Stat updateStat = zk.setData("/zk_test/child", "updated".getBytes(), -1);
		logger.debug("update child result:{}",updateStat);
		Stat updateStat2 = zk.setData("/zk_test/child", "second updated".getBytes(), -1);
		logger.debug("update child result:{}",updateStat2);
		byte[] updatedChild = zk.getData("/zk_test/child", true, null);
		logger.debug("get child data:{}",new String(updatedChild));
		zk.delete("/zk_test/child", -1);
		zk.delete("/zk_test", -1);
		zk.close();
	}
	
	
	
}
