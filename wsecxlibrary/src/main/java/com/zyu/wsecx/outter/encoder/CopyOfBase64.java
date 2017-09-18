package com.zyu.wsecx.outter.encoder;

import java.io.IOException;
import java.io.OutputStream;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  Base64.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.encoder
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:base64 编码工具类  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-9 下午6:39:19
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
public class CopyOfBase64 {
	private static final Encoder encoder = new EncoderBase64();

	/**
	 * encode the input data producing a base 64 encoded byte array.
	 * 
	 * @return a byte array containing the base 64 encoded data.
	 */
	public static String encode(byte[] data) {
		
		
		 String ret = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);

		 return ret;
	}

	/**
	 * Encode the byte data to base 64 writing it to the given output stream.
	 * 
	 * @return the number of bytes produced.
	 */
	public static int encode(byte[] data, OutputStream out) throws IOException {
		return encoder.encode(data, 0, data.length, out);
	}

	/**
	 * Encode the byte data to base 64 writing it to the given output stream.
	 * 
	 * @return the number of bytes produced.
	 */
	public static int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
		return encoder.encode(data, off, length, out);
	}

	/**
	 * decode the base 64 encoded input data. It is assumed the input data is
	 * valid.
	 * 
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decode(byte[] data) {
		 byte[] retBy = android.util.Base64.decode(data, android.util.Base64.DEFAULT);
		 return retBy;
	}

	/**
	 * decode the base 64 encoded String data - whitespace will be ignored.
	 * 
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decode(String data) {
		byte[] retBy = android.util.Base64.decode(data, android.util.Base64.DEFAULT);
		return retBy;
	}

	/**
	 * decode the base 64 encoded String data writing it to the given output
	 * stream, whitespace characters will be ignored.
	 * 
	 * @return the number of bytes produced.
	 */
	public static int decode(String data, OutputStream out) throws Exception {
		return encoder.decode(data, out);
	}
}
