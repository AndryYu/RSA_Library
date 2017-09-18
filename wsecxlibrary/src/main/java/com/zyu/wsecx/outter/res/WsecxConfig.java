package com.zyu.wsecx.outter.res;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WsecxConfig.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.res
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  解析配置文件xml
 * @版本: V1.5
 * @创建人： lizhiming
 * @创建时间：2014-8-9 下午4:38:58
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
        2014/8/12         |        liyade         |      重构                     |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 **************************************************************************
 */
public final class WsecxConfig {

	/**
	 * 默认基本算法
	 */
	private DefaultAlg defaultAlg;

	/**
	 * 厂商集合
	 */
	private Map<String, DeviceDescribe> deviceMap;
	/**
	 * 厂商标识集合
	 */
	private Map<String, String> idMap;

	/**
	 * 默认选择的厂商标识
	 */
	private String defaultDeviceID;

	public WsecxConfig() {
		// TODO Auto-generated constructor stub

		defaultAlg = new DefaultAlg();
		idMap = new HashMap<String, String>();
		deviceMap = new HashMap<String, DeviceDescribe>();

	}

	/**
	 * @return the defaults
	 */
	public DefaultAlg getDefaultAlg() {
		return defaultAlg;
	}

	/**
	 * @param defaultAlg
	 *            the defaults to set
	 */
	public void setDefaultAlg(DefaultAlg defaultAlg) {
		this.defaultAlg = defaultAlg;
	}

	/**
	 * @param deviceMap
	 *            the devices to set
	 */
	public void addDeviceDescribe(DeviceDescribe device) {
		this.deviceMap.put(device.getId(), device);
	}

	public DeviceDescribe getDeviceDescribe(String id) {
		return (DeviceDescribe) this.deviceMap.get(id);
	}

	public Collection<DeviceDescribe> getDeviceList() {
		return this.deviceMap.values();
	}

	/**
	 * @param idMap
	 *            the ids to set
	 */
	public void addID(String id) {
		this.idMap.put(id, id);
	}

	public String getID(String id) {
		return this.idMap.get(id);
	}

	/**
	 * @return the defaultDevice
	 */
	public String getDefaultDeviceID() {
		return defaultDeviceID;
	}

	/**
	  * @param defaultDevice 要设置的 defaultDevice
	  */

	public void setDefaultDeviceID(String id) {
		this.defaultDeviceID = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDefaultAlg().getAsymm() + "***" + this.idMap.keySet() + "***" + this.deviceMap.keySet();
	}

}
