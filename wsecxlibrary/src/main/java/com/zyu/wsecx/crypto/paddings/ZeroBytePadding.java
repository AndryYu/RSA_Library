package com.zyu.wsecx.crypto.paddings;

import cn.org.bjca.wsecx.core.crypto.InvalidCipherTextException;
import cn.org.bjca.wsecx.core.math.SecureRandom;

/**
 * A padder that adds NULL byte padding to a block.
 */
public class ZeroBytePadding implements BlockCipherPadding {
    /**
     * Initialise the padder.
     *
     * @param random - a SecureRandom if available.
     */
    @Override
    public void init(SecureRandom random)
            throws IllegalArgumentException {
        // nothing to do.
    }

    /**
     * Return the name of the algorithm the padder implements.
     *
     * @return the name of the algorithm the padder implements.
     */
    public String getPaddingName() {
        return "ZeroByte";
    }

    /**
     * add the pad bytes to the passed in block, returning the
     * number of bytes added.
     */
    public int addPadding(
            byte[] in,
            int inOff) {
        int added = (in.length - inOff);

        while (inOff < in.length) {
            in[inOff] = (byte) 0;
            inOff++;
        }

        return added;
    }

    /**
     * return the number of pad bytes present in the block.
     */
    public int padCount(byte[] in)
            throws InvalidCipherTextException {
        int count = in.length;

        while (count > 0) {
            if (in[count - 1] != 0) {
                break;
            }

            count--;
        }

        return in.length - count;
    }

	/* (非 Javadoc)
      * <p>Title: init</p>
	  * <p>Description: </p>
	  * @param random
	  * @throws IllegalArgumentException
	  * @see cn.org.bjca.wsecx.core.crypto.paddings.BlockCipherPadding#init(cn.org.bjca.wsecx.core.math.SecureRandom)
	  */


//	@Override
//	public void init(cn.org.bjca.wsecx.core.math.SecureRandom random) throws IllegalArgumentException {
//		// TODO Auto-generated method stub
//		
//	}
}
