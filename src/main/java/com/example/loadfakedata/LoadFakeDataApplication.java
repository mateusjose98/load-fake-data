package com.example.loadfakedata;

import com.example.loadfakedata.mock.*;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@SpringBootApplication
@Slf4j
public class LoadFakeDataApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LoadFakeDataApplication.class, args);
    }

    Faker faker = new Faker();
    Random random = new Random();

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final EmprestimoRepository emprestimoRepository;

    public LoadFakeDataApplication(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository, EmprestimoRepository emprestimoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    @Transactional // args[0] = número de registros, args[1] = step (args[0] / 1000)
    @Override
    public void run(String... args) throws Exception {

        var MAX_DATA = args.length == 0 || args[0] == null ? 1000 : Integer.valueOf(args[0]);
        var STEP =  args.length == 0  || args[1] == null ? 10 : Integer.valueOf(args[1]);

        System.out.println(" >>> INSERINDO " + MAX_DATA + " registros!");
        esperar(4);
        long in = System.currentTimeMillis();
        List<Endereco> end = new ArrayList<>();
        for (int i = 1; i <= MAX_DATA; i++) {
            Endereco endereco = Endereco.fake(faker);
            end.add(endereco);
            if(i % STEP == 0) {
                log.info("Bloco de Enderecos size {}", end.size());
                List<Endereco> finalEnd = end;
                Thread.ofVirtual().name("Virtual").start(() -> {
                    List<Endereco> enderecos = enderecoRepository.saveAll(finalEnd);
                    enderecoRepository.flush();
                    List<Pessoa> pes = new ArrayList<>();
                    for (Endereco e : enderecos) {
                        pes.add(Pessoa.fake(faker, e));
                    }
                    pessoaRepository.saveAll(pes).forEach(pessoa -> {
                        if (pessoa.getNome().startsWith("A") || pessoa.getNome().startsWith("B") || pessoa.getNome().startsWith("Z")) {
                            Emprestimo ep1 = Emprestimo.fake(faker, random, pessoa);
                            emprestimoRepository.save(ep1);
                        }
                    });
                });

                esperar(1);
                end = new ArrayList<>();

            }
        }

        System.out.println(" >>> FINALIZANDO INSERÇÃO DE " + MAX_DATA + " registros!");
        System.out.println(" >>> TEMPO " + (System.currentTimeMillis() - in) + " ms");
        esperar(4);

    }

    private static void esperar(long t) {
        try {
            Thread.sleep(1000* t);
        }catch (InterruptedException e) {
            throw new RuntimeException(":/");
        }
    }


}
