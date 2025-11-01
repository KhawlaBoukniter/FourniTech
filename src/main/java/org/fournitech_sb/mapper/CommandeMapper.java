package org.fournitech_sb.mapper;

import org.fournitech_sb.dto.CommandeDto;
import org.fournitech_sb.model.Commande;
import org.fournitech_sb.model.Fournisseur;
import org.fournitech_sb.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { ProduitCommandeMapper.class})
public interface CommandeMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fournisseurId", source = "fournisseur.id")
    @Mapping(target = "produitCommandes", source = "produitCommandes")
    @Mapping(target = "dateCommande", source = "dateCommande")
    @Mapping(target = "prix", source = "prix")
    CommandeDto toDto(Commande commande);

    @Mapping(target = "fournisseur", source = "fournisseurId", qualifiedByName = "fournisseurFromId")
    @Mapping(target = "produitCommandes", source = "produitCommandes")
    Commande toEntity(CommandeDto commandeDto);

    @Named("fournisseurFromId")
    default Fournisseur fournisseurFromId(Long id) {
        if (id == null) return null;
        Fournisseur f = new Fournisseur();
        f.setId(id);
        return f;
    }

}
