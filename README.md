# DesafioCielo
Api criada baseada no sistema legado disponibilizado via e-mail

## Aquitetura
Arquitetura utilizada foi MVC

### Entidades
    Banco - dados bancários
    Remessa - dados da remessa
    Lancamento - Pagamento das remessas com aos dados bancários

## FrameworksLibsFerramentas
    [Spring Boot](httpswww.springbot.com)
    [Hibernate](httpshibernate.org)
    [Docker](httpsdocs.docker.com)
    [Liquibase](httpswww.liquibase.org)
    [Gradle](httpsgradle.org)
    [Lombok](httpsprojectlombok.org)
    [PostgreSQL](httpswww.postgresql.org)
    [JacksonJSON](httpswww.baeldung.comjackson)

## Como Usar
    Para utilizar a api
         com docker
             git clone httpsgithub.comadrmarciaoDesafioCielo.git
             Acessar pagina do projeto
             Com o docker instalado executar docker-compose up
         sem docker
             instalar postgres
             criar um banco de dados DB
             importar o projeto no intellij
             executar
            
## Variáveis de ambiente
    POSTGRES_DATABASE
    POSTGRES_DB
    POSTGRES_USER
    POSTGRES_PASSWORD

## API REST
Obs Porta de execução da API 8080.
  Banco
     apibanco [PUTPOST]
     apibanco{id} [GET]
     apibanco{page}{limit} [GET]
     apibanco{id} [DELETE]
  Lancamento
     apilancamento [PUTPOST]
     apilancamento{id} [GET]
     apilancamento{page}{limit} [GET]
     apilancamento{id} [DELETE]
  Remessa
      apiremessa [PUTPOST]
      apiremessa{id} [GET]
      apiremessa{page}{limit} [GET]
      apiremessa{id} [DELETE]
    
   