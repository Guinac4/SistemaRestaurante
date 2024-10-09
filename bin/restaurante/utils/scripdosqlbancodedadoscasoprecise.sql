CREATE DATABASE db_restaurante;

USE db_restaurante;

CREATE TABLE pratos (
	id INT(10) auto_increment primary key,
    nome varchar(100) not null,
    descricao varchar(255) default null,
    preco decimal(10,2) not null,
    categoria varchar(50) not null
);

create table pedidos (
	id INT(10) auto_increment primary key,
    id_prato int(10) not null,
    quantidade int not null,
	mesa int not null,
    total decimal(10,2) not null,
    status varchar(1) default 0 not null,
    data_hora timestamp not null default current_timestamp,
    foreign key (id_prato) references pratos (id)
);

CREATE TABLE usuarios (
	id int(10) auto_increment primary key,
    nome varchar(200) not null,
    username varchar(50) not null,
    senha_hash varchar(255) not null
);

insert into usuarios (nome, username, senha_hash) values ('Admin', 'admin', sha2('admin123',256));

SELECT * FROM usuarios;