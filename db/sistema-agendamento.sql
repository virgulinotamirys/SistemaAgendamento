drop schema if exists sistema_agendamento;
create schema sistema_agendamento;
use sistema_agendamento;

CREATE TABLE estado (
  nome VARCHAR(20) NOT NULL,
  uf_id CHAR(2) NOT NULL,
  PRIMARY KEY (uf_id)
);

CREATE TABLE cidade (
  cidade_id INT NOT NULL,
  nome VARCHAR(20) NOT NULL,
  estado_uf_id CHAR(2) NOT NULL,
  PRIMARY KEY (cidade_id),
  CONSTRAINT fk_cidade_estado1 FOREIGN KEY (estado_uf_id) REFERENCES estado (uf_id)
);

create view vw_cidade_estado as
select c.cidade_id, e.nome
from cidade c
inner join estado e on c.estado_uf_id = e.uf_id;

select * from vw_cidade_estado;


CREATE TABLE endereco (
  end_id INT NOT NULL,
  longrad VARCHAR(100) NOT NULL,
  bairro VARCHAR(20) NULL DEFAULT NULL,
  cep VARCHAR(20) NOT NULL,
  comp VARCHAR(50) NULL DEFAULT NULL,
  cidade_cid INT NOT NULL,
  PRIMARY KEY (end_id),
  CONSTRAINT fk_endereco_cidade1 FOREIGN KEY (cidade_cid) REFERENCES cidade (cidade_id)
);


create view vw_cidade_endereco as
select e.end_id, e.longrad, e.bairro, e.cep, e.comp, c.nome
from endereco e
inner join cidade c on e.cidade_cid = c.cidade_id;

select * from vw_cidade_endereco;


/* ------------------------ */

CREATE TABLE subcategoria (
  subcategoria_id INT NOT NULL,
  nome VARCHAR(50) NOT NULL,
  PRIMARY KEY (subcategoria_id)
);

CREATE TABLE curso (
  curso_id INT NOT NULL,
  categoria_id VARCHAR(20) NOT NULL,
  subcategoria_subcategoria_id INT NOT NULL,
  PRIMARY KEY (curso_id),
  CONSTRAINT fk_curso_subcategoria1 FOREIGN KEY (subcategoria_subcategoria_id) REFERENCES subcategoria (subcategoria_id)
);


create view vw_curso as
select c.curso_id, c.categoria_id, sub.nome
from curso c
inner join subcategoria sub on c.subcategoria_subcategoria_id = sub.subcategoria_id;

select * from vw_curso;

/* ------------------------ */

CREATE TABLE usuario (
  usuario_id INT NOT NULL,
  nome VARCHAR(100) NOT NULL,
  celular VARCHAR(12) NOT NULL,
  telefone VARCHAR(13) NULL DEFAULT NULL,
  email VARCHAR(100) NOT NULL,
  dataNascimento DATE NULL DEFAULT NULL,
  genero CHAR(6) NOT NULL,
  endereco_end_id INT NOT NULL,
  PRIMARY KEY (usuario_id),
  CONSTRAINT fk_usuario_endereco1 FOREIGN KEY (endereco_end_id) REFERENCES endereco (end_id)
);


create view vw_usuario_end as
select u.usuario_id, u.endereco_end_id, u.nome, e.longrad, e.bairro, e.cep
from usuario u
inner join endereco e on u.endereco_end_id = e.end_id;


CREATE TABLE acesso_usuario (
  email VARCHAR(100) NOT NULL,
  login VARCHAR(50) NULL DEFAULT NULL,
  senha VARCHAR(40) NOT NULL,
  tipo VARCHAR(20) NOT NULL,
  flag TINYINT(1) NULL DEFAULT 0,
  acesso_usuario_id INT NOT NULL,
  usuario_id INT NOT NULL,
  PRIMARY KEY (acesso_usuario_id),
  CONSTRAINT fk_acesso_usuario_usuario1 FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
);


CREATE TABLE log_acesso_usuario (
  email VARCHAR(100) NOT NULL,
  login VARCHAR(50) NULL DEFAULT NULL,
  senha VARCHAR(40) NOT NULL,
  tipo VARCHAR(20) NOT NULL,
  flag TINYINT(1) NULL DEFAULT 0,
  acesso_usuario_id INT NOT NULL,
  usuario_id INT NOT NULL,
  data_acesso timestamp NOT NULL
);

/*use sistema_agendamento;
DELIMITER $$
CREATE PROCEDURE select_login_usuario(in p_login varchar(50), in p_senha varchar(40), out yes_no int)
BEGIN
	set yes_no = (select count(*) from acesso_usuario where login=p_login && senha=p_senha);
    if yes_no > 0 then begin
    insert into log_acesso_usuario(login, senha, data_acesso) values(p_login, p_senha, now());
    END; end if;
    select yes_no;
END $$
DELIMITER ;*/

