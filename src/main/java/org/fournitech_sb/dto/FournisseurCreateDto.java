package org.fournitech_sb.dto;

import lombok.Data;

@Data
public class FournisseurCreateDto {
    private String nom;
    private String societe;
    private String adresse;
    private String contact;
    private  String email;
    private String telephone;
    private String ville;
    private String ice;

}
