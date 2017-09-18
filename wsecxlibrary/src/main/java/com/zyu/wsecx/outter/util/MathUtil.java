package com.zyu.wsecx.outter.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import cn.org.bjca.wsecx.core.math.ec.ECPoint;
import cn.org.bjca.wsecx.soft.sm.SM3Digest;
import cn.org.bjca.wsecx.soft.sm.sm2.SM2Signer;

/***************************************************************************
 * <pre></pre>
 *
 * @文件名称: MathUtil.java
 * @包 路   径：  cn.org.bjca.wsecx.soft.sm.sm2
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 * @类描述:
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2015-3-2 上午11:08:06
 * @修改记录： -----------------------------------------------------------------------------------------------
 * 时间                      |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 * |                 |                           |
 * -----------------------------------------------------------------------------------------------
 ***************************************************************************/

/***************************************************************************
 * <pre></pre>
 * @文件名称: MathUtil.java
 * @包 路   径：  cn.org.bjca.wsecx.soft.sm.sm2
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 *
 * @类描述:
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2015-3-2 上午11:08:06
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

public class MathUtil {
    public static byte[] conversionKey(byte[] smallKey, byte[] bigKey) {
        byte[] newKey = new byte[smallKey.length];
        for (int i = 0; i < smallKey.length; i++) {
            newKey[i] = ((byte) (smallKey[i] ^ bigKey[i]));
        }

        return newKey;
    }

    public static int byte2int(byte[] buffer, int offset) {
        int num = 0;

        for (int i = 0; i < 4; i++) {
            num = (int) (num + ((0xFF & buffer[(i + offset)]) << i * 8));
        }

        return num;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = ((byte) (i >> 24 & 0xFF));
        result[1] = ((byte) (i >> 16 & 0xFF));
        result[2] = ((byte) (i >> 8 & 0xFF));
        result[3] = ((byte) (i & 0xFF));
        return result;
    }

    public static String toHexString(byte[] data) {
        String str = "";
        for (int i = 1; i <= data.length; i++) {
            byte temp = data[(i - 1)];
            int n = (temp & 0xF0) >> 4;
            str = str + IntToHex(n);
            n = temp & 0xF;
            str = str + IntToHex(n);
            str = str + " ";
            if (i % 16 == 0) {
                str = str + "\n";
            }
        }

        return str;
    }

    public static void printWithHex(byte[] data) {
//		System.out.println(toHexString(data));
    }

    public static String IntToHex(int n) {
        if ((n > 15) || (n < 0))
            return "";
        if ((n >= 0) && (n <= 9)) {
            return n + "";
        }
        switch (n) {
            case 10:
                return "A";
            case 11:
                return "B";
            case 12:
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15:
                return "F";
        }

        return "";
    }

    public static byte[] intToByte(int inData) {
        ByteBuffer byteBuf = ByteBuffer.allocate(4);

        byteBuf.putInt(inData);

        byte[] outData = byteBuf.array();

        return outData;
    }

    public static int getFirstIntFromByteArray(byte[] buffer, int offset) {
        int num = 0;

        for (int i = 0; i < 4; i++) {
            num = (int) (num + ((0xFF & buffer[(i + offset)]) << i * 8));
        }

        return num;
    }


    /**
     * 国产算法hash实现
     */
    public static byte[] sm3Hash(byte[] dataInput, byte[] publicKey, byte[] id) {

//		SubjectPublicKeyInfo spki = strucCert.getSubjectPublicKeyInfo();
//		byte[] bPubKey = spki.getPublicKeyData().getBytes();
//		byte[] publicKey = new byte[64];
//
//		System.arraycopy(bPubKey, 1, publicKey, 0, publicKey.length);

        byte[] bHash = new byte[32];
        SM2Signer sm2 = new SM2Signer();
        SM3Digest sm3 = new SM3Digest();

        if (publicKey == null) {
            sm3.update(dataInput, 0, dataInput.length);
            sm3.doFinal(bHash, 0);
        } else {
            ECPoint pubkey = sm2.decodePoint(publicKey);
            BigInteger affineX = pubkey.getX().toBigInteger();
            BigInteger affineY = pubkey.getY().toBigInteger();

            sm3.addId(affineX, affineY, id);
            sm3.update(dataInput, 0, dataInput.length);
            sm3.doFinal(bHash, 0);
        }

//		byte[] returnData = new byte[bHash.length + 4];
//
//		if (bHash != null)
//		{
//			byte[] rightByte = MathUtil.intToByteArray(0);
//			System.arraycopy(rightByte, 0, returnData, 0, 4);
//			System.arraycopy(bHash, 0, returnData, 4, bHash.length);
//		} else
//		{
//			byte[] errorByte = MathUtil.intToByteArray(1);
//			System.arraycopy(errorByte, 0, returnData, 0, 4);
//			System.arraycopy(bHash, 0, returnData, 4, bHash.length);
//		}

        return bHash;
    }


    public static void main(String[] args) {
        byte[] newKey = conversionKey("123".getBytes(), "85678".getBytes());

//		for (int i = 0; i < newKey.length; i++)
//			System.out.println(newKey[i]);
    }
}