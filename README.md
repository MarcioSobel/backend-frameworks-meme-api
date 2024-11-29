#### Equipe
- Márcio Cavalcanti Sobel - 01578025
- José Gabriel de Oliveira Lino - 01609620
- Carlos Alberto Ramalho Bezerra Neto - 01585045
- Monique Rafaela Carvalho Lopes - 01633424
- Carla Maria Santana Lopes - 01440665
- Gustavo Portela Pachêco - 01604533

#### Requisitos do projeto:
- :white_check_mark: Autenticação e Autorização stateless com JWT
- :white_check_mark: Utilizar um ORM para acesso aos dados
- :white_check_mark: Base de dados SQL com pelo menos duas tabelas de domínio (sem contar a tabela de usuários)
- :white_check_mark: Tratamento de exceção com mensagem padronizada de erros para o cliente
- :white_check_mark: Regras de negócio e casos de uso
- :white_check_mark: Separação do código em pelo menos 3 camadas: `Repository`, `Controller` e `Service` (ou caso de uso)
- :white_check_mark: DTOs e validação de seus campos
- :white_check_mark: Endpoint `POST`
- :white_check_mark: Endpoint `DELETE`
- :white_check_mark: Endpoint `PATCH` / `PUT`
- :white_check_mark: Endpoint `GET`
- :white_check_mark: Endpoint `GET` por algum parâmetro que não seja ID
- :white_check_mark: Testes unitários
- :white_check_mark: Documentação via Swagger
- :x: Deploy

#### Rodando o projeto
Primeiramente, é necessário ter o [Docker](https://www.docker.com/products/docker-desktop/) e o [Maven](https://maven.apache.org/) instalados.

Inicie o banco de dados via docker:
```
$ docker compose up -d
```
Inicie a aplicação Spring:
```
$ mvn spring-boot:run
```
Após a visualização rodar, você pode acessar e testar as rotas a partir da [documentação](http://localhost:8080/swagger-ui/index.html) (Link para o localhost).