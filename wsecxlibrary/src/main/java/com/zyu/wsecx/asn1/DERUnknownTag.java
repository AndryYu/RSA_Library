package com.zyu.wsecx.asn1;

import java.io.IOException;

import cn.org.bjca.wsecx.outter.util.Arrays;

/**
 * We insert one of these when we find a tag we don't recognise.
 */
public class DERUnknownTag extends DERObject {
	int tag;

	byte[] data;

	/**
	 * @param tag
	 *            the tag value.
	 * @param data
	 *            the contents octets.
	 */
	public DERUnknownTag(int tag, byte[] data) {
		this.tag = tag;
		this.data = data;
	}

	public int getTag() {
		return tag;
	}

	public byte[] getData() {
		return data;
	}

	void encode(DEROutputStream out) throws IOException {
		out.writeEncoded(tag, data);
	}

	public boolean equals(Object o) {
		if (!(o instanceof DERUnknownTag)) {
			return false;
		}

		DERUnknownTag other = (DERUnknownTag) o;

		return tag == other.tag && Arrays.areEqual(data, other.data);
	}

	public int hashCode() {
		return tag ^ Arrays.hashCode(data);
	}
}
