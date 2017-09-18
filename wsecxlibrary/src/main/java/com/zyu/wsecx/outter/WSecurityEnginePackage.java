package com.zyu.wsecx.outter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.org.bjca.wsecx.interfaces.BJCAWirelessInfo;
import cn.org.bjca.wsecx.interfaces.BJCAWirelessInterface;
import cn.org.bjca.wsecx.interfaces.ConnectionDevice;
import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;
import cn.org.bjca.wsecx.outter.encoder.Base64;
import cn.org.bjca.wsecx.outter.res.WsecxConfig;
import cn.org.bjca.wsecx.outter.util.ByteUtil;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WSecurityEnginePackage.java
 * @包   路   径：  cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:   * @类描述: 移动证书中间件v1.5 
 *          为了便于厂商集成进行安全封装
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-25 下午9:34:44
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
public class WSecurityEnginePackage {

	// 安全引擎接口
	// public static String CONNECT_PASSWORD = "12345678";
	// 安全引擎接口
	public static IWSecurityEngine constainer = null;

	private static Context context;

	private static String faID;

	// 绑定存储信息
	public final static String CONNECTION_INFO = "bjcaInfo";

	// 绑定存储信息分隔符
	public final static String CONNECTION_NULL = "##";

	/**
	 * 电子签章图片
	 */
	public final static String PIC_NAME = "ESEAL";

	/**
	 * 电子签章图片
	 */
	public final static String LASTMODIFY = "LastModify";

	/**
	 * 
	 * <p>
	 * loadWsecxConfig
	 * </p>
	 * 
	 * @Description: 加载中间件配置信息
	 * @param cx
	 *            　ａｎｄｒｏｉｄ上下文
	 * @return　ｘｍｌ的配置文件对象
	 */
	public static WsecxConfig loadWsecxConfig(Context cx) {

		WsecxConfig config = WSecurityEngineDeal.loadWsecxConfig(cx);

		return config;
	}

	/**
	 * 
	  * <p>init</p>
	  * @Description:主要用以蓝牙环境初始化
	  *              首次连接仅仅初始化环境变量
	  *              检查是否绑定过，如果绑定过将连接蓝牙设备
	  * @param cx
	  * @param factoryID
	  * @param refresh 是否重新初始化环境 false为不重新加载
	  * @return
	  * @throws WSecurityEngineException
	 */
	public static int init(Context cx, String factoryID, boolean refresh) throws WSecurityEngineException {

		context = cx;

		faID = factoryID;

		if (refresh) {
			constainer = WSecurityEngineDeal.reloadInstance(factoryID);
		} else {
			constainer = WSecurityEngineDeal.getInstance(factoryID);
		}

		// constainer = WSecurityEngineDeal.getInstance(faID);

		// 初始化环境变量
		int env = constainer.setEnv(cx);

		if (env != BJCAWirelessInterface.SUCESS) {
			throw new WSecurityEngineException(env, "setEnv 环境初始化异常：" + env);
		}

		/**
		* 如果蓝牙在用先断开连接
		*/
		if (constainer.isConnect()) {
			constainer.disconnectDevice();
		}
		int connectBle = BJCAWirelessInterface.SUCESS;

		ConnectionDevice device = null;

		/**
		 * 是否绑定过
		 */
		device = getBinderDevice();
		if (device != null) {
			connectBle = constainer.connectDevice(device.getConnectionID(), null);

			if (connectBle != BJCAWirelessInterface.SUCESS) {
				return connectBle;
			}

			if (connectBle == BJCAWirelessInterface.SUCESS) {
				ConnectionDevice deviceLocal = WSecurityEnginePackage.getBinderDevice();

				if (deviceLocal == null) {
					return BJCAWirelessInfo.ErrorInfo.UNBINDER_EXCEPTION;
				}

				String lastModifyLocal = deviceLocal.getLastModify();

				if (lastModifyLocal == null) {
					lastModifyLocal = "0";
				}

				byte[] lastModifyKey = constainer.loadWSecXDataInterface().readData(WSecurityEnginePackage.LASTMODIFY);

				String lastModify = ByteUtil.byte2Time(lastModifyKey);

				if (!device.getLastModify().equals(lastModify)) {
					return BJCAWirelessInfo.ErrorInfo.BLE_REBINDER_EXCEPTION;
				}

			}

			constainer.setContainerName(device.getAlias());
		}

		return connectBle;
	}

