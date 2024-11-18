package com.erkan.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    @NotBlank(message = "Ad alanı boş bırakılamaz")
    private String firstName;

    @NotBlank(message = "Soyad alanı boş bırakılamaz")
    private String lastName;

    @Email(message = "Geçerli bir email adresi giriniz")
    @NotBlank(message = "Email alanı boş bırakılamaz")
    private String email;

    @NotBlank(message = "Şifre alanı boş bırakılamaz")
    private String password;
}