DELIMITER $$
CREATE PROCEDURE select_login_usuario(in p_login varchar(50), in p_senha varchar(40))
BEGIN
	select count(*), email, login, senha, tipo, flag, acesso_usuario_id, usuario_id into @logado, @email, @login, @senha, @tipo, @flag, @acesso_usuario_id, @usuario_id from acesso_usuario where login=p_login && senha=p_senha;
	if @logado > 0 
	then begin
		insert into log_acesso_usuario(email, login, senha, tipo, flag, acesso_usuario_id, usuario_id, data_acesso) values(@email, @login, @senha, @tipo, @flag, @acesso_usuario_id, @usuario_id, now());
    end;
	end if;
	select @logado;
END $$
DELIMITER ;


create view vw_acess_usuario as
select a.acesso_usuario_id, a.email, u.nome
from acesso_usuario a
inner join usuario u on a.usuario_id = u.usuario_id;


select * from vw_usuario_end;
select * from vw_acess_usuario;

/* ------------------------ */

CREATE TABLE publicacao (
  publicacao_id INT NOT NULL,
  titulo TEXT(100) NOT NULL,
  descricao LONGTEXT NOT NULL,
  data_p DATETIME NOT NULL,
  usuario_id INT NOT NULL,
  acesso_usuario_id INT NOT NULL,
  PRIMARY KEY (publicacao_id),
  CONSTRAINT fk_publicacao_usuario1 FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
  CONSTRAINT fk_publicacao_usuario2 FOREIGN KEY (acesso_usuario_id) REFERENCES acesso_usuario (acesso_usuario_id)
);


create view vw_publicacao as
select p.publicacao_id, p.titulo, p.descricao, p.data_p, u.nome, a.email
from publicacao p
join usuario u on p.usuario_id = u.usuario_id
join acesso_usuario a on p.acesso_usuario_id = a.acesso_usuario_id;

select * from vw_publicacao;

/* ------------------------ */

CREATE TABLE imagem (
  imagem_id INT NOT NULL,
  descricao TEXT NOT NULL,
  url BLOB NOT NULL,
  tipo VARCHAR(20) NOT NULL,
  publicacao_publicacao_id INT NOT NULL,
  PRIMARY KEY (imagem_id),
  CONSTRAINT fk_imagem_publicacao1 FOREIGN KEY (publicacao_publicacao_id) REFERENCES publicacao (publicacao_id)
);

create view vw_imagem_pub as
select i.imagem_id, p.titulo, p.descricao
from imagem i
inner join publicacao p on i.publicacao_publicacao_id = p.publicacao_id;


select * from vw_imagem_pub;

/* ------------------------ */

CREATE TABLE comentario (
  comentario_id INT NOT NULL,
  descricao LONGTEXT NOT NULL,
  horario DATETIME NULL DEFAULT NULL,
  usuario_usuario_id INT NOT NULL,
  publicacao_publicacao_id INT NOT NULL,
  PRIMARY KEY (comentario_id),
  CONSTRAINT fk_comentario_usuario1 FOREIGN KEY (usuario_usuario_id) REFERENCES usuario (usuario_id),
  CONSTRAINT fk_comentario_publicacao1 FOREIGN KEY (publicacao_publicacao_id) REFERENCES publicacao (publicacao_id)
);

create view vw_comentario as
select c.comentario_id, u.nome, p.titulo, p.descricao
from comentario c
join usuario u on c.usuario_usuario_id = u.usuario_id
join publicacao p on c.publicacao_publicacao_id = p.publicacao_id;

select * from vw_comentario;

/* ------------------------ */

CREATE TABLE agendamento (
  agendamento_id INT AUTO_INCREMENT NOT NULL,
  diasemana VARCHAR(20) NOT NULL,
  horaInicial VARCHAR(20) NOT NULL,
  horaFinal VARCHAR(20) NOT NULL,
  PRIMARY KEY (agendamento_id)
);

create view vw_agendamento as
select *
from agendamento;

DELIMITER $$
CREATE PROCEDURE insert_agendamento(in p_diasemana varchar(20), in p_horaInicial varchar(20), in p_horaFinal varchar(20))
BEGIN
	set autocommit = 0;
	start transaction;
		insert into agendamento(diasemana, horaInicial, horaFinal) values (p_diasemana, p_horaInicial, p_horaFinal);
        set @agendamento_id = last_insert_id();
		insert into log_agendamento(agendamento_id, diasemana, horaInicial, horaFinal, data_registro) values (@agendamento_id, p_diasemana, p_horaInicial, p_horaFinal, now());
	commit;
