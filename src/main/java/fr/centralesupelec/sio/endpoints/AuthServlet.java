package fr.centralesupelec.sio.endpoints;

import fr.centralesupelec.sio.data.AccountsRepository;
import fr.centralesupelec.sio.endpoints.utils.ResponseHelper;
import fr.centralesupelec.sio.model.Account;
import fr.centralesupelec.sio.model.Token;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An authentication servlet for the API.
 * Clients must obtain an access token with this endpoint, then they sign subsequent requests to other data endpoints with it.
 * This servlet MUST NOT be matched by the {@link AuthFilter}!
 */
// This annotation replaces the registration of the servlet and its URL mappings in web.xml.
@WebServlet(urlPatterns = "/auth/token")
public class AuthServlet extends HttpServlet {

    // This method will be called in case of a POST request on the URL in the mapping.
    // All methods in a servlet receive the request (req) as a parameter.
    // They don't return anything, instead they are supposed to write to the provided response (resp).
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Clients are expected to send credentials in query parameters, or using form-data.
        // Both are accessible by the getParameter() method.
        String username = req.getParameter("username");
        if (username == null || username.isEmpty()) {
            ResponseHelper.writeError(
                    resp,
                    "Missing username",
                    resp.SC_BAD_REQUEST // HTTP standard code 400 to indicate a client error
            );
            return;
        }
        // Check if the username looks like an email (using library Apache Commons).
        if (!EmailValidator.getInstance().isValid(username)) {
            ResponseHelper.writeError(resp,"Invalid username", resp.SC_BAD_REQUEST);
            return;
        }

        String password = req.getParameter("password");
        if (password == null || password.isEmpty()) {
            ResponseHelper.writeError(resp, "Missing password", resp.SC_BAD_REQUEST);
            return;
        }

        // Check that the account exists
        Account account = AccountsRepository.getInstance().getAccount(username);
        if (account == null) {
            ResponseHelper.writeError(resp,"Invalid credentials", resp.SC_UNAUTHORIZED);
            return;
        }
        // Check the password
        // TODO: Better check with a hash
        if (!account.getPasswordHash().equals(password)) {
            // normally the application will send the password hashed in md5 but now since its an api without other interaction the password must be in md5
            ResponseHelper.writeError(resp,"Invalid credentials, please encrypte your password in MD5 before", resp.SC_UNAUTHORIZED);
            return;
        }

        // Generate a successful Token response
        Token token = new Token();
        token.setAccessToken(AuthManager.generateAccessToken(username));
        ResponseHelper.writeJsonResponse(resp, token);

    }

}
