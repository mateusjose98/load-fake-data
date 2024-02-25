package com.example.loadfakedata.mock;

import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "Emprestimo")
@Getter
@Setter
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Emprestimo_SEQ")
    @SequenceGenerator(name = "Emprestimo_SEQ", sequenceName = "Emprestimo_SEQ", allocationSize = 100, initialValue = 1)
    private Integer id;
    private String descricao;
    private Date data;
    private Double valor;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public static Emprestimo fake(Faker faker, Random random, Pessoa pessoa) {
        Emprestimo ep1 = new Emprestimo();
        ep1.setValor(random.nextDouble() * 1000);
        ep1.setPessoa(pessoa);
        ep1.setDescricao(faker.food().ingredient());
        ep1.setData(faker.date().birthday());
        return ep1;
    }
}
