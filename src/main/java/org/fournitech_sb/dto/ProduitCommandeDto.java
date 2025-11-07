package org.fournitech_sb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProduitCommandeDto {
    Long id;
    Long produitId;

    @NotNull
    @Min(0)
    @Positive
    Integer quantite;
}
