package com.zyu.wsecx.asn1.pkcs;

import com.zyu.wsecx.asn1.ASN1Encodable;
import com.zyu.wsecx.asn1.ASN1EncodableVector;
import com.zyu.wsecx.asn1.ASN1InputStream;
import com.zyu.wsecx.asn1.ASN1Sequence;
import com.zyu.wsecx.asn1.DERBitString;
import com.zyu.wsecx.asn1.DERInteger;
import com.zyu.wsecx.asn1.DERNull;
import com.zyu.wsecx.asn1.DERObject;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DERSequence;
import com.zyu.wsecx.asn1.cryptopro.CryptoProObjectIdentifiers;
import com.zyu.wsecx.asn1.nist.NISTObjectIdentifiers;
import com.zyu.wsecx.asn1.oiw.OIWObjectIdentifiers;
import com.zyu.wsecx.asn1.x509.AlgorithmIdentifier;
import com.zyu.wsecx.asn1.x9.X9ObjectIdentifiers;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;



/**
 * PKCS10 Certification request object.
 * 
 * <pre>
 * CertificationRequest ::= SEQUENCE {
 *   certificationRequestInfo  CertificationRequestInfo,
 *   signatureAlgorithm        AlgorithmIdentifier{{ SignatureAlgorithms }},
 *   signature                 BIT STRING
 * }
 * </pre>
 */
public class CertificationRequest extends ASN1Encodable {

	protected static Hashtable algorithms = new Hashtable();

	protected static Hashtable params = new Hashtable();

	private static Hashtable keyAlgorithms = new Hashtable();

	private static Hashtable oids = new Hashtable();

	protected static Vector noParams = new Vector();

	private static Hashtable sigAlgorithms = new Hashtable();

