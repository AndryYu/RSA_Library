/**   
 * @Title: WSecXContainer.java 
 * @Package cn.org.bjca.wsecx 
 * @Description: 用户对外密钥容器
 * @author liyade
 * @date 2014-1-27 1:23:33 
 * @version V1.0   
 */
package com.zyu.wsecx.outter;

import android.content.Context;
import android.util.Base64;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.org.bjca.wsecx.core.impl.WSecurityEngine;
import cn.org.bjca.wsecx.core.jni.JNISoftWirelessImpl;
import cn.org.bjca.wsecx.interfaces.BJCABLEWirelessInterface;
import cn.org.bjca.wsecx.interfaces.BJCAWirelessInfo;
import cn.org.bjca.wsecx.interfaces.BJCAWirelessInterface;
import cn.org.bjca.wsecx.interfaces.BluetoothKeyListener;
import cn.org.bjca.wsecx.interfaces.ConnectionDevice;
import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;
import cn.org.bjca.wsecx.outter.res.ContainerConfig;
import cn.org.bjca.wsecx.outter.res.DeviceDescribe;
import cn.org.bjca.wsecx.outter.res.WsecxConfig;
import cn.org.bjca.wsecx.outter.res.WsecxConfigParse;
import cn.org.bjca.wsecx.soft.SoftWirelessImpl;

/**
 * *************************************************************************
 * 
 * <pre></pre>
 * 
 * @文件名称: WSecurityEngineDeal.java
 * @包 路 径： cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 * 
 * @类描述: * 单例实现密钥容器抽象类，实现的方法不需要登录便可调用
 * 
 *       配置文件加载
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-12 下午2:53:19
 * 
 * 
 * 
 * @修改记录： 
 *        -----------------------------------------------------------------------------------------------
 *        		时间 		| 		修改人 		| 		修改的方法 		| 		修改描述
 *        -----------------------------------------------------------------------------------------------
 *        					| 					| 						|
 *        -----------------------------------------------------------------------------------------------
 * 
 ************************************************************************** 
 */
public class WSecurityEngineDeal extends WSecurityEngine {

	/**
	 * KEY容器 配置文件名称, 在assets目录下
	 */
	private final static String PROCONFIG_XML = "BJCAWSecx.xml";

	/**
	 * 蓝牙服务ID
	 */
	public final static String BLE_SERVICEID = "ServiceID";

	/**
	 * 蓝牙设备名称
	 */
	public final static String BLE_NAME = "NAME";

	/**
	 * 蓝牙设备连接异或初始值
	 */
	public final static String BLE_CONNECTION_XOR = "BJCABLEWSE201408";

	/**
	 * 保存最新的证书密码卡号
	 */
	public final static String BJCA_KEY_SN = "BJCAENVSN";

	/**
	 * 单实例
	 */
	private static WSecurityEngineDeal instance = null;

	/**
	 * 厂商实现标识
	 */
	private String deviceFlag = null;

	/**
	 * 移动上下文
	 */
	private Context context;

	/**
	 * 
	 * 
	 * 
	 * @Title: WSecurityEngineDeal
	 * @Description: 获取WSecurityEngineDeal单实例
	 * @param @param implName key实现标识
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecurityEngineDeal
	 * @throws
	 */

