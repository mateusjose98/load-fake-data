package com.example.loadfakedata.mock;

import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ENDERECO")
@Getter @Setter
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENDERECO_SEQ")
    @SequenceGenerator(name = "ENDERECO_SEQ", sequenceName = "ENDERECO_SEQ", allocationSize = 100, initialValue = 1)
    private Integer id;
    private String rua;
    private String numero;
    private String cidade;
    private String pais;

    public static Endereco fake(Faker faker) {
        Endereco endereco = new Endereco();
        endereco.setRua(faker.address().streetName());
        endereco.setNumero(faker.address().buildingNumber());
        endereco.setCidade(faker.address().city());
        endereco.setPais(faker.address().country());
        return endereco;
    }
}