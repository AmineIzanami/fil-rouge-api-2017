package fr.centralesupelec.sio.endpoints;

import fr.centralesupelec.sio.data.AccountsRepository;
import fr.centralesupelec.sio.model.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;

/**
 * A (simplified) class to handle authentication-related stuff in the API.
 * The manager is in charge of generating access token when a user logs in, an to verify them afterward.
 * Tokens are JWTs (JSON Web Tokens): they are not encrypted (simply base-64 encoded), but their signature can be verified to check if they have be tempered.
 */
public class AuthManager {

    // This is a random private key used to sign the tokens.
    // This should be kept secret on the server.

    private static final byte[] SIGNATURE = TextCodec.BASE64.decode("b5dfdd86fdc777b34b78a7fe976aef9b54767400e73bae310b74ab2884a109b6");

    // Our authority name as a issuer and consumer of JWT.
    public static final String ISSUER = "fr.centralesupelec.movies";

    /**
     * Generate a new access token for a user.
     * @param username The name of the user.
     * @return A newly created access token.
     */
    // TODO: Switch to an ID? Can users change their username?
    public static String generateAccessToken(String username) {
        // We use the jsonwebtoken.io library to generate the token.
        // This library uses a "builder" pattern, with metho chaining.
        JwtBuilder b = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SIGNATURE) // Define the signature of the token.
                .setIssuer(ISSUER)
                .setSubject(username); // The username is stored in the standard "sub" key of the token.
        return b.compact();
    }

    public static boolean checkAccessToken(String accessToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SIGNATURE) // Check the token using the same signature.
                    .requireIssuer(ISSUER) // Ensure the issuer is the same.
                    .parseClaimsJws(accessToken) // This will throw JwtException in case of an invalid token.
                    .getBody();
            String username = claims.getSubject();

            return AccountsRepository.getInstance().getAccount(username) != null;
        } catch (JwtException ex) {
            return false;
        }
    }

}
