package com.zyu.wsecx.asn1.cms;

import com.zyu.wsecx.asn1.ASN1Encodable;
import com.zyu.wsecx.asn1.ASN1EncodableVector;
import com.zyu.wsecx.asn1.ASN1Sequence;
import com.zyu.wsecx.asn1.ASN1TaggedObject;
import com.zyu.wsecx.asn1.DEREncodable;
import com.zyu.wsecx.asn1.DERObject;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DERSequence;
import com.zyu.wsecx.asn1.DERTaggedObject;

import java.util.Enumeration;


public class ContentInfo extends ASN1Encodable implements CMSObjectIdentifiers {
    private DERObjectIdentifier contentType;
    private DEREncodable content;

    public static ContentInfo getInstance(
            Object obj) {
        if (obj == null || obj instanceof ContentInfo) {
            return (ContentInfo) obj;
        } else if (obj instanceof ASN1Sequence) {
            return new ContentInfo((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public ContentInfo(
            ASN1Sequence seq) {
        Enumeration e = seq.getObjects();

        contentType = (DERObjectIdentifier) e.nextElement();

        if (e.hasMoreElements()) {
            content = ((ASN1TaggedObject) e.nextElement()).getObject();
        }
    }

    public ContentInfo(
            DERObjectIdentifier contentType,
            DEREncodable content) {
        this.contentType = contentType;
        this.content = content;
    }

    public DERObjectIdentifier getContentType() {
        return contentType;
    }

    public DEREncodable getContent() {
        return content;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * <pre>
     * ContentInfo ::= SEQUENCE {
     *          contentType ContentType,
     *          content
     *          [0] EXPLICIT ANY DEFINED BY contentType OPTIONAL }
     * </pre>
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(contentType);
//
        if (content != null) {
            v.add(new DERTaggedObject(0, content));
        }

        return new DERSequence(v);
    }
}
