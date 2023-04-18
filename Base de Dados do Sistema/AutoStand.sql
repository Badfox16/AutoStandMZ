-- criar uma base de dados 
create database bdAutoStand;

use bdAutoStand;

-- criar tabela para os usuarios
create table tbUsuarios(
IDUser int primary key auto_increment,
Nome varchar(30) not null,
Apelido varchar(30) not null,
Telefone varchar(15),
Username varchar(30) not null unique,
Senha varchar(20) not null
);

-- criar uma tabela para os carros
create table tbCarros(
IDCarro int primary key auto_increment,
Marca varchar(35) not null,
Modelo varchar(35) not null,
Matricula varchar(20) unique,
Preco int(10),
Status varchar (30)
);
-- criar uma tabela para registrar o aluguer 
create table tbAluguer(
Codigo int unique primary key auto_increment,
IDCarro int,
IDCliente int,
Taxa int,
Data_Aluguer varchar(200),
Data_Devolucao varchar(200)
); 
-- criar uma tabela para os clientes
create table tbClientes(
 IDCliente int primary key auto_increment,
Nome varchar (50), 
BI varchar (30) unique, 
Telefone varchar (15) 
);

-- insercao de dados na tabela (CREATE)
insert into tbUsuarios(Nome,Apelido,Telefone,Username,Senha)
values('Administrator', 'Root', null,'Admin', 'admin123');


 insert into tbUsuarios(Nome,Apelido,Telefone,Username,Senha)
 values('Mutizo','Maita', '866696797', 'mutizom6', '123456');
 insert into tbCarros(Marca, Modelo, Matricula, Preco, Disponivel)
values ("Toyota","Allion","Branco","AEU-399 MC",200,"Ocupado");
 insert into tbCarros(Marca, Modelo, Matricula, Preco, Disponivel)
values ("Toyota","Supra","Preto","EAA-328 SF",500,"Disponivel");

-- leitura dos dados na tabela (READ)
select * from tbUsuarios;


select * from tbCarros;
select * from tbAluguer;
select * from tbClientes;

update tbCarros set Status = 'Disponivel' where IDCarro = 3;
select IDCarro from tbCarros order by IDCarro;
