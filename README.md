# search-cpf
Projeto que disponibiliza um web service para consulta de CPF's.

A Consulta deve ser feita seguindo o seguinte padrão:
search-doc/service/cpf-só-numeros/data-nascimento-so-numeros(ddMMyyyy)

A demonstração da aplicação funcionando pode ser encontrada em http://186.202.45.156:8080/search-doc

O dados retornados são semelhantes a:
```json
{  
   "numeroCpf":"xxx.xxx.xxx-xx",
   "nomePessoaFisica":"Fulano de Tal dos Anzóis Pereira e Silva",
   "dataNascimento":"dd/MM/yyyy",
   "situacaoCadastral":"REGULAR",
   "dataInscricao":"dd/MM/yyyy",
   "digitoVerificador":"00",
   "constaObito":"NÃO",
   "dataEmissao":"dd/MM/yyyy",
   "codigoControleComprovante":"0413.C589.9FC2.211B"
}
```
