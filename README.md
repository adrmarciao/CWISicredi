# Aplicativo de Eventos CWI - Sicredi

    Aplicativo foi desenvolvido para exibição de dados de eventos disponível pela seguinte api:  
     - [GET Eventos](http://5f5a8f24d44d640016169133.mockapi.io/api/events)
     - [GET Evento](http://5f5a8f24d44d640016169133.mockapi.io/api/events/1)
     - [POST Checkin](http://5f5a8f24d44d640016169133.mockapi.io/api/checkin)

## Estrutura de pastas

    O Aplicativo foi desenvolvido em dois modulos. O Modulo principal com Layout, ViewModel e 
    Repository e o modulo de network onde está centralizado toda a logica de consumo da api de 
    eventos.
    
    Estrutura de pacotes:
    App
    - module, este pacote compreende informações para injeção de dependencia;
    - repository, este pacote compreende a camada de dominio da aplicação que tem por objetivo 
      encapsular regras e realizar solicitação de dados a camada de network(camada reponsável pela 
      requisição de dados da api);
    - view, este pacote encapsula fragmentos, activies e adapter;
    - viewmodel, este pacote conversa diretamente com a camada de repository e nele compreende a 
      as regras para a renderização das telas;
     
    Network
     - dto, pacote que implementa o padrão "data transfer object" e tem como objetivo fazer a 
       transferência de dados entre o modulo de Network e App;
     - exception, pacote para o tratamento de erros de rede;
     - rest, encapsula as interfaces do retrofit para realizar as requisições;
     - service, pacote com classes responsável por realizar as requisições usando retrofit
     
     Obs: o sistema possui um cache implementado pelo protocolo HTTP denominado HTTP Caching.

## Arquitetura

    <img src="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel#/media/File:MVVMPattern.png"  width="800" height="200">

## Library
    [AndroidX](httpswww.springbot.com)
    [Retrofit](httpshibernate.org)

## Instrução
            
## Testes
   

## API REST

    
   