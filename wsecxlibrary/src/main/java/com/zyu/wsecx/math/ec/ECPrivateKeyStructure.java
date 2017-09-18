package com.zyu.wsecx.math.ec;

import com.zyu.wsecx.asn1.ASN1Encodable;
import com.zyu.wsecx.asn1.ASN1EncodableVector;
import com.zyu.wsecx.asn1.ASN1Object;
import com.zyu.wsecx.asn1.ASN1OctetString;
import com.zyu.wsecx.asn1.ASN1Sequence;
import com.zyu.wsecx.asn1.ASN1TaggedObject;
import com.zyu.wsecx.asn1.DERBitString;
import com.zyu.wsecx.asn1.DEREncodable;
import com.zyu.wsecx.asn1.DERInteger;
import com.zyu.wsecx.asn1.DERObject;
import com.zyu.wsecx.asn1.DEROctetString;
import com.zyu.wsecx.asn1.DERSequence;
import com.zyu.wsecx.asn1.DERTaggedObject;

import java.math.BigInteger;
import java.util.Enumeration;


/**
 * the elliptic curve private key object from SEC 1
 */
public class ECPrivateKeyStructure extends ASN1Encodable {
    private ASN1Sequence seq;

    public ECPrivateKeyStructure(
        ASN1Sequence  seq)
    {
        this.seq = seq;
    }

    public ECPrivateKeyStructure(
        BigInteger key)
    {
        byte[] bytes = BigIntegers.asUnsignedByteArray(key);

        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(new DERInteger(1));
        v.add(new DEROctetString(bytes));

        seq = new DERSequence(v);
    }

    public ECPrivateKeyStructure(
        BigInteger key,
        ASN1Encodable parameters)
    {
        this(key, null, parameters);
    }

    public ECPrivateKeyStructure(
        BigInteger key,
        DERBitString publicKey,
        ASN1Encodable parameters)
    {
        byte[] bytes = BigIntegers.asUnsignedByteArray(key);

        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(new DERInteger(1));
        v.add(new DEROctetString(bytes));

        if (parameters != null)
        {
            v.add(new DERTaggedObject(true, 0, parameters));
        }

        if (publicKey != null)
        {
            v.add(new DERTaggedObject(true, 1, publicKey));
        }

        seq = new DERSequence(v);
    }

    public BigInteger getKey()
    {
        ASN1OctetString octs = (ASN1OctetString)seq.getObjectAt(1);

        return new BigInteger(1, octs.getOctets());
    }

    public DERBitString getPublicKey()
    {
        return (DERBitString)getObjectInTag(1);
    }

    public ASN1Object getParameters()
    {
        return getObjectInTag(0);
    }

    private ASN1Object getObjectInTag(int tagNo)
    {
        Enumeration e = seq.getObjects();

        while (e.hasMoreElements())
        {
            DEREncodable obj = (DEREncodable)e.nextElement();

            if (obj instanceof ASN1TaggedObject)
            {
                ASN1TaggedObject tag = (ASN1TaggedObject)obj;
                if (tag.getTagNo() == tagNo)
                {
                    return (ASN1Object)((DEREncodable)tag.getObject()).getDERObject();
                }
            }
        }
        return null;
    }

    /**
     * ECPrivateKey ::= SEQUENCE {
     *     version INTEGER { ecPrivkeyVer1(1) } (ecPrivkeyVer1),
     *     privateKey OCTET STRING,
     *     parameters [0] Parameters OPTIONAL,
     *     publicKey [1] BIT STRING OPTIONAL }
     */
    public DERObject toASN1Object()
    {
        return seq;
    }
}
