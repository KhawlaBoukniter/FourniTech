package org.fournitech_sb.service.interfaces;

import org.fournitech_sb.dto.CommandeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommandeServiceInterface {
    CommandeDto createCommande(CommandeDto commandeDTO);
    CommandeDto getCommandeById(Long id);
    Page<CommandeDto> getAllCommandes(Integer page, Integer size, String sortBy, Boolean ascending);
    CommandeDto updateCommande(Long id, CommandeDto commandeDTO);
    void deleteCommande(Long id);
    CommandeDto annulerCommande(Long id);
    CommandeDto validerCommande(Long id);
}
