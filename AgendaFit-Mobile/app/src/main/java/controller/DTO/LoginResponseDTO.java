package controller.DTO;

import java.util.UUID;

public class LoginResponseDTO {
    private UUID token;

    public LoginResponseDTO(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

}
