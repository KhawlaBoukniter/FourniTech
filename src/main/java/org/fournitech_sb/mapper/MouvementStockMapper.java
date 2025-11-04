package org.fournitech_sb.mapper;

import org.fournitech_sb.dto.MouvementStockDto;
import org.fournitech_sb.model.MouvementStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {
    MouvementStockDto toDto(MouvementStock mouvementStock);
    MouvementStock toEntity(MouvementStockDto mouvementStockDto);

}
