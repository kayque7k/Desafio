# README #

Estes documento README tem como objetivo fornecer as informações necessárias do que foi usado no projeto.

### Dependencias ###

* dependências de material, para seguir o padrao de material designer imposto pela google.

* dependências de lifecycle, foram utilizadas para ter acesso as viewmodel, acesso independente da reconstrucao de tela,
eu sempre vou ter acesso as minhas viewmodel, porem nao utilizei o livedata por utilizar o flow com o collectAsState que se encaixa e é indicada para a programacao reativa com o compose

* dependências de retrofit, realizar chamadas externa http ou https para o servidor e recuperar os dados (so como demonstrativo, pois os dados estao mockados nos Responses, por nao ter chamadas para consultar, mas caso tiver e so implementar e mudar no registro de injacao de dependencia)

* dependências de assertj, realizar testes unitarios para comparacoes de valores e objetos

* dependências de mockito, realizar testes unitarios para validar se as devidas chamadas foram feitas e mocar objetos e chamadas

* dependências de mockk, realizar testes unitarios para mocar objetos

* dependências de koin, realizar injecao de dependencia e inversao de controle para diminuir o acoplamento dos dados

### oque melhoraria ###

* tornaria os pacotes de commons,coreapi e dsc como modulos independentes.


### Oque ha no projeto ###

* (x) Testes unitários.
* (x) arquitetura: Utilizei o MVVM seguindo o clean architecture, por se encaixa com o padrao de programacao reativa do compose juntamente com o flow com o collectAsState,
    e com a viewmodel do lifecycle, sendo uma arquitetura de facil teste ja que todas as logicas ficaram na viewmodel;
* (x) Material Design: os componentes utilizando o compose tambem seguem fortemente o padrao de Material Design, e ja compoem acessibilidade, os que nao compoem como imagem foi implementado;
* (x) Utilizar alguma ferramenta de Injeção de Dependência: Koin
* (x) Utilizei o Flow com o collectAsState ao inves do LiveData para a programacao reativa por se encaixar melhor com o compose e tambem Coroutines para as chamadas externa com funcoes suspend (Caso um dia seja impelementada endpoint para os usecase);
