package br.com.erudio.controllers.docs;

import br.com.erudio.data.dto.security.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface AuthControllerDoc {

    @Operation(summary = "Authenticates an user and returns a token")
    ResponseEntity<?> signIn(AccountCredentialsDTO credentials);

    @Operation(summary = "Refresh token for authenticated user and returns a new token")
    ResponseEntity<?> refreshToken(String username, String refreshToken);

    AccountCredentialsDTO create(AccountCredentialsDTO credentials);
}