	/**
	 * 
	  * <p>init</p>
	  * @Description:主要用以蓝牙环境初始化
	  *              首次连接仅仅初始化环境变量
	  *              检查是否绑定过，如果绑定过将连接蓝牙设备
	  * @param cx
	  * @param factoryID
	  * @param refresh 是否重新初始化环境 false为不重新加载
	  * @return
	  * @throws WSecurityEngineException
	 */
	/**
	 * 
	  * <p>init</p>
	  * @Description:主要用以蓝牙环境初始化
	  *              首次连接仅仅初始化环境变量
	  *              检查是否绑定过，如果绑定过将连接蓝牙设备
	  * @param cx
	  * @param factoryID
	  * @param refresh 是否重新初始化环境 false为不重新加载
	  * @return
	  * @throws WSecurityEngineException
	 */
	public static int init(Context cx) throws WSecurityEngineException {

		context = cx;

		faID = "SoftWirelessImpl";

		constainer = WSecurityEngineDeal.reloadInstance(faID);

		// constainer = WSecurityEngineDeal.getInstance(faID);

		// 初始化环境变量
		int env = constainer.setEnv(cx);

		if (env != BJCAWirelessInterface.SUCESS) {
			throw new WSecurityEngineException(env, "setEnv 环境初始化异常：" + env);
		}

		return env;
	}

	/**
	 * 
	  * <p>init</p>
	  * @Description:主要用以蓝牙环境初始化
	  *              首次连接仅仅初始化环境变量
	  *              检查是否绑定过，如果绑定过将连接蓝牙设备
	  * @param cx
	  * @param factoryID
	  * @param refresh 是否重新初始化环境 false为不重新加载
	  * @return
	  * @throws WSecurityEngineException
	 */
	public static int reload(Context cx, String factoryID, boolean refresh) throws WSecurityEngineException {

		context = cx;

		faID = factoryID;

		if (refresh) {
			constainer = WSecurityEngineDeal.reloadInstance(factoryID);
		} else {
			constainer = WSecurityEngineDeal.getInstance(factoryID);
		}

		// constainer = WSecurityEngineDeal.getInstance(faID);

		// 初始化环境变量
		int env = constainer.setEnv(cx);

		if (env != BJCAWirelessInterface.SUCESS) {
			throw new WSecurityEngineException(env, "setEnv 环境初始化异常：" + env);
		}

		/**
		* 如果蓝牙在用先断开连接
		*/
		if (!constainer.isConnect()) {
			return BJCAWirelessInterface.FAIL;
		}
		int connectBle = BJCAWirelessInterface.SUCESS;

		ConnectionDevice device = null;

		/**
		 * 是否绑定过
		 */
		device = getBinderDevice();
		if (device != null) {
			// connectBle = constainer.connectDevice(device.getConnectionID(),
			// null);
			//
			//
			// if(connectBle != BJCAWirelessInterface.SUCESS)
			// {
			// return connectBle;
			// }
			//

			if (connectBle == BJCAWirelessInterface.SUCESS) {
				ConnectionDevice deviceLocal = WSecurityEnginePackage.getBinderDevice();

				if (deviceLocal == null) {
					return BJCAWirelessInfo.ErrorInfo.UNBINDER_EXCEPTION;
				}

				String lastModifyLocal = deviceLocal.getLastModify();

				if (lastModifyLocal == null) {
					lastModifyLocal = "0";
				}

				byte[] lastModifyKey = constainer.loadWSecXDataInterface().readData(WSecurityEnginePackage.LASTMODIFY);

				String lastModify = ByteUtil.byte2Time(lastModifyKey);

				if (!device.getLastModify().equals(lastModify)) {
					return BJCAWirelessInfo.ErrorInfo.BLE_REBINDER_EXCEPTION;
				}

			}

			constainer.setContainerName(device.getAlias());
		}

		return connectBle;
	}

