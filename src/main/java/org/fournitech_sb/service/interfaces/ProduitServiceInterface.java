package org.fournitech_sb.service.interfaces;

import org.fournitech_sb.dto.ProduitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProduitServiceInterface {
    ProduitDto createProduit(ProduitDto dto);
    ProduitDto getProduitById(Long id);
    Page<ProduitDto> getAllProduits(Pageable pageable);
    ProduitDto updateProduit(Long id, ProduitDto dto);
    void deleteProduit(Long id);

}