	static {
		algorithms.put("RSA", new DERObjectIdentifier("1.2.840.113549.1.1.1"));
		algorithms.put("MD2WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.2"));
		algorithms.put("MD2WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.2"));
		algorithms.put("MD5WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
		algorithms.put("MD5WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
		algorithms.put("RSAWITHMD5", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
		algorithms.put("SHA1WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
		algorithms.put("SHA1WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
		algorithms.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
		algorithms.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
		algorithms.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
		algorithms.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
		algorithms.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
		algorithms.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
		algorithms.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
		algorithms.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
		algorithms.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
		algorithms.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
		algorithms.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
		algorithms.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
		algorithms.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
		algorithms.put("RSAWITHSHA1", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
		algorithms.put("RIPEMD160WITHRSAENCRYPTION", new DERObjectIdentifier("1.3.36.3.3.1.2"));
		algorithms.put("RIPEMD160WITHRSA", new DERObjectIdentifier("1.3.36.3.3.1.2"));
		algorithms.put("SHA1WITHDSA", new DERObjectIdentifier("1.2.840.10040.4.3"));
		algorithms.put("DSAWITHSHA1", new DERObjectIdentifier("1.2.840.10040.4.3"));
		algorithms.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
		algorithms.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
		algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
		algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
		algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
		algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
		algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
		algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
		algorithms.put("GOST3411WITHGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
		algorithms.put("GOST3410WITHGOST3411", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
		algorithms.put("GOST3411WITHECGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
		algorithms.put("GOST3411WITHECGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
		algorithms.put("GOST3411WITHGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);

		// For sm2 algs
		/*
		 * algorithms.put("SM3WITHSM2", new DERObjectIdentifier(
		 * "1.2.156.10197.1.501")); algorithms.put("SHA1WITHSM2", new
		 * DERObjectIdentifier( "1.2.156.10197.1.502"));
		 */
		algorithms.put("SM3WITHSM2", new DERObjectIdentifier("1.2.840.10045.2.1"));

		//
		// reverse mappings
		//
		oids.put(new DERObjectIdentifier("1.2.156.10197.1.501"), "SM3WITHSM2");
		oids.put(new DERObjectIdentifier("1.2.156.10197.1.502"), "SHA1WITHSM2");

		oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
		oids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
		oids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
		oids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
		oids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
		oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
		oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410");

		oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
		oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
		oids.put(new DERObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
		oids.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
		oids.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
		oids.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
		oids.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
		oids.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
		oids.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
		oids.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
		oids.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
		oids.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");

		sigAlgorithms.put("SM3WITHSM2", new DERObjectIdentifier("1.2.156.10197.1.501"));

		//
		// key types
		//
		keyAlgorithms.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
		keyAlgorithms.put(X9ObjectIdentifiers.id_dsa, "DSA");

		//
		// According to RFC 3279, the ASN.1 encoding SHALL (id-dsa-with-sha1) or
		// MUST (ecdsa-with-SHA*) omit the parameters field.
		// The parameters field SHALL be NULL for RSA based signature
		// algorithms.
		//
		noParams.addElement(X9ObjectIdentifiers.ecdsa_with_SHA1);
		noParams.addElement(X9ObjectIdentifiers.ecdsa_with_SHA224);
		noParams.addElement(X9ObjectIdentifiers.ecdsa_with_SHA256);
		noParams.addElement(X9ObjectIdentifiers.ecdsa_with_SHA384);
		noParams.addElement(X9ObjectIdentifiers.ecdsa_with_SHA512);
		noParams.addElement(X9ObjectIdentifiers.id_dsa_with_sha1);
		noParams.addElement(NISTObjectIdentifiers.dsa_with_sha224);
		noParams.addElement(NISTObjectIdentifiers.dsa_with_sha256);

		//
		// RFC 4491
		//
		noParams.addElement(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
		noParams.addElement(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);

		//
		// explicit params
		//
		AlgorithmIdentifier sha1AlgId = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, new DERNull());
		params.put("SHA1WITHRSAANDMGF1", creatPSSParams(sha1AlgId, 20));

		AlgorithmIdentifier sha224AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, new DERNull());
		params.put("SHA224WITHRSAANDMGF1", creatPSSParams(sha224AlgId, 28));

		AlgorithmIdentifier sha256AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, new DERNull());
		params.put("SHA256WITHRSAANDMGF1", creatPSSParams(sha256AlgId, 32));

		AlgorithmIdentifier sha384AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, new DERNull());
		params.put("SHA384WITHRSAANDMGF1", creatPSSParams(sha384AlgId, 48));

		AlgorithmIdentifier sha512AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, new DERNull());
		params.put("SHA512WITHRSAANDMGF1", creatPSSParams(sha512AlgId, 64));

		params.put("SM3WITHSM2", new DERObjectIdentifier("1.2.156.10197.1.301"));
	}

	protected CertificationRequestInfo reqInfo = null;

	protected AlgorithmIdentifier sigAlgId = null;

	protected AlgorithmIdentifier pubKeyAlgId = null;

	protected DERBitString sigBits = null;

	private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier hashAlgId, int saltSize) {
		return new RSASSAPSSparams(hashAlgId, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, hashAlgId), new DERInteger(saltSize), new DERInteger(1));
	}

	public static CertificationRequest getInstance(Object o) {
		if (o instanceof CertificationRequest) {
			return (CertificationRequest) o;
		}

		if (o instanceof ASN1Sequence) {
			return new CertificationRequest((ASN1Sequence) o);
		}

		throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
	}

	protected CertificationRequest() {
	}

	public CertificationRequest(CertificationRequestInfo requestInfo, AlgorithmIdentifier algorithm, DERBitString signature) {
		this.reqInfo = requestInfo;
		this.sigAlgId = algorithm;
		this.sigBits = signature;
	}

	public CertificationRequest(ASN1Sequence seq) {
		reqInfo = CertificationRequestInfo.getInstance(seq.getObjectAt(0));
		sigAlgId = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
		sigBits = (DERBitString) seq.getObjectAt(2);
	}

	public CertificationRequestInfo getCertificationRequestInfo() {
		return reqInfo;
	}

	public AlgorithmIdentifier getSignatureAlgorithm() {
		return sigAlgId;
	}

	public DERBitString getSignature() {
		return sigBits;
	}

	public DERObject toASN1Object() {
		// Construct the CertificateRequest
		ASN1EncodableVector v = new ASN1EncodableVector();

		v.add(reqInfo);
		v.add(sigAlgId);
		v.add(sigBits);

		return new DERSequence(v);
	}

	protected static ASN1Sequence toDERSequence(byte[] bytes) {

		ASN1InputStream dIn = null;
		try {
			dIn = new ASN1InputStream(bytes);

			return (ASN1Sequence) dIn.readObject();
		} catch (Exception e) {
			throw new IllegalArgumentException("badly encoded request");
		} finally {
			try {
				dIn.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
}
