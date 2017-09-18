package com.zyu.wsecx.pkcs7;


import com.zyu.wsecx.pkcs7.sign.CMSProcessableByteArray;

/**
 * a holding class for a byte array of data to be signed or verified.
 *
 * @deprecated use CMSProcessableByteArray
 */
public class CMSSignableByteArray extends CMSProcessableByteArray {
    public CMSSignableByteArray(
            byte[] bytes) {
        super(bytes);
    }
}
