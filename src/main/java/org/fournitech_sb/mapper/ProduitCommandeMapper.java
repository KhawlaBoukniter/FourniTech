package org.fournitech_sb.mapper;

import org.fournitech_sb.dto.ProduitCommandeDto;
import org.fournitech_sb.model.Produit;
import org.fournitech_sb.model.ProduitCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProduitCommandeMapper {
    @Mapping(target = "produitId", source = "produit.id")
    ProduitCommandeDto toDto(ProduitCommande produitCommande);

    @Mapping(target = "produit", source = "produitId", qualifiedByName = "produitFromId")
    @Mapping(target = "commande", ignore = true)
    ProduitCommande toEntity(ProduitCommandeDto produitCommandeDto);

    @Named("produitFromId")
    default Produit produitFromId(Long id) {
        if (id == null) return null;
        Produit p = new Produit();
        p.setId(id);
        return p;
    }
}