	public static IWSecurityEngine getInstance(String implName) throws WSecurityEngineException {

		if (implName == null) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INPUT_PARAM_NULL_INVAILD, "implName 参数为空");
		}

		if (instance == null) {
			synchronized (WSecurityEngineDeal.class) {
				if (instance == null) {
					instance = new WSecurityEngineDeal(implName);
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * <p>
	 * loadWsecxConfig
	 * </p>
	 * 
	 * @Description:加载配置信息
	 * @param context
	 *            系统上下文
	 * @return
	 * @throws WSecurityEngineException
	 */
	public static WsecxConfig loadWsecxConfig(Context context) throws WSecurityEngineException {

		WSecurityEngineDeal intTmp = new WSecurityEngineDeal(null);

		intTmp.context = context;

		intTmp.loadContext();

		return intTmp.getContainerConfig().getWsecxConfig();
	}

	/**
	 * 
	 * 
	 * @Title: WSecurityEngineDeal
	 * @Description: 配置文件发生变化时重载实例
	 * @param @param implName 设备名称
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecurityEngineDeal实例
	 * @throws
	 */

	public static IWSecurityEngine reloadInstance(String implName) throws WSecurityEngineException {
		instance = null;

		return getInstance(implName);
	}

	private WSecurityEngineDeal(String implName) {
		deviceFlag = implName;
	}

	/**
	 * @throws WSecurityEngineException
	 * @Title: setEnv
	 * @Description: 设置移动环境变量（android特有接口） 其他平台可选 对于蓝牙设备版本不支持时返回false
	 * @param @param context
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */
	public int setEnv(Context context) throws WSecurityEngineException {
		this.context = context;
		int isOK = BJCAWirelessInterface.SUCESS;
		try {
			if (bacaInterface != null)
				isOK = bacaInterface.setEnv(context);
			 else {
				this.loadContext();
				isOK = bacaInterface.setEnv(context);
			}
			return isOK;
		} catch (WSecurityEngineException e) {
			throw e;
		}
	}

	/**
	 * @throws WSecurityEngineException
	 * @Title: unSetEnv
	 * @Description: otg释放USB资源
	 * @param @param context
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */
	public boolean unSetEnv() {
		if (config.getDeviceType() == BJCAWirelessInterface.OTG)
			return bacaInterface.finalizeEnv();

		return true;
	}

	/**
	 * 加载并实例化厂商KEY实现
	 */

	protected void loadContext() throws WSecurityEngineException {
		if (this.context == null)
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INSTANCE_BJCA_WIRELESS_INTERFACE_ERROR, "没有初始化context，请setEnv");
		try {
			InputStream is = context.getAssets().open(PROCONFIG_XML);
			this.config = new ContainerConfig(WsecxConfigParse.parse(is));
			if (deviceFlag == null)
				return;

			DeviceDescribe device = config.getSelDevice(deviceFlag);
			if (device == null)
				throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INPUT_PARAM_NULL_INVAILD, deviceFlag + "：DeviceEntity not found");

			Class classVal = Class.forName(device.getProvider());
			Object id = classVal.newInstance();
			if (id instanceof BJCAWirelessInterface)
				bacaInterface = (BJCAWirelessInterface) id;

			if (id instanceof BJCABLEWirelessInterface)
				bleInterface = (BJCABLEWirelessInterface) id;


		} catch (WSecurityEngineException e) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INSTANCE_BJCA_WIRELESS_INTERFACE_ERROR, e);
		} catch (IOException e) {
			defConfig();
		} catch (ClassNotFoundException e) {
			defConfig();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INSTANCE_BJCA_WIRELESS_INTERFACE_ERROR, e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INSTANCE_BJCA_WIRELESS_INTERFACE_ERROR, e);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INSTANCE_BJCA_WIRELESS_INTERFACE_ERROR, e);
		}
	}

	private void defConfig() {
		// this.config = new ContainerConfig("cn.org.bjca.wsecx.core.jni.JNISoftWirelessImpl");
		this.config = new ContainerConfig("cn.org.bjca.wsecx.soft.SoftWirelessImpl");
		DeviceDescribe dev = config.getSelDevice(ContainerConfig.DEF_ID);
		// bacaInterface = new JNISoftWirelessImpl();
		bacaInterface = new SoftWirelessImpl();
	}

	/**
	 * 设置容器别名
	 * 
	 * @Title: IWSecurityEngine
	 * @Description: 设置容器别名
	 * @param @param containName
	 * @return void
	 * @throws
	 */

	public void setContainerName(String name) {
		config.setContainerAlias(name);
	}

	public void setContainerConfig(ContainerConfig config) {
		this.config = config;
	}

	public ContainerConfig getContainerConfig() {
		return this.config;
	}

	/**
	 * 获得容器名称集合,不需要登录便可获取
	 * 
	 * @throws WSecurityEngineException
	 * 
	 * @Title: getContainerList
	 * @Description: 获得容器名称集合,不需要登录便可获取
	 * @param @return
	 * @param @throws WSecXAppException
	 * @return 容器名称集合
	 * @throws
	 */

	public Vector<String> getContainerList() throws WSecurityEngineException {

		if (bacaInterface == null)
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.KEY_UNINIT, "请先执行 init 初始化");

		Vector<String> conName = new Vector<String>();
		try {
			conName = bacaInterface.getContainerList();
		} catch (Exception e) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.ALIAS_CERT_UNFOUND, "：getContainerList error:" + e.getMessage() + "==" + conName);
		}

		String sn = null;
		if (config.getDeviceType() != BJCAWirelessInterface.SD) {
			byte[] retBy = bacaInterface.readFile(BJCA_KEY_SN);
			if (retBy != null) {
				sn = new String(retBy);
			}
		}

		if (conName == null || conName.size() == 0)
			conName = new Vector<String>();
		 else if (sn != null)
			this.setContainerName(sn);
		 else
			this.setContainerName((String) conName.get(conName.size() - 1));

		return conName;
	}

	/**
	 * 
	 * @Title: getDeviceInfo
	 * @Description: 获得设备信息，不需要登录便可获取
	 * @param @param infoType 信息标识
	 * @param @return 设备信息
	 * @return WSecXContainer
	 * @throws
	 */

	public String getDeviceInfo(int infoType) throws WSecurityEngineException {

		if (infoType < BJCAWirelessInfo.DeviceInfo.DEVICE_TYPE || infoType >= BJCAWirelessInfo.DeviceInfo.DEVICE_INFO_END) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.DEVICE_INFO_ERROR, "infoType error:" + infoType);
		}

		if (bacaInterface == null) {
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.ENV_UNINIT, "请先执行 init 初始化");
		}

		ConnectionDevice device = WSecurityEnginePackage.getBinderDevice();

		if (device != null && infoType == BJCAWirelessInfo.DeviceInfo.DEVICEID && !device.getDeviceID().equals(ConnectionDevice.NULL)) {
			return device.getDeviceID();
		}

		return bacaInterface.getDeviceInfo(infoType);
	}

	public List<Map<String, String>> findDevices() {
		if (this.config.getDeviceType() == BJCAWirelessInterface.BLE) {
			return this.bleInterface.findDevices();
		}

		List retList = new ArrayList();

		Map ret = new HashMap();

		// String deviceID = bacaInterface.getDeviceInfo(BJCAWirelessInfo.DeviceInfo.DEVICEID);
		String id = this.config.getSelDeviceDescribe().getDeviceID();
		if (id == null) {
			id = this.config.getSelDeviceDescribe().getDescribe();
		}

		ret.put(BLE_SERVICEID, id);
		ret.put(BLE_NAME, this.config.getSelDeviceDescribe().getDescribe());
		retList.add(ret);
		return retList;
	}

	/**
	 * 
	 * @Title: BJCABLEWirelessInterface
	 * @Description: 连接蓝牙设备
	 * @param @param btID 服务ID
	 * @param @param btPass 连接密码
	 * @param @return 返回码
	 * @throws
	 */
	public int connectDevice(String btID, String btPass) {

		if (config.getDeviceType() == BJCAWirelessInterface.BLE) {

			// if (btID == null || btID.length() != BLE_CONNECTION_XOR.length())
			// {
			// return BJCAWirelessInfo.ErrorInfo.INPUT_PARAM_NULL_INVAILD;
			// }

			byte[] ids = btID.getBytes();
			byte[] init = BLE_CONNECTION_XOR.getBytes();

			byte[] tmp = new byte[ids.length];

			for (int i = 0; i < init.length; i++) {
				tmp[i] = (byte) (ids[i] ^ init[i]);
			}

			int ret = bleInterface.connectDevice(btID, new String(Base64.encode(tmp, Base64.DEFAULT)));
			return ret;
		}
		return BJCAWirelessInterface.SUCESS;
	}

	/**
	 * 
	 * <p>
	 * disconnectDevice
	 * </p>
	 * 
	 * @Description:断开蓝牙设备
	 * @return
	 */

	public boolean disconnectDevice() {
		// this.config.setLogin(false);
		if (config.getDeviceType() == BJCAWirelessInterface.BLE)
			return bleInterface.disconnectDevice();

		return true;
	}

	/**
	 * 
	 * <p>
	 * isConnect
	 * </p>
	 * 
	 * @Description:判断蓝牙设备是否连接
	 * @return boolean
	 */
	@Override
	public boolean isConnect() {
		// Log.d("debug===", config.getDeviceType()+"");
		if (config.getDeviceType() == BJCAWirelessInterface.BLE)
			return bleInterface.isConnect();

		// Log.d("debug===", "true");
		return true;
	}

	/*
	 * (非 Javadoc) <p>Title: register</p> <p>Description: </p>
	 * 
	 * @param context
	 * 
	 * @param listener
	 * 
	 * @see
	 * cn.org.bjca.wsecx.core.impl.WSecurityEngine#register(android.content.
	 * Context, cn.org.bjca.wsecx.interfaces.BluetoothKeyListener)
	 */

	@Override
	public void register(Context context, BluetoothKeyListener listener) {
		bleInterface.register(context, listener);
	}


	public byte[] encryptPin(String deviceID, String pin) {
		return ((JNISoftWirelessImpl) bacaInterface).encryptPin(deviceID, pin);
	}

	/**
	 * <p>decryptPin</p>
	 * @Description:pin码解密
	 * @param deviceID
	 * @param enpin
	 * @param enpin
	 * @return
	 */
	public byte[] decryptPin(String deviceID, byte[] enpin) {
		return ((JNISoftWirelessImpl) bacaInterface).decryptPin(deviceID, enpin);
	}
}
