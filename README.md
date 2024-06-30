
<h1 align="center" style="font-weight: bold;">Barbearia GS</h1>


</br>
<div align="center">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="spring"/>
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java"/>
  <img src="https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white" alt="bootstrap"/>
    <img src="https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white" alt="thymeleaf"/>
  <img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="mysql"/>
  <img src="https://img.shields.io/badge/postgresql-%23336791.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="postgresql"/>
 
</div>

</br>
<p align="center">
 <a href="#started">Rodando Localmente</a> • 
<a href="#deploy">Deploy</a> • 
 <a href="#contribute">Contribuindo</a>
</p>
</br>


<p align="center">
  <img src="https://github.com/friche11/Barbearia-GS/assets/104036970/6d827ce9-2146-4a9b-bc06-0c2a991079c0" alt="project image" width="900"/>
</p>
</br>
<h2 id="about">📌 Sobre</h2>
<p >
  <b>O projeto da Barbearia GS é um sistema de gerenciamento e agendamento para ajudar a administrar as operações diárias da barbearia. O sistema foi feito para o dono de uma barbearia e visa melhorar a eficiência operacional, proporcionando uma experiência mais organizada tanto para os clientes quanto para os funcionários.</b>
</p>

<h2 id="started">🚀 Rodando localmente</h2>

<h3>Pré-requisitos</h3>

Ter instalado todas as tecnologias abaixo:

- [Git](https://git-scm.com/downloads)
- [SDK 17](https://www.oracle.com/br/java/technologies/downloads/#java17)
- [MySQL](https://dev.mysql.com/downloads/)

<h3>Clonando</h3>

Abra o git bash em alguma pasta e insira o comando abaixo para clonar o repositório

```bash
git clone https://github.com/friche11/Barbearia-GS.git
```

<h3> Configurações cruciais</h2>

Abra o MySQL e crie uma database no mysql executando o comando abaixo
```sql
create database barbeariags;
```
Mude o nome de usuário e a senha do `application.properties` para seu nome de usuário e sua senha do MySQL. Para isso, vá até as pastas `src/main/resources`
</br>


<h3>Executando o projeto</h3>

Abra o projeto no Visual Studio Code e execute os comandos abaixo em um novo terminal para rodar o projeto
```bash
mvn spring-boot:run
```
Se você não tiver o maven instalado, execute
```bash
./mvnw spring-boot:run
```
Se o Visual Studio Code não pedir para instalar as extensões automaticamente, instale manualmente as que estão abaixo e execute novamente o projeto.
</br> </br>
-Debugger for Java </br>
-Extension Pack for Java </br>
-Maven for Java </br>
-Project Manager for Java </br>
-Test Runner for Java </br>
-MySQL Management Tool</br> </br>
Pronto, o projeto estará rodando na porta 8080!

<h2 id="deploy">🚀 Deploy</h2>
O deploy foi feito utilizando 1 web service e 1 database de PostgreSQL no https://render.com
</br>
</br>
URL da aplicação: https://barbearia-deploy.onrender.com/
</br>
</br>

Como os servidores do render caem quando ficam inativos na versão gratuita, recomendo ver o funcionamento da aplicação pelo vídeo no youtube: https://youtu.be/u0tfqU2uwKY


<h2 id="contribute">📫 Contribuindo</h2>

Se você deseja contribuir, clone esse repositório, crie sua branch e coloque a mão na massa!

1. `git clone https://github.com/friche11/Barbearia-GS.git`
2. `git checkout -b feature/NAME`
3. No final, abra um Pull Request explicando o problema/melhoria identificado, o que foi feito para resolver e screenshots das alterações visuais :)


[📝 Como fazer um Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Padrões de Commit](https://github.com/iuricode/padroes-de-commits)