END $$
DELIMITER ;

CREATE TABLE log_agendamento (
  /*usuario_id INT NOT NULL,*/
  agendamento_id INT NOT NULL,
  diasemana VARCHAR(20) NOT NULL,
  horaInicial VARCHAR(20) NOT NULL,
  horaFinal VARCHAR(20) NOT NULL,
  data_registro timestamp NOT NULL
);

/*--------- */


CREATE TABLE agendamento_subcategoria (
  agendamento_agendamento_id INT NOT NULL,
  subcategoria_subcategoria_id INT NOT NULL,
  PRIMARY KEY (agendamento_agendamento_id, subcategoria_subcategoria_id),
  CONSTRAINT fk_agendamento_has_subcategoria_agendamento1 FOREIGN KEY (agendamento_agendamento_id) REFERENCES agendamento (agendamento_id),
  CONSTRAINT fk_agendamento_has_subcategoria_subcategoria1 FOREIGN KEY (subcategoria_subcategoria_id) REFERENCES subcategoria (subcategoria_id)
);


create view vw_agend_sub as
select agen_sub.agendamento_agendamento_id, a.diasemana, subcategoria_id
from agendamento_subcategoria agen_sub
join agendamento a on agen_sub.agendamento_agendamento_id = a.agendamento_id
join subcategoria s on agen_sub.subcategoria_subcategoria_id = s.subcategoria_id;

select * from vw_agend_sub;

/* ------------------------ */

CREATE TABLE usuario_agendamento (
  usuario_agendamento_id INT NOT NULL,
  agendamento_agendamento_id INT NOT NULL,
  PRIMARY KEY (usuario_agendamento_id, agendamento_agendamento_id),
  CONSTRAINT fk_usuario_has_agendamento_usuario1 FOREIGN KEY (usuario_agendamento_id) REFERENCES usuario (usuario_id),
  CONSTRAINT fk_usuario_has_agendamento_agendamento1 FOREIGN KEY (agendamento_agendamento_id) REFERENCES agendamento (agendamento_id)
);

DELIMITER $$
CREATE PROCEDURE insert_insere_agendamento(in p_usuario_id int, in p_agendamento_id int, in p_diasemana varchar(20), in p_horaInicial time, in p_horaFinal time)
BEGIN
	set autocommit = 0;
	start transaction;
		insert into agendamento(agendamento_id, diasemana, horaInicial, horaFinal) values (p_agendamento_id, p_diasemana, p_horaInicial, p_horaFinal);
		insert into usuario_agendamento(usuario_agendamento_id, agendamento_agendamento_id) values (p_usuario_id, p_agendamento_id);
		insert into log_agendamento(usuario_id, agendamento_id, diasemana, horaInicial, horaFinal, data_registro) values (p_usuario_id, p_agendamento_id, p_diasemana, p_horaInicial, p_horaFinal, now());
	commit;
END $$
DELIMITER ;


create view vw_user_agend as
select user_agend.usuario_agendamento_id, u.nome, a.diasemana
from usuario_agendamento user_agend
join usuario u on user_agend.usuario_agendamento_id = u.usuario_id
join agendamento a on user_agend.agendamento_agendamento_id = a.agendamento_id;

select * from vw_user_agend;

/* ------------------------ */

CREATE TABLE acesso_usuario_curso (
  acesso_usuario_curso_id INT NOT NULL,
  acesso_usuario_id INT NOT NULL,
  curso_id INT NOT NULL,
  PRIMARY KEY (acesso_usuario_curso_id),
  CONSTRAINT fk_acesso_usuario_has_curso_acesso_usuario1 FOREIGN KEY (acesso_usuario_id) REFERENCES acesso_usuario (acesso_usuario_id),
  CONSTRAINT fk_acesso_usuario_has_curso_curso1 FOREIGN KEY (curso_id) REFERENCES curso (curso_id)
);

create view vw_acesso_usuario_curso as
select ac_user_curso.acesso_usuario_curso_id, a.email, c.curso_id
from acesso_usuario_curso ac_user_curso
join acesso_usuario a on ac_user_curso.acesso_usuario_id = a.acesso_usuario_id
join curso c on ac_user_curso.curso_id = c.curso_id;

select * from vw_acesso_usuario_curso;

/* ------------------------ */

/*call select_login_usuario('tamirys','123');
call insert_insere_agendamento(1,1,'segunda-feira','14:00:00','17:00:00');*/

/* Script de manutenção de BD */

/*select * from agendamento order by agendamento.agendamento_id limit 10;

optimize table agendamento;*/
