package com.zyu.wsecx.pkcs7.sign;

import com.zyu.wsecx.asn1.ASN1Object;
import com.zyu.wsecx.asn1.ASN1Set;
import com.zyu.wsecx.asn1.DERNull;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DEROctetString;
import com.zyu.wsecx.asn1.DERSet;
import com.zyu.wsecx.asn1.cms.AttributeTable;
import com.zyu.wsecx.asn1.cms.CMSObjectIdentifiers;
import com.zyu.wsecx.asn1.cms.IssuerAndSerialNumber;
import com.zyu.wsecx.asn1.cms.SignerIdentifier;
import com.zyu.wsecx.asn1.cryptopro.CryptoProObjectIdentifiers;
import com.zyu.wsecx.asn1.nist.NISTObjectIdentifiers;
import com.zyu.wsecx.asn1.oiw.OIWObjectIdentifiers;
import com.zyu.wsecx.asn1.pkcs.PKCSObjectIdentifiers;
import com.zyu.wsecx.asn1.teletrust.TeleTrusTObjectIdentifiers;
import com.zyu.wsecx.asn1.x509.AlgorithmIdentifier;
import com.zyu.wsecx.asn1.x509.CertificateList;
import com.zyu.wsecx.asn1.x509.TBSCertificateStructure;
import com.zyu.wsecx.asn1.x509.X509CertificateStructure;
import com.zyu.wsecx.asn1.x9.X9ObjectIdentifiers;
import com.zyu.wsecx.pkcs7.CMSAttributeTableGenerator;
import com.zyu.wsecx.pkcs7.CMSException;
import com.zyu.wsecx.pkcs7.SignerInformationStore;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CRLException;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CMSSignedGenerator {
    /**
     * Default type for the signed data.
     */
    public static final String DATA = CMSObjectIdentifiers.data.getId();

    public static final String DIGEST_SHA1 = OIWObjectIdentifiers.idSHA1.getId();
    public static final String DIGEST_SM3 = "1.2.156.10197.1.401";


    public static final String DIGEST_SHA224 = NISTObjectIdentifiers.id_sha224.getId();
    public static final String DIGEST_SHA256 = NISTObjectIdentifiers.id_sha256.getId();
    public static final String DIGEST_SHA384 = NISTObjectIdentifiers.id_sha384.getId();
    public static final String DIGEST_SHA512 = NISTObjectIdentifiers.id_sha512.getId();
    public static final String DIGEST_MD5 = PKCSObjectIdentifiers.md5.getId();
    public static final String DIGEST_GOST3411 = CryptoProObjectIdentifiers.gostR3411.getId();
    public static final String DIGEST_RIPEMD128 = TeleTrusTObjectIdentifiers.ripemd128.getId();
    public static final String DIGEST_RIPEMD160 = TeleTrusTObjectIdentifiers.ripemd160.getId();
    public static final String DIGEST_RIPEMD256 = TeleTrusTObjectIdentifiers.ripemd256.getId();

    public static final String ENCRYPTION_RSA = PKCSObjectIdentifiers.rsaEncryption.getId();
    public static final String ENCRYPTION_SM2 = "1.2.840.113549.1.1.2";


    public static final String ENCRYPTION_DSA = X9ObjectIdentifiers.id_dsa_with_sha1.getId();
    public static final String ENCRYPTION_ECDSA = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
    public static final String ENCRYPTION_RSA_PSS = PKCSObjectIdentifiers.id_RSASSA_PSS.getId();
    public static final String ENCRYPTION_GOST3410 = CryptoProObjectIdentifiers.gostR3410_94.getId();
    public static final String ENCRYPTION_ECGOST3410 = CryptoProObjectIdentifiers.gostR3410_2001.getId();

    private static final String ENCRYPTION_ECDSA_WITH_SHA1 = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
    private static final String ENCRYPTION_ECDSA_WITH_SHA224 = X9ObjectIdentifiers.ecdsa_with_SHA224.getId();
    private static final String ENCRYPTION_ECDSA_WITH_SHA256 = X9ObjectIdentifiers.ecdsa_with_SHA256.getId();
    private static final String ENCRYPTION_ECDSA_WITH_SHA384 = X9ObjectIdentifiers.ecdsa_with_SHA384.getId();
    private static final String ENCRYPTION_ECDSA_WITH_SHA512 = X9ObjectIdentifiers.ecdsa_with_SHA512.getId();

    private static final Set NO_PARAMS = new HashSet();
    private static final Map EC_ALGORITHMS = new HashMap();

    static {
        NO_PARAMS.add(ENCRYPTION_DSA);
        NO_PARAMS.add(ENCRYPTION_ECDSA);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA1);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA224);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA256);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA384);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA512);

        EC_ALGORITHMS.put(DIGEST_SHA1, ENCRYPTION_ECDSA_WITH_SHA1);
        EC_ALGORITHMS.put(DIGEST_SHA224, ENCRYPTION_ECDSA_WITH_SHA224);
        EC_ALGORITHMS.put(DIGEST_SHA256, ENCRYPTION_ECDSA_WITH_SHA256);
        EC_ALGORITHMS.put(DIGEST_SHA384, ENCRYPTION_ECDSA_WITH_SHA384);
        EC_ALGORITHMS.put(DIGEST_SHA512, ENCRYPTION_ECDSA_WITH_SHA512);
    }

    protected List _certs = new ArrayList();
    protected List _crls = new ArrayList();
    protected List _signers = new ArrayList();
    protected Map _digests = new HashMap();

    protected final SecureRandom rand;

    /**
     * base constructor
     */
    protected CMSSignedGenerator() {
        this(new SecureRandom());
    }

    /**
     * constructor allowing specific source of randomness
     *
     * @param rand instance of SecureRandom to use
     */
    protected CMSSignedGenerator(SecureRandom rand) {
        this.rand = rand;
    }

    protected String getEncOID(PrivateKey key, String digestOID) {
        String encOID = null;

        if (key == null) {
            encOID = ENCRYPTION_RSA;
        } else if (key instanceof RSAPrivateKey || "RSA".equalsIgnoreCase(key.getAlgorithm())) {
            encOID = ENCRYPTION_RSA;
        } else if (key instanceof DSAPrivateKey || "DSA".equalsIgnoreCase(key.getAlgorithm())) {
            encOID = ENCRYPTION_DSA;
            if (!digestOID.equals(DIGEST_SHA1)) {
                throw new IllegalArgumentException("can't mix DSA with anything but SHA1");
            }
        }
        // else if ("ECDSA".equalsIgnoreCase(key.getAlgorithm()) ||
        // "EC".equalsIgnoreCase(key.getAlgorithm()))
        // {
        // encOID = (String)EC_ALGORITHMS.get(digestOID);
        // if (encOID == null)
        // {
        // throw new
        // IllegalArgumentException("can't mix ECDSA with anything but SHA family digests");
        // }
        // }
        // else if (key instanceof GOST3410PrivateKey ||
        // "GOST3410".equalsIgnoreCase(key.getAlgorithm()))
        // {
        // encOID = ENCRYPTION_GOST3410;
        // }
        // else if ("ECGOST3410".equalsIgnoreCase(key.getAlgorithm()))
        // {
        // encOID = ENCRYPTION_ECGOST3410;
        // }

        return encOID;
    }

    protected AlgorithmIdentifier getEncAlgorithmIdentifier(String encOid, Signature sig) throws IOException {
        if (NO_PARAMS.contains(encOid)) {
            return new AlgorithmIdentifier(new DERObjectIdentifier(encOid));
        } else {
            if (encOid.equals(CMSSignedGenerator.ENCRYPTION_RSA_PSS)) {
                AlgorithmParameters sigParams = sig.getParameters();

                return new AlgorithmIdentifier(new DERObjectIdentifier(encOid), ASN1Object.fromByteArray(sigParams.getEncoded()));
            } else {
                return new AlgorithmIdentifier(new DERObjectIdentifier(encOid), new DERNull());
            }
        }
    }

    protected Map getBaseParameters(DERObjectIdentifier contentType, AlgorithmIdentifier digAlgId, byte[] hash) {
        Map param = new HashMap();
        param.put(CMSAttributeTableGenerator.CONTENT_TYPE, contentType);
        param.put(CMSAttributeTableGenerator.DIGEST_ALGORITHM_IDENTIFIER, digAlgId);
        param.put(CMSAttributeTableGenerator.DIGEST, hash.clone());
        return param;
    }

    protected ASN1Set getAttributeSet(AttributeTable attr) {
        if (attr != null) {
            return new DERSet(attr.toASN1EncodableVector());
        }

        return null;
    }

    /**
     * add the certificates and CRLs contained in the given CertStore
     * to the pool that will be included in the encoded signature block.
     * <p>
     * Note: this assumes the CertStore will support null in the get
     * methods.
     *
     * @param certStore CertStore containing the public key certificates and CRLs
     * @throws CertStoreException if an issue occurs processing the CertStore
     * @throws CMSException                          if an issue occurse transforming data from the CertStore into the message
     */
    // public void addCertificatesAndCRLs(
    // CertStore certStore)
    // throws CertStoreException, CMSException
    // {
    // _certs.addAll(CMSUtils.getCertificatesFromStore(certStore));
    // _crls.addAll(CMSUtils.getCRLsFromStore(certStore));
    // }
    public void addCertChains(X509Certificate[] certs) throws CertStoreException, CMSException, CertificateEncodingException, IOException {

        if (certs == null) {
            return;
        }

        for (X509Certificate c : certs) {

            _certs.add(X509CertificateStructure.getInstance(ASN1Object.fromByteArray(c.getEncoded())));
        }
    }

    public void addCertChains(X509CertificateStructure[] certs) throws CertStoreException, CMSException, CertificateEncodingException, IOException {

        if (certs == null) {
            return;
        }

        for (X509CertificateStructure c : certs) {

            _certs.add(c);
        }
    }

    public void addCRLs(X509CRL[] crls) throws CertStoreException, CMSException, CertificateEncodingException, IOException, CRLException {

        if (crls == null) {
            return;
        }

        for (X509CRL c : crls) {
            _crls.add(CertificateList.getInstance(ASN1Object.fromByteArray(c.getEncoded())));
        }

    }

    /**
     * Add the attribute certificates contained in the passed in store to the
     * generator.
     *
     * @param store a store of Version 2 attribute certificates
     * @throws CMSException if an error occurse processing the store.
     */
    // public void addAttributeCertificates(X509Store store) throws CMSException
    // {
    // try {
    // for (Iterator it = store.getMatches(null).iterator(); it.hasNext();) {
    // X509AttributeCertificate attrCert = (X509AttributeCertificate) it.next();
    //
    // _certs.add(new DERTaggedObject(false, 2,
    // AttributeCertificate.getInstance(ASN1Object.fromByteArray(attrCert.getEncoded()))));
    // }
    // } catch (IllegalArgumentException e) {
    // throw new CMSException("error processing attribute certs", e);
    // } catch (IOException e) {
    // throw new CMSException("error processing attribute certs", e);
    // }
    // }

    /**
     * Add a store of precalculated signers to the generator.
     *
     * @param signerStore store of signers
     */
    public void addSigners(SignerInformationStore signerStore) {
        Iterator it = signerStore.getSigners().iterator();

        while (it.hasNext()) {
            _signers.add(it.next());
        }
    }

    /**
     * Return a map of oids and byte arrays representing the digests calculated on the content during
     * the last generate.
     *
     * @return a map of oids (as String objects) and byte[] representing digests.
     */
    public Map getGeneratedDigests() {
        return new HashMap(_digests);
    }

    static SignerIdentifier getSignerIdentifier(X509Certificate cert) {
        TBSCertificateStructure tbs;
        try {
            tbs = CMSUtils.getTBSCertificateStructure(cert);
        } catch (CertificateEncodingException e) {
            throw new IllegalArgumentException("can't extract TBS structure from this cert");
        }

        IssuerAndSerialNumber encSid = new IssuerAndSerialNumber(tbs.getIssuer(), tbs.getSerialNumber().getValue());
        return new SignerIdentifier(encSid);
    }

    static SignerIdentifier getSignerIdentifier(X509CertificateStructure cert) {
        TBSCertificateStructure tbs = cert.getTBSCertificate();

        IssuerAndSerialNumber encSid = new IssuerAndSerialNumber(tbs.getIssuer(), tbs.getSerialNumber().getValue());
        return new SignerIdentifier(encSid);
    }

    static SignerIdentifier getSignerIdentifier(byte[] subjectKeyIdentifier) {
        return new SignerIdentifier(new DEROctetString(subjectKeyIdentifier));
    }

    static class DigOutputStream extends OutputStream {
        MessageDigest dig;

        public DigOutputStream(MessageDigest dig) {
            this.dig = dig;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            dig.update(b, off, len);
        }

        public void write(int b) throws IOException {
            dig.update((byte) b);
        }
    }

    // static class SigOutputStream extends OutputStream {
    // private final Signature sig;
    //
    // public SigOutputStream(Signature sig) {
    // this.sig = sig;
    // }
    //
    // public void write(byte[] b, int off, int len) throws IOException {
    // try {
    // sig.update(b, off, len);
    // } catch (SignatureException e) {
    // throw new CMSStreamException("signature problem: " + e, e);
    // }
    // }
    //
    // public void write(int b) throws IOException {
    // try {
    // sig.update((byte) b);
    // } catch (SignatureException e) {
    // throw new CMSStreamException("signature problem: " + e, e);
    // }
    // }
    // }
}
