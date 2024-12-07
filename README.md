# Structo

Para executar o projeto sem ser no ambiente
de testes o container no PostgreSQL 
pode ser configurado usando o arquivo 
[HELP.md](./HELP.md), as variáveis de 
ambiente devem ser configuradas no `.env` conforme o [.env.example](./.env.example)

## Repositório com testes End-ToEnd

[StructoTest](https://github.com/LastBoxLabel/StructoTest)


## Para executar

1. Instale as dependências e plugins:

```shell
mvn install
```

2. Para executar o Jacoco:

```
mvn clean test jacoco:report 
```

3. Para abrir o relatório:

[Relatório](./target/jacoco-report/index.html)

## Observalção

Para não precisar de executar o Ollama deixei um WireMock para que possa utilizar no ambiente de testes configurado