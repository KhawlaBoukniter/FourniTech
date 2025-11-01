package org.fournitech_sb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fournitech_sb.model.StatutCommande;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CommandeDto {
    Long id;

    @NotNull
    LocalDate dateCommande;

    @NotNull
    Long fournisseurId;
    List<ProduitCommandeDto> produitCommandes;

    @NotNull
    @Min(0)
    @Positive
    Double prix;
    StatutCommande statutCommande;
}
