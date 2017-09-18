package com.zyu.wsecx.asn1.x509;


import com.zyu.wsecx.asn1.ASN1Choice;
import com.zyu.wsecx.asn1.ASN1TaggedObject;
import com.zyu.wsecx.asn1.DEREncodable;
import com.zyu.wsecx.asn1.DERGeneralizedTime;
import com.zyu.wsecx.asn1.DERObject;
import com.zyu.wsecx.asn1.DERUTCTime;

public class Time implements DEREncodable, ASN1Choice {
	DERObject time;

	public static Time getInstance(ASN1TaggedObject obj, boolean explicit) {
		return getInstance(obj.getObject());
	}

	public Time(DERObject time) {
		if (!(time instanceof DERUTCTime)
				&& !(time instanceof DERGeneralizedTime)) {
			throw new IllegalArgumentException("unknown object passed to Time");
		}

		this.time = time;
	}

	public static Time getInstance(Object obj) {
		if (obj instanceof Time) {
			return (Time) obj;
		} else if (obj instanceof DERUTCTime) {
			return new Time((DERUTCTime) obj);
		} else if (obj instanceof DERGeneralizedTime) {
			return new Time((DERGeneralizedTime) obj);
		}

		throw new IllegalArgumentException("unknown object in factory");
	}

	public String getTime() {
		if (time instanceof DERUTCTime) {
			return ((DERUTCTime) time).getAdjustedTime();
		} else {
			return ((DERGeneralizedTime) time).getTime();
		}
	}

	/**
	 * <pre>
	 * Time ::= CHOICE {
	 *             utcTime        UTCTime,
	 *             generalTime    GeneralizedTime }
	 * </pre>
	 */
	public DERObject getDERObject() {
		return time;
	}
}
