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

import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.org.bjca.wsecx.interfaces.BluetoothKeyListener;
import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;
import cn.org.bjca.wsecx.outter.res.ContainerConfig;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  IWSecurityEngine.java
 * @包   路   径：  cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  密钥容器用户二次开发发布接口
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-12 下午2:52:25
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
public interface IWSecurityEngine
{

	/**
	 * 
	 * 用户登录
	 * 
	 * @throws WSecurityEngineException
	 * @Title: login
	 * @Description: 用户登录
	 * @param @param userPin
	 * @param @return
	 * @return int
	 * @throws
	 */

	public int login(String userPin) throws WSecurityEngineException;

	/**
	 * 
	 * @Title: logout
	 * @Description: 系统登出
	 * @param @return
	 * @return boolean
	 * @throws
	 */

	public boolean logout() throws WSecurityEngineException;

	/**
	 * 释放设备环境
	 * 
	 * @throws WSecurityEngineException
	 * 
	 * @Title: finalizeEvn
	 * @Description: 释放设备环境
	 * @param @return
	 * @return
	 * @throws
	 */

	public boolean finalizeEvn() throws WSecurityEngineException;

	/**
	 * 设置容器别名
	 * 
	 * @Title: IWSecurityEngine
	 * @Description: 设置容器别名
	 * @param @param containName
	 * @return void
	 * @throws
	 */

	public void setContainerName(String containName);

	/**
	 * 获得容器名称列表
	 * 
	 * @throws WSecurityEngineException
	 * 
	 * @Title: getContainerList
	 * @Description: 获得容器名称列表
	 * @param @return
	 * @param @throws WSecXAppException
	 * @return 容器名称集合
	 * @throws
	 */

	public Vector<String> getContainerList() throws WSecurityEngineException;

	/**
	 * 获得设备容器信息
	 * 
	 * @Title: IWSecurityEngine
	 * @Description: TODO
	 * @param @param infoType
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return String
	 * @throws
	 */

	public String getDeviceInfo(int infoType) throws WSecurityEngineException;

	/**
	 *
	 * 
	 * @Title: IWSecurityEngine
	 * @Description:  获取证书应用接口
	 * @param @return 设定文件
	 * @return WSecXAppInterface接口
	 * @throws
	 */

	public WSecXAppInterface loadWSecXAppInterface();

	/**
	 * 获取证书容器接口
	 * 
	 * @Title: loadWSecXCertContainerInterface
	 * @Description: TODO
	 * @param @return
	 * @return WSecXCertContainerInterface
	 * @throws
	 */

	public WSecXCertContainerInterface loadWSecXCertContainerInterface();

	/**
	 * 
	 * 
	 * @Title: loadWSecXDevicePinInterface
	 * @Description: 获取证书pin码操作接口
	 * @param @return
	 * @return WSecXDevicePinInterface
	 * @throws
	 */

	public WSecXDevicePinInterface loadWSecXDevicePinInterface();

	/**
	 * 
	 * 
	 * @Title: loadWSecXDataInterface
	 * @Description: OTG文件管理接口   增删改查文件
	 * @param @return
	 * @return WSecXDevicePinInterface
	 * @throws
	 */

	public WSecXDataInterface loadWSecXDataInterface();
	
	/**
	 * 
	  * <p>loadWSecXCounterSignerInterface</p>
	  * @Description:
	  * @return
	 */

	public WSecXCounterSignerInterface loadWSecXCounterSignerInterface();

	/**
	 * @throws WSecurityEngineException 
	 * @throws BJCAWirelessException 
	 * 
	 *
	 * 
	 * @Title: setEnv
	 * @Description: 设置移动环境变量（android特有接口）
	 * 其他平台可选
	 * @param @param context
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */
	public int setEnv(Context context) throws WSecurityEngineException;
	
	
	
	public boolean unSetEnv();

	/**
	 * 
	* @Title: IWSecurityEngine
	* @Description: 修改配置信息及默认密钥算法
	* @param @param algHash
	* @param @param algSym
	* @param @param algDisSym    设定文件 
	* @return IWSecurityEngine    返回类型 
	* @throws
	 */
	public void setContainerConfig(ContainerConfig config);

	/**
	 * 
	  * <p>isLogin</p>
	  * @Description:是否登录
	  * @return
	 */
	public boolean isLogin();

	/**
	 * 
	  * <p>getContainerConfig</p>
	  * @Description:获得配置信息
	  * @return
	 */

	public ContainerConfig getContainerConfig();

	/**
	 * BLE 蓝牙接口
	 */

	public List<Map<String, String>> findDevices();

	/**
	 * 
	  * <p>connectDevice</p>
	  * @Description:连接蓝牙设备
	  * @param btID 服务ID
	  * @param btPass 服务密码
	  * @return
	 */
	public int connectDevice(String btID, String btPass);

	/**
	 * 
	  * <p>disconnectDevice</p>
	  * @Description:断开蓝牙连接
	  * @return
	 */

	public boolean disconnectDevice();

	/**
	 * 
	  * <p>isConnect</p>
	  * @Description:判断蓝牙设备是否处于拦截状态
	  * @return true为连接中
	 */

	public boolean isConnect();

	/**
	 * 注册蓝牙设备
	  * <p>register</p>
	  * @Description:
	  * @param context
	  * @param listener
	 */
	public void register(Context context, BluetoothKeyListener listener);

	// public void waitForDevEvent() throws WSecurityEngineException;
	//
	// public void CancelWaitForDevEvent();
	
	
	public byte[] encryptPin(String deviceID, String pin);
	

	/**
	 * pin码解密
	  * <p>decryptPin</p>
	  * @Description:
	  * @param deviceID
	  * @param enpin
	  * @param enpin_len
	  * @return
	 */
	public byte[] decryptPin(String deviceID, byte[] enpin);
}
