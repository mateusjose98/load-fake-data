package com.example.loadfakedata.mock;

import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Indexed;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "PESSOA", indexes = {
        @Index(
                name = "cpf_idx",
                columnList = "cpf"
        )
})
@Getter
@Setter
@ToString
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Pessoa_SEQ")
    @SequenceGenerator(name = "Pessoa_SEQ", sequenceName = "Pessoa_SEQ", allocationSize = 100, initialValue = 1)
    private Integer id;
    private String nome;
    private String cpf;
    private String genero;
    private String email;
    private Date nascimento;
    private String nomeMae;
    private String preferencia;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public static Pessoa fake(Faker faker, Endereco e) {
        Pessoa pessoa = new Pessoa();
        pessoa.setPreferencia(faker.friends().character());
        pessoa.setNome(faker.name().fullName());
        pessoa.setNascimento(faker.date().birthday(1, 100));
        pessoa.setNomeMae(faker.name().nameWithMiddle());
        pessoa.setCpf(faker.idNumber().valid());
        pessoa.setGenero(faker.options().option("M", "F"));
        pessoa.setEmail(faker.internet().emailAddress());
        pessoa.setEndereco(e);
        return pessoa;
    }

}