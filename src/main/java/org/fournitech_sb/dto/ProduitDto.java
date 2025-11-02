package org.fournitech_sb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProduitDto {
    Long id;

    @NotBlank
    String nom;
    String description;

    @Positive
    @Min(0)
    @NotNull
    Double prixUnit;

    @NotBlank
    String categorie;

    @Positive
    @Min(0)
    @NotNull
    Integer stockActuel;
}
