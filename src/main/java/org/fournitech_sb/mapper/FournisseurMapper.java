package org.fournitech_sb.mapper;

import org.fournitech_sb.dto.FournisseurDto;
import org.fournitech_sb.model.Fournisseur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {
    @Mapping(target = "id", source = "id")
    FournisseurDto toDto(Fournisseur fournisseur);
    @Mapping(target = "id", ignore = true)
    Fournisseur toEntity(FournisseurDto fournisseurDto);
}
