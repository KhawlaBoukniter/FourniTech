package org.fournitech_sb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FournisseurDto {
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String societe;
    private String adresse;
    private String contact;

    @Email
    @NotNull
    private String email;
    private String telephone;
    private String ville;

    @NotBlank
    private String ice;
}
