package zookeeper.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

public class WatchChildNode {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(WatchChildNode.class);
	
	//定义常量
	   private static final String CONNECTSTRING = "192.168.174.133:2181";
	   private static final String PATH = "/atguigu";
	   private static final int SESSION_TIMEOUT = 50*1000;
	   //定义实例变量
	   private ZooKeeper zk = null;
	   
	   //以下为业务方法
	   public ZooKeeper startZK() throws IOException
	   {
	       return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
	          @Override
	          public void process(WatchedEvent event)
	          {
//	              if(event.getType() == EventType.NodeChildrenChanged && event.getPath().equals(PATH))
//	              {
//	                 showChildLists(PATH);
//	              }else{
//	                 showChildLists(PATH);
//	              }
	        	  showChildLists(PATH);
	          }
	       });
	   }
	   
	   public void stopZK() throws InterruptedException
	   {
	       if(zk != null)
	       {
	          zk.close();
	       }
	   }
	   
	   public void showChildLists(String path)
	   {
	       List<String> list = null;
	       try 
	       {
	          list = zk.getChildren(PATH, true);
	       }catch (KeeperException | InterruptedException e) {
	          e.printStackTrace();
	       }
	       System.out.println("**********showChildLists: "+list);
	   }
	 
	   public static void main(String[] args) throws IOException, KeeperException, InterruptedException
	   {
	       WatchChildNode watch = new WatchChildNode();
	       
	       watch.setZk(watch.startZK());
	       
	       Thread.sleep(Long.MAX_VALUE);
	   }
	 
	   //setter---getter
	   public ZooKeeper getZk()
	   {
	       return zk;
	   }
	   public void setZk(ZooKeeper zk)
	   {
	       this.zk = zk;
	   }
}
