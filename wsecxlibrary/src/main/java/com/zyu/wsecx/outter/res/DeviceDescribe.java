package com.zyu.wsecx.outter.res;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  DeviceEntity.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.res
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述: 设备表述实体 
 * @版本: V1.5
 * @创建人： lizhiming
 * @创建时间：2014-8-9 下午4:41:58
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 **************************************************************************
 */
public class DeviceDescribe {
	/**
	 * 厂商唯一标识
	 */
	private String id;
	/**
	 * 厂商描述
	 */
	private String describe;
	/**
	 * 厂商提供的实现类
	 */
	private String provider ;
	
	/**
	 * 
	 */
	private int type;

	/**
	 * 设备连接时的唯一标识
	 */
	private String deviceID ;
	/**
	 * 设备连接时唯一名称标识
	 */
	private String deviceName ;

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	  * @param deviceID 要设置的 deviceID
	  */

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	  * @param deviceName 要设置的 deviceName
	  */

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * @param describe
	 *            the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		return "id:" + id + ",describe:" + describe + "provider:" + provider + "type:" + type;
	}
}
