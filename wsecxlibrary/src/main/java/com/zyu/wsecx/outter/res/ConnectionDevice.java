package com.zyu.wsecx.outter.res;


/***************************************************************************
 * <pre></pre>
 * @文件名称:  Device.java
 * @包   路   径：  cn.org.bjca.wsecx.interfaces
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-16 下午10:07:41
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 ***************************************************************************/
/***************************************************************************
 * <pre></pre>
 * @文件名称:  Device.java
 * @包   路   径：  cn.org.bjca.wsecx.interfaces
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-16 下午10:07:41
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 ***************************************************************************/

public class ConnectionDevice {

	private final static String SPLIT = "#";

	public final static String NULL = "null";

	/**
	 * 厂商标识
	 */
	private String factoryID = null;
	/**
	 * 设备标识
	 */
	private String deviceID = null;
	/**
	 * 容器默认别名
	 */
	private String alias = null;
	/**
	 * 蓝牙连接服务ID
	 */
	private String connectionID = null;
	/**
	 * 蓝牙连接名称
	 */
	private String connectionName = null;
	/**
	 * 蓝牙连接密码
	 */
	private String connectionPassword = null;
	/**
	 * 默认证书
	 */
	private String cert;

	/**
	  * <p>Title: </p>connection
	  * <p>Description: </p>
	  */

	// public ConnectionDevice(String connectionID, String connectionName) {
	// // TODO Auto-generated constructor stub
	// this.connectionID = connectionID;
	// this.connectionName = connectionName;
	// }

	public ConnectionDevice(String factoryID, String connectionID, String connectionName) {
		// TODO Auto-generated constructor stub
		this.connectionID = connectionID;
		this.connectionName = connectionName;
		this.factoryID = factoryID;

	}

	public ConnectionDevice() {
		// TODO Auto-generated constructor stub
		this.connectionID = "";
		this.connectionName = "";
		this.factoryID = "";
		this.deviceID = "";
		this.alias = "";
	}

	/**
	 * @return the connectionPassword
	 */
	public String getConnectionPassword() {
		return connectionPassword;
	}

	/**
	  * @param connectionPassword 要设置的 connectionPassword
	  */

	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	/**
	 * @return the connectionID
	 */
	public String getConnectionID() {
		return connectionID;
	}

	/**
	 * @return the connectionName
	 */
	public String getConnectionName() {
		return connectionName;
	}

	/*
	 * (非 Javadoc) <p>Title: toString</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */

	public static ConnectionDevice getConnectionDevice(String data) {
		// TODO Auto-generated method stub

		String ret = data;

		if (ret.indexOf(SPLIT) == -1) {
			return null;
		}
		String[] devices = ret.split(SPLIT);
		
		if (devices.length != 7) {
			return null;
		}

		ConnectionDevice device = new ConnectionDevice(devices[0], devices[1], devices[2]);
		device.setConnectionPassword(devices[3]);
		device.setDeviceID(devices[4]);
		device.setAlias(devices[5]);
		device.setCert(devices[6]);
		return device;

		// return null;

	}

	/**
	 * @return the factoryID
	 */
	public String getFactoryID() {
		return factoryID;
	}

	/**
	  * @param factoryID 要设置的 factoryID
	  */

	public void setFactoryID(String factoryID) {
		this.factoryID = factoryID;
	}

	/**
	  * @param deviceID 要设置的 deviceID
	  */

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	  * @param alias 要设置的 alias
	  */

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the cert
	 */
	public String getCert() {
		return cert;
	}

	/**
	  * @param cert 要设置的 cert
	  */

	public void setCert(String cert) {
		this.cert = cert;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringBuffer ret = new StringBuffer();
		ret.append(factoryID);
		ret.append(SPLIT);
		ret.append(connectionID);
		ret.append(SPLIT);
		ret.append(connectionName);
		ret.append(SPLIT);
		ret.append(connectionPassword);
		ret.append(SPLIT);
		ret.append(deviceID);
		ret.append(SPLIT);
		ret.append(alias);
		ret.append(SPLIT);
		ret.append(cert);

		return ret.toString();
	}

}
