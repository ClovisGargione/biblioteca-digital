package org.sankhya.auth;

import java.text.ParseException;

import org.joda.time.DateTime;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.crypto.MACSigner;

public class AuthUtils {

    private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
    private static final String TOKEN_SECRET = "aempresasankhyaeamelhorempresadomundo";
    public static final String AUTH_HEADER_KEY = "Authorization";

    public static String getSubject(String authHeader) throws ParseException, JOSEException {
	return decodeToken(authHeader).getSubject();
    }

    public static JWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
	SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
	if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
	    return signedJWT.getJWTClaimsSet();
	} else {
	    throw new JOSEException("Signature verification failed");
	}
    }

    public static String getSerializedToken(String authHeader) {
	return authHeader.split(" ")[1];
    }

    public static Token createToken(String remoteHost, Integer id, String login) throws JOSEException {
	JWTClaimsSet claim = new JWTClaimsSet.Builder().subject(Integer.toString(id)).issuer(remoteHost)
		.issueTime(DateTime.now().toDate()).expirationTime(DateTime.now().plusDays(14).toDate())
		.claim("login", login).build();

	MACSigner signer = new MACSigner(TOKEN_SECRET);
	SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
	jwt.sign(signer);

	return new Token(jwt.serialize());
    }

    public static boolean tokenExpirado(JWTClaimsSet claimSet) {

	if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now()))
	    return true;

	return false;
    }

    public static boolean palavraSecretaValida(String token) {
	try {
	    MACVerifier verifier = new MACVerifier(TOKEN_SECRET);
	    SignedJWT jwt = SignedJWT.parse(getSerializedToken(token));
	    return jwt.verify(verifier);
	} catch (ParseException pe) {
	    return false;
	} catch (JOSEException je) {
	    return false;
	}
    }
}