	/**
	 * <p>findDevices</p>
	 * @Description:查找蓝牙设备
	 * @return
	 * @throws WSecurityEngineException
	 */
	public static List<ConnectionDevice> findDevices() throws WSecurityEngineException {
		if (context == null || faID == null || constainer == null) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.KEY_UNINIT, "connect没有初始化错误" + context + constainer);
		}
		/** 如果蓝牙在用先断开连接*/
		if (constainer.isConnect()) {
			constainer.disconnectDevice();
		}
		List<ConnectionDevice> allRet = new ArrayList<ConnectionDevice>();
		List<Map<String, String>> blDevices = constainer.findDevices();
		if (blDevices == null) {
			/** 在超时时间内，没有指定的外部介质设备 */
			return allRet;
		}
		for (Map<String, String> device : blDevices) {
			String connectionID = device.get(WSecurityEngineDeal.BLE_SERVICEID);
			String connectionName = device.get(WSecurityEngineDeal.BLE_NAME);
			ConnectionDevice connectionDevices = new ConnectionDevice(faID, connectionID, connectionName);
			allRet.add(connectionDevices);
		}
		if (allRet.size() == 0) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.DEVICE_UNFOUND, "没有找到匹配的移动介质");
		}
		return allRet;
	}

	/**
	  * <p>connect</p>
	  * @Description:主要用以蓝牙连接并绑定选择的绑定设备
	  *              连接后自动绑定该设备
	  * @param con 连接的设备
	  * @param isBind 是否存储绑定信息
	  * @return
	  * @throws WSecurityEngineException
	 */
	public static int connect(ConnectionDevice con, boolean isBind, boolean isPic) throws WSecurityEngineException {

		if (context == null || constainer == null || con == null) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.KEY_UNINIT, "connect没有初始化错误" + context + constainer);
		}

		/**
		* 如果蓝牙在用先断开连接
		*/
		if (constainer.isConnect()) {
			constainer.disconnectDevice();
		}
		int connectBle = BJCAWirelessInterface.FAIL;

		ConnectionDevice device = null;

		/**
		 * 首次绑定
		 */
		device = con;
		long connectBle_st = System.currentTimeMillis();
		connectBle = constainer.connectDevice(device.getConnectionID(), null);
		long connectBle_ed = System.currentTimeMillis();
		// Log.e("connectBle_ed", "connectBle_ed click===" + (connectBle_ed -
		// connectBle_st));

		if (connectBle != BJCAWirelessInterface.SUCESS) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.ENV_UNINIT, "connect移动介质失败：" + connectBle);
		}

		String alias = ConnectionDevice.NULL;
		String deviceID = ConnectionDevice.NULL;
		String cert = ConnectionDevice.NULL;

		// 解绑定
		unBindDevice();

		Vector<String> list = null;

		long getContainerList_st = System.currentTimeMillis();
		list = constainer.getContainerList();
		long getContainerList_ed = System.currentTimeMillis();

		// Log.e("getContainerList_ed", "getContainerList_ed click===" +
		// (getContainerList_ed - getContainerList_st));

		if (list != null && list.size() > 0) {
			alias = (String) list.get(list.size() - 1);
			constainer.setContainerName(alias);
		}

		if (isBind) {
			long en = System.currentTimeMillis();

			deviceID = constainer.getDeviceInfo(BJCAWirelessInfo.DeviceInfo.DEVICEID);

			if (alias != null) {
				long cert_st = System.currentTimeMillis();

				cert = constainer.loadWSecXAppInterface().getCert(alias, BJCAWirelessInterface.KEY_SIGN);

				long cert_ed = System.currentTimeMillis();

				byte[] picBin = null;
				if (isPic) {
					picBin = constainer.loadWSecXDataInterface().readData(PIC_NAME);
				}

				byte[] lastModifyBin = constainer.loadWSecXDataInterface().readData(LASTMODIFY);

				String lastModifyStr = ByteUtil.byte2Time(lastModifyBin);

				String picBase64 = "";

				if (picBin != null) {
					picBase64 = Base64.encode(picBin);
				}

				// 绑定外部介质
				setDevice(faID, deviceID, alias, cert, picBase64, lastModifyStr, con);
			}

			long en1 = System.currentTimeMillis();

			// Log.e("isBind===", (en1 - en) + "");

		}

		return connectBle;
	}

	/**
	 * 
	  * <p>connect</p>
	  * @Description:默认不保存图片
	  * 主要用以蓝牙连接并绑定选择的绑定设备
	  *              连接后自动绑定该设备
	  * @param con 连接的设备
	  * @param isBind 是否存储绑定信息
	  * @return
	  * @throws WSecurityEngineException
	 */
	public static int connect(ConnectionDevice con, boolean isBind) throws WSecurityEngineException {

		return connect(con, isBind, false);
	}

	/**
	 * 
	  * <p>getConstainer</p>
	  * @Description:获取 中间件接口
	  * @return
	  * @throws WSecurityEngineException
	 */

	public static IWSecurityEngine getConstainer() throws WSecurityEngineException {

		if (context == null || constainer == null) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.KEY_UNINIT, "没有初始化错误" + context + constainer);
		}

		return constainer;
	}

	/**
	 * 
	  * <p>getBinderDevice</p>
	  * @Description:得到绑定信息
	  * @return
	  * @throws WSecurityEngineException
	 */

	public static ConnectionDevice getBinderDevice() throws WSecurityEngineException {

		if (context == null) {
			return null;
		}
		SharedPreferences sPreferences = context.getSharedPreferences(CONNECTION_INFO, Context.MODE_PRIVATE);
		String key = sPreferences.getString(CONNECTION_INFO, CONNECTION_NULL);
		// Log.i("getBinderDevice=====", key);
		if (key.equalsIgnoreCase(CONNECTION_NULL)) {
			return null;
		}

		ConnectionDevice connectionDevice = ConnectionDevice.getConnectionDevice(key);

		return connectionDevice;
	}

	/**
	 * 
	 * <p>
	 * getCerts
	 * </p>
	 * 
	 * @Description: 通过检查出的设备，存储绑定信息
	 * @param deviceDescribe
	 *            　介质信息
	 * @param deviceIns
	 *            　中间件接口
	 * @return
	 */
	private static int setDevice(String factoryID, String deviceID, String alias, String cert, String picBase64, String lastModify, ConnectionDevice connectionDevice) {

		if (constainer == null || context == null) {
			return BJCAWirelessInfo.ErrorInfo.ENV_UNINIT;
		}

		SharedPreferences sPreferences = context.getSharedPreferences(CONNECTION_INFO, Context.MODE_PRIVATE);
		Editor editor = sPreferences.edit();

		connectionDevice.setFactoryID(factoryID);
		connectionDevice.setAlias(alias);
		connectionDevice.setCert(cert);
		connectionDevice.setDeviceID(deviceID);
		connectionDevice.setPicBase(picBase64);
		connectionDevice.setLastModify(lastModify);
		editor.putString(CONNECTION_INFO, connectionDevice.toString());

		editor.commit();

		return BJCAWirelessInterface.SUCESS;
	}

	/**
	 * 
	  * <p>unBindDevice</p>
	  * @Description:解绑
	  * @return
	 */
	public static boolean unBindDevice() {

		SharedPreferences sPreferences = context.getSharedPreferences(CONNECTION_INFO, Context.MODE_PRIVATE);
		Editor editor = sPreferences.edit();
		editor.putString(CONNECTION_INFO, CONNECTION_NULL);
		editor.commit();

		return true;
	}

	/**
	 * <p>encryptPin</p>
	 * @Description:			JNI加密软证书
	 * @param deviceID
	 * @param pin
	 * @return
	 */
	public static byte[] encryptPin(String deviceID, String pin) {
		return constainer.encryptPin(deviceID, pin);
	}

	/**
	 * <p>decryptPin</p>
	 * @Description:			JNI解密软证书
	 * @param deviceID
	 * @param enpin
	 * @return
	 */
	public static byte[] decryptPin(String deviceID, byte[] enpin) {
		return constainer.decryptPin(deviceID, enpin);
	}
}
