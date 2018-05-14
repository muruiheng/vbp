package com.sgai.vbp.config.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgai.vbp.config.client.Constants;
import com.sgai.vbp.config.exception.ConfigException;
import com.sgai.vbp.util.AssertUtil;


/**
 * zk config op
 * @author mrh
 */
public class VbpZookeeper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VbpZookeeper.class);

	private CountDownLatch latch = new CountDownLatch(1);

	private String zkAddress;
	

	public VbpZookeeper(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	/**
	 * to connect zookeeper server and countDown all process to wait on;
	 * @return
	 */
	public ZooKeeper connect() {
		ZooKeeper zk = null;
		try {
			LOGGER.info("connect Server....");
			zk = new ZooKeeper(zkAddress, Constants.ZK_SESSION_TIMEOUT, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					if (event.getState() == Event.KeeperState.SyncConnected) {
						LOGGER.info("connectServer....connected!");
						latch.countDown();
					}
				}
			});
			latch.await();
		} catch (IOException e){
			LOGGER.error("", e);
		} catch (InterruptedException e) {
			LOGGER.error("", e);
		}
		return zk;
	}
	
	public ZooKeeper reConnect() {
		return this.connect();
	}

	/**
	 * 
	 * @param zk
	 * @param data
	 */
	public void createNode(ZooKeeper zk, String data, String dataPth) {
		try {
			Stat exists = zk.exists(Constants.ZK_CONFIG_PATH, false);
			if (!AssertUtil.isVal(exists)) {
				String[] paths = Constants.ZK_CONFIG_PATH.split(Constants.ZK_SPLITOR);
				StringBuffer zkPath = new StringBuffer(Constants.ZK_SPLITOR);
				for (String path : paths) {
					if (AssertUtil.isVal(path)) {
						zkPath.append(path);
						zk.create(zkPath.toString(), zkPath.toString().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
						zkPath.append(Constants.ZK_SPLITOR);
					}
				}
			}
			
			if (!dataPth.startsWith(Constants.ZK_CONFIG_PATH)) {
				throw new ConfigException("the data path must be start with " + Constants.ZK_CONFIG_PATH);
			}
			
			zk.create(dataPth, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			LOGGER.info("------------" + exists);
		} catch (KeeperException e) {
			LOGGER.error("createNode failed", e);
		} catch (InterruptedException e) {
			LOGGER.error("createNode failed", e);
		}
			
	}
}
