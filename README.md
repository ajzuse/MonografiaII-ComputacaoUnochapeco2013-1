MonografiaII ComputacaoUnochapeco2013-1
=======================================

## How to use?

* You should clone git repo, init and update submodules:

```
git clone git@github.com:ajzuse/MonografiaII-ComputacaoUnochapeco2013-1.git
cd MonografiaII-ComputacaoUnochapeco2013-1
git submodule update --init --recursive
```


## Download do Arquivo JAR

* Você pode baixar o arquivo jar do servidor em:

```
https://www.dropbox.com/s/0p8hrbuda2qmpij/Monografia-Server_Side.rar
```

* As requisições devem ser feitas no formato:

```
http://localhost:90?usuario=xxx&senha=yyy&info=[notas|horarios|materiais]
```

Caso não seja informado o parametro info, todas as informações serão retornadas

## Cliente REST

*Para quem usa o Google Chrome, pode verificar os retornos do Servidor utilizando o Advanced Rest Client

```
https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo
```
