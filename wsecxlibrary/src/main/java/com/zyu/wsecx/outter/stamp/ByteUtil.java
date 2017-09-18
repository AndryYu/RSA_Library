package com.zyu.wsecx.outter.stamp;

import java.io.ByteArrayOutputStream;

/***************************************************************************
 * <pre></pre>
 * @文件名称:  ByteUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-20 下午7:24:56
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
 * @文件名称:  ByteUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-20 下午7:24:56
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

public class ByteUtil
{

	/**
	  * <p>Title: </p>
	  * <p>Description: </p>
	  */

	public static final char[] HEX =
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	public static final String delimiter = "";

	public ByteUtil() {
		// TODO Auto-generated constructor stub
	}

	public static int byteTOIntR(byte[] retInt, int begin, int len)
	{

		// byte[] retInt = { (byte) 0x65, (byte) 0x04, (byte) 0x00, (byte) 0x00
		// };

		byte retByte[] = new byte[len];

		System.arraycopy(retInt, begin, retByte, 0, len);

		int ret = 0;

		int s = len - 1;
		int n = len;

		for (int i = s; (i >= 0 && n > 0); --i, --n)
		{
			ret <<= 8;
			ret |= retByte[i] & 0xFF;
		}

		return ret;
	}

	public static String byteTOString(byte[] retInt, int begin, int len)
	{

		byte retByte[] = new byte[len];

		System.arraycopy(retInt, begin, retByte, 0, len);

		return new String(retByte);
	}

	/**
	  * 
	   * <p>bytes2int</p>
	   * @Description:字节累加，从低位到高位
	   *              4个字节
	   * @param bytes
	   * @return
	  */
	public static int bytes2int(byte[] bytes)
	{
		int num = 0;
		for (int i = 0; i < 4; i++)
		{
			num += ((0xFF & bytes[(3 - i)]) << i * 8);
		}

		return num;
	}

	public static byte[] long2bytes(long num)
	{
		byte[] bytes = new byte[8];

		for (int i = 0; i < 8; i++)
		{
			bytes[(7 - i)] = ((byte) (int) (0xFF & num >> i * 8));
		}

		return bytes;
	}

	public static byte[] int2bytes(int num)
	{
		byte[] bytes = new byte[4];

		for (int i = 0; i < 4; i++)
		{
			bytes[(3 - i)] = ((byte) (0xFF & num >> i * 8));
		}
		return bytes;
	}

	public static byte[] reverse(byte[] input)
	{
		byte[] output = new byte[input.length];

		for (int i = 0; i < input.length; i++)
		{
			output[i] = input[(input.length - 1 - i)];
		}

		return output;
	}

	public static byte[] catbytes(byte[] bytes1, byte[] bytes2)
	{
		byte[] out = new byte[bytes1.length + bytes2.length];
		System.arraycopy(bytes1, 0, out, 0, bytes1.length);
		System.arraycopy(bytes2, 0, out, bytes1.length, bytes2.length);

		return out;
	}

	public static byte[] subbytes(byte[] bytes, int offset, int length)
	{
		byte[] buf = new byte[length];
		System.arraycopy(bytes, offset, buf, 0, length);

		return buf;
	}

	public static String bytes2hex(byte[] bytes)
	{
		return bytes2hex(bytes, "", bytes.length + 1);
	}

	public static byte[] hex2bytes(String str)
	{
		return hex2bytes(str, "");
	}

	private static byte[] hex2bytes(String str, String delimiter)
	{
		str = str.toLowerCase();

		if ("".equals(delimiter))
		{
			byte[] buf = new byte[str.length() / 2];

			for (int i = 0; i < buf.length; i++)
			{
				char ch = str.charAt(i * 2);
				if ((ch >= 'a') && (ch <= 'f'))
					buf[i] = ((byte) (ch - 'a' + 10 << 4));
				else
				{
					buf[i] = ((byte) (ch - '0' << 4));
				}

				ch = str.charAt(i * 2 + 1);
				if ((ch >= 'a') && (ch <= 'f'))
				{
					int tmp104_102 = i;
					byte[] tmp104_101 = buf;
					tmp104_101[tmp104_102] = ((byte) (tmp104_101[tmp104_102] + (byte) (ch - 'a' + 10)));
				} else
				{
					int tmp123_121 = i;
					byte[] tmp123_120 = buf;
					tmp123_120[tmp123_121] = ((byte) (tmp123_120[tmp123_121] + (byte) (ch - '0')));
				}
			}
			return buf;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String[] arr = str.split(delimiter);
		for (int i = 0; i < arr.length; i++)
			if (!arr[i].trim().equals(""))
			{
				baos.write(hex2byte(arr[i]));
			}
		return baos.toByteArray();
	}

	public static String byte2hex(byte n)
	{
		String str = "";

		str = str + HEX[((n & 0xF0) >> 4)];
		str = str + HEX[(n & 0xF)];

		return str;
	}

	public static byte hex2byte(String str)
	{
		char ch = str.charAt(0);
		byte n;
		if ((ch >= 'a') && (ch <= 'f'))
		{
			n = (byte) (ch - 'a' + 10 << 4);
		} else
		{
			n = (byte) (ch - '0' << 4);
		}

		ch = str.charAt(1);
		if ((ch >= 'a') && (ch <= 'f'))
		{
			n = (byte) (n + (byte) (ch - 'a' + 10));
		} else
		{
			n = (byte) (n + (byte) (ch - '0'));
		}

		return n;
	}

	private static String bytes2hex(byte[] data, String delimiter, int wrap)
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < data.length; i++)
		{
			if ((i != 0) && (i % wrap == 0))
			{
				sb.append("\n");
			}
			sb.append(byte2hex(data[i]));
			sb.append(delimiter);
		}

		sb.append(", " + data.length);
		return sb.toString();
	}

	public static void printHex(byte[] bytes)
	{
		printHex(bytes, 16);
	}

	private static void printHex(byte[] bytes, int wrap)
	{
		for (int i = 0; i < bytes.length; i += wrap)
		{
			if (i % 16 == 0)
			{
				if (i != 0)
				{
//					System.out.println();
				}
//				System.out.printf("%08X  ", new Object[]
//				{ Integer.valueOf(i) });
			}

			for (int j = i; ((j < i + wrap ? 1 : 0) & (j < bytes.length ? 1 : 0)) != 0; j++)
			{
				if (j - i == wrap / 2)
				{
//					System.out.printf(" ", new Object[0]);
				}
//				System.out.printf("%02X ", new Object[]
//				{ Byte.valueOf(bytes[j]) });
			}

		}

//		System.out.println();
	}

	/**
	 * 
	 * <p>
	 * subBytes
	 * </p>
	 * 
	 * @Description:从一个byte[]数组中截取一部分
	 * @param src
	 * @param begin
	 * @param count
	 * @return
	 */
	public static byte[] subBytes(byte[] src, int begin, int count)
	{
		byte[] bs = new byte[count];
		for (int i = begin; i < begin + count; i++)
			bs[i - begin] = src[i];
		return bs;
	}

	/**
	 * 
	 * <p>
	 * byte4ToIntInvert
	 * </p>
	 * 
	 * @Description:byte倒序转整数
	 * @param bytes
	 * @param off
	 * @return
	 */
	public static int byte4ToIntInvert(byte[] bytes, int off)
	{
		int b3 = bytes[off] & 0xFF;
		int b2 = bytes[off + 1] & 0xFF;
		int b1 = bytes[off + 2] & 0xFF;
		int b0 = bytes[off + 3] & 0xFF;
		return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
	}

	public static int byte2ToIntInvert(byte[] bytes, int off)
	{

		int b1 = bytes[off] & 0xFF;
		int b0 = bytes[off + 1] & 0xFF;
		return (b1 << 8) | b0;
	}

	/**
	 * 1.       SKF_WriteFile时自动更新文件 “LastModify”
	在移动端中间件中做同样处理
	2.       LastModify文件格式及内容： 大小为8字节， 分别写入年，月，日，时，分，秒，各占一字节，最后2字节预留， 即每次会写入最后一次更新时间，从而提高写入效率。
	 
	 *  0 失败
	 */
	
	public static String byte2Time(byte[] bytes)
	{

		int yearInit = 2000;
		String nullChar = "0";
		
		if(bytes == null || bytes.length <=2)
		{
			return nullChar;
		}
		
		StringBuffer res = new StringBuffer();
		
		int len = bytes.length;
		for(int i = 0; i<len-2; i++)
		{
			int temp = (int)(bytes[i] & 0xFF);
			
			if(i == 0)
			{
				temp +=yearInit;
			}else
			{
				temp += 0;
			}
			
			
			if(temp <10)
			{
				res.append(nullChar);
			}
			
			res.append(temp);
		}
		
		return res.toString();
	}

}
