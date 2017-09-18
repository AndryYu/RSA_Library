package com.zyu.wsecx.asn1.cms.ecc;


import com.zyu.wsecx.asn1.ASN1Encodable;
import com.zyu.wsecx.asn1.ASN1EncodableVector;
import com.zyu.wsecx.asn1.ASN1OctetString;
import com.zyu.wsecx.asn1.ASN1Sequence;
import com.zyu.wsecx.asn1.ASN1TaggedObject;
import com.zyu.wsecx.asn1.DERObject;
import com.zyu.wsecx.asn1.DERSequence;
import com.zyu.wsecx.asn1.DERTaggedObject;
import com.zyu.wsecx.asn1.cms.OriginatorPublicKey;

public class MQVuserKeyingMaterial extends ASN1Encodable {
    private OriginatorPublicKey ephemeralPublicKey;
    private ASN1OctetString addedukm;

    public MQVuserKeyingMaterial(
            OriginatorPublicKey ephemeralPublicKey,
            ASN1OctetString addedukm) {
        this.ephemeralPublicKey = ephemeralPublicKey;
        this.addedukm = addedukm;
    }

    private MQVuserKeyingMaterial(
            ASN1Sequence seq) {

        this.ephemeralPublicKey = OriginatorPublicKey.getInstance(
                seq.getObjectAt(0));

        if (seq.size() > 1) {
            this.addedukm = ASN1OctetString.getInstance(
                    (ASN1TaggedObject) seq.getObjectAt(1), true);
        }
    }

    /**
     * return an MQVuserKeyingMaterial object from a tagged object.
     *
     * @param obj      the tagged object holding the object we want.
     * @param explicit true if the object is meant to be explicitly
     *                 tagged false otherwise.
     * @throws IllegalArgumentException if the object held by the
     *                                  tagged object cannot be converted.
     */
    public static MQVuserKeyingMaterial getInstance(
            ASN1TaggedObject obj,
            boolean explicit) {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    /**
     * return an MQVuserKeyingMaterial object from the given object.
     *
     * @param obj the object we want converted.
     * @throws IllegalArgumentException if the object cannot be converted.
     */
    public static MQVuserKeyingMaterial getInstance(
            Object obj) {
        if (obj == null || obj instanceof MQVuserKeyingMaterial) {
            return (MQVuserKeyingMaterial) obj;
        }

        if (obj instanceof ASN1Sequence) {
            return new MQVuserKeyingMaterial((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("Invalid MQVuserKeyingMaterial: " + obj.getClass().getName());
    }

    public OriginatorPublicKey getEphemeralPublicKey() {
        return ephemeralPublicKey;
    }

    public ASN1OctetString getAddedukm() {
        return addedukm;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * <pre>
     * MQVuserKeyingMaterial ::= SEQUENCE {
     *   ephemeralPublicKey OriginatorPublicKey,
     *   addedukm [0] EXPLICIT UserKeyingMaterial OPTIONAL  }
     * </pre>
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(ephemeralPublicKey);

        if (addedukm != null) {
            v.add(new DERTaggedObject(true, 0, addedukm));
        }

        return new DERSequence(v);
    }
}