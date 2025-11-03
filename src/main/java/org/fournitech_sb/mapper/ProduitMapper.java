package org.fournitech_sb.mapper;

import org.fournitech_sb.dto.ProduitDto;
import org.fournitech_sb.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    @Mapping(target = "id", source = "id")
    ProduitDto toDto(Produit produit);

    @Mapping(target = "id", ignore = true)
    Produit toEntity(ProduitDto produitDto);

}
