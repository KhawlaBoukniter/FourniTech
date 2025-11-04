package org.fournitech_sb.dto;

import lombok.Data;
import org.fournitech_sb.model.TypeMouvement;

import java.time.LocalDate;

@Data
public class MouvementStockDto {
    Long id;
    LocalDate dateMouvement;
    Integer quantiteMouvement;
    TypeMouvement typeMouvement;
    ProduitDto produitDto;
    CommandeDto commandeDto;
}
