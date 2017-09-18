package com.zyu.wsecx.outter.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  Streams.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  流操作
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-9 下午5:01:12
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
public final class Streams {
	private static int BUFFER_SIZE = 512;

	public static byte[] readAll(InputStream inStr) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		pipeAll(inStr, buf);
		return buf.toByteArray();
	}

	public static int readFully(InputStream inStr, byte[] buf)
			throws IOException {
		return readFully(inStr, buf, 0, buf.length);
	}

	public static int readFully(InputStream inStr, byte[] buf, int off, int len)
			throws IOException {
		int totalRead = 0;
		while (totalRead < len) {
			int numRead = inStr.read(buf, off + totalRead, len - totalRead);
			if (numRead < 0) {
				break;
			}
			totalRead += numRead;
		}
		return totalRead;
	}

	public static void pipeAll(InputStream inStr, OutputStream outStr)
			throws IOException {
		byte[] bs = new byte[BUFFER_SIZE];
		int numRead;
		while ((numRead = inStr.read(bs, 0, bs.length)) >= 0) {
			outStr.write(bs, 0, numRead);
		}
	}
}
