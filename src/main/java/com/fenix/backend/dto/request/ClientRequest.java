package com.fenix.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Size(max = 20) String phone,
        @NotBlank @Size(min = 11, max = 14) String cpf
) {}
