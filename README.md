## Load fake data in Postgres
Este projeto tem por objetivo popular uma base Postgres com tabelas Pessoa, Endereco e Emprestimo.



### Para rodar você precisará de: 
- Java 21
- Postgres (deixo um docker-compose.yml caso queira testar)
### Como executar (apenas o jar):
Com o seu banco rodando, java 21 no path, execute o comando a seguir, substituindo os valores de acordo com sua necessidade:
  ```
  java -DDB=fake_data -DDB_USER=postgres -DDB_PASS=P4ssword! -DDB_PORT=5444 -jar load-fake-in-postgres.jar 100 10
  ```
os parâmetros finais, no exemplo 100 e 10 (default 1000 e 10), são respectivamente a quantidade de pessoas/endereços que serão inseridas e de o tamanho do lote a ser inserido. A quantidade de empréstimos inserida segue uma regra definida no código :)

