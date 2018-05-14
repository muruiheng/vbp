package com.sgai.vbp.config.client.watcher;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.sgai.vbp.config.zookeeper.VbpZookeeper;
import com.sgai.vbp.log.Logger;
import com.sgai.vbp.util.AssertUtil;

public class VbpConfigWatcher {

	private Logger logger = Logger.getLogger(VbpConfigWatcher.class);
	private VbpZookeeper zookeeper;
	
	
	public static void build(String zkAddress, String...paths) {
		VbpConfigWatcher watcher = new VbpConfigWatcher();
		watcher.zookeeper = new VbpZookeeper(zkAddress);
		watcher.init(paths);
	}

	public void init (String... paths) {
		CountDownLatch latch = new CountDownLatch(1);
		new Runnable() {
			public void run() {
				if (!AssertUtil.isVal(paths)) {
					return ;
				}
				ZooKeeper zk = zookeeper.connect();
		        zk.register(new Watcher() {
		        	 
		            @Override
		            public void process(WatchedEvent event) {
		            	switch (event.getState()) {
						case Disconnected:
							init(paths);
							break;
						case Expired:
							init(paths);
							break;
						default:
							break;
						}
		            }
		        });
				for (String path : paths) {
					watchConfig(zk, path);
				}
				try {
					latch.await();
				} catch (InterruptedException e) {
					logger.error("the zk config watcher init failed!", e);
				}
			}
			
		}.run();
		
	}
	
	public void watchConfig(ZooKeeper zk, String dataPath) {
		try {
			zk.getChildren(dataPath, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					switch (event.getType()) {
					case NodeChildrenChanged:
						updateConfig(event);
						break;
					case NodeDataChanged:
						updateConfig(event);
						break;
					case NodeCreated:
						updateConfig(event);
						break;
					case NodeDeleted:
						updateConfig(event);
						break;
					default:
						break;
					}

				}
			});
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateConfig(WatchedEvent event) {
		
	}
}
