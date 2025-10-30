package org.fournitech_sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "fournisseurs")
@Data
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String societe;
    private String adresse;
    private String contact;

    @Column(unique = true)
    private  String email;
    private String telephone;
    private String ville;

    @Column(unique = true)
    private String ice;
}
