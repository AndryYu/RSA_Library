package com.zyu.wsecx.outter.util;

import java.io.ByteArrayOutputStream;

import cn.org.bjca.wsecx.interfaces.BJCAWirelessInfo;
import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;

/***************************************************************************
 * <pre></pre>
 * @文件名称:  OSStringUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述: 对windows和linux平台的回车换行进行转换 
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-10 下午5:12:01
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
 * @文件名称:  OSStringUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  操作系统对回车字符处理
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-10 下午5:12:01
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

public class OSStringUtil {

	public final static int WINDOWS = 0;
	public final static int LINUX = 1;

	/**
	  * <p>Title: </p>
	  * <p>Description: </p>
	  */

	public OSStringUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	  * <p>convert</p>
	  * @Description:操作系统对回车字符处理
	  * @param data
	  * @param os 操作系统标识
	  * @param charset 字符集
	  * @return
	  * @throws WSecurityEngineException
	 */

	public static String convert(String data, int os, String charset) throws WSecurityEngineException {

		if (os == LINUX) {
			return data;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] outData = data.getBytes(charset);

			for (int i = 0; i < outData.length; i++) {
				if (outData[i] == '\n') {

					out.write('\r');
					out.write('\n');

				} else if (outData[i] == '\r') {
					continue;

				} else {
					out.write(outData[i]);
				}
			}
			String ret = new String(out.toByteArray(), charset);
			return ret;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.INPUT_PARAM_NULL_INVAILD, e);
		}
	}

}
