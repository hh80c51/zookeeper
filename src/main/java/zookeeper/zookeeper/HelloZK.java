package zookeeper.zookeeper;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooKeeper;

public class HelloZK {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(HelloZK.class);
	//实例变量
	private static final String CONNECTSTRING = "192.168.174.133:2181";
	private static final String PATH = "/atguigu";
	private static final int SESSION_TIMEOUT = 50*1000;
	
	public ZooKeeper startZK() throws IOException {
		return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
			
			@Override
			public void process(WatchedEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void stopZK(ZooKeeper zk) throws InterruptedException{
		if(null != zk){
			zk.close();
		}
	}
	
	public void createZNode(ZooKeeper zk,String nodePath,String nodeValue) throws KeeperException, InterruptedException{
		zk.create(nodePath, nodeValue.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	public String getZNode(ZooKeeper zk, String nodePath) throws KeeperException, InterruptedException{
		
		String retValue = null;
		
		byte[] byteArray = zk.getData(nodePath, false, new Stat());
		retValue = new String(byteArray);
		
		return retValue;
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		HelloZK hello = new HelloZK();
		
		ZooKeeper zk = hello.startZK();
		
		if(zk.exists(PATH, false) == null){
			hello.createZNode(zk, PATH, "hello1228");
			
			String retValue = hello.getZNode(zk, PATH);
			
			if (logger.isInfoEnabled()) {
				logger.info("main(String[]) - String retValue=" + retValue);
			}
			
		}else {
			if (logger.isInfoEnabled()) {
				logger.info("I have this node!");
			}
		}
		hello.stopZK(zk);
	}
	
}
