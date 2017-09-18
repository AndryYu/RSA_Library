package com.zyu.wsecx.outter.util;

/***************************************************************************
 * <pre></pre>
 * @文件名称:  CryptoUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-31 下午4:27:41
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
 * @文件名称:  CryptoUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-31 下午4:27:41
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

import cn.org.bjca.wsecx.core.crypto.PBEParametersGenerator;
import cn.org.bjca.wsecx.core.crypto.digests.SHA1Digest;
import cn.org.bjca.wsecx.core.crypto.generators.PKCS12ParametersGenerator;
import cn.org.bjca.wsecx.core.crypto.params.DESedeParameters;
import cn.org.bjca.wsecx.core.crypto.params.KeyParameter;
import cn.org.bjca.wsecx.interfaces.BJCAWirelessInterface;
import cn.org.bjca.wsecx.soft.sm.SM3Digest;

public class CryptoUtil {
	public CryptoUtil() {

	}

	////生成16字节*8 = 128
	public static byte[] generateKey(char[] key) {
		PKCS12ParametersGenerator pkcs12parametersgenerator = new PKCS12ParametersGenerator(new SHA1Digest());
		pkcs12parametersgenerator.init(PBEParametersGenerator.PKCS12PasswordToBytes(key), null, 1);
		KeyParameter keyparameter = (KeyParameter) pkcs12parametersgenerator.generateDerivedParameters(128);
		byte abyte0[] = keyparameter.getKey();
		DESedeParameters desedeparameters = new DESedeParameters(abyte0);
		return desedeparameters.getKey();
	}
	
	//生成16字节
	public static byte[] generateSM3Key(char[] key) {
		PKCS12ParametersGenerator pkcs12parametersgenerator = new PKCS12ParametersGenerator(new SM3Digest());
		pkcs12parametersgenerator.init(PBEParametersGenerator.PKCS12PasswordToBytes(key), null, 1);
		KeyParameter keyparameter = (KeyParameter) pkcs12parametersgenerator.generateDerivedParameters(128);
		byte abyte0[] = keyparameter.getKey();
		DESedeParameters desedeparameters = new DESedeParameters(abyte0);
		return desedeparameters.getKey();
	}
	
	public static byte[] getDefaultIV(int alg, int mode) {

		byte[] ivSM4 = new byte[] { 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11 };
        
		
		//byte[] iv = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 };
        
		byte[] iv3DS = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
//		, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11 };

		if (mode != BJCAWirelessInterface.CBC) {
			return null;
		}
		byte[] ret = null;
		if (alg == BJCAWirelessInterface.TDES) {
//			ret = new byte[8];
//			System.arraycopy(iv, 0, ret, 0, 8);

			return iv3DS;
		} else if (alg == BJCAWirelessInterface.SM4) {
			ret = new byte[16];

			System.arraycopy(ivSM4, 0, ret, 0, 16);
		} else {
			return null;
		}
		return ret;
	}
	
}


