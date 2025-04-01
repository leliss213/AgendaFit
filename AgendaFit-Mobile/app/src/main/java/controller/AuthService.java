package controller;

import controller.DTO.LoginRequestDTO;
import controller.DTO.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/api/users/auth/login")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO requestDTO);
}
