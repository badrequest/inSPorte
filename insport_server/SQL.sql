-- Script de Criação das tabelas e sequences da base de dados central

CREATE TABLE comentario
(
  id numeric NOT NULL,
  texto character varying(512),
  CONSTRAINT pk_comentario PRIMARY KEY (id)
);

CREATE TABLE comentario_imagem
(
  id numeric NOT NULL,
  id_comentario numeric,
  imagem character varying,
  CONSTRAINT pk_comentario_imagem PRIMARY KEY (id),
  CONSTRAINT fk_comentario FOREIGN KEY (id_comentario)
      REFERENCES comentario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE envio
(
  id numeric NOT NULL,
  id_linha numeric,
  id_usuario numeric,
  id_comentario numeric,
  data timestamp with time zone,
  lat real,
  lon real,
  CONSTRAINT pk_envio PRIMARY KEY (id),
  CONSTRAINT fk_comentario FOREIGN KEY (id_comentario)
      REFERENCES comentario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_linha FOREIGN KEY (id_linha)
      REFERENCES linha (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usuario FOREIGN KEY (id_usuario)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE likes
(
  id_linha numeric NOT NULL,
  id_usuario numeric NOT NULL,
  status boolean,
  id numeric NOT NULL,
  CONSTRAINT pk_like PRIMARY KEY (id),
  CONSTRAINT fk_linha FOREIGN KEY (id_linha)
      REFERENCES linha (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usuario FOREIGN KEY (id_usuario)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE linha
(
  id numeric NOT NULL,
  nome character varying(256),
  codigo character varying(64),
  CONSTRAINT pk_linha PRIMARY KEY (id)
);

CREATE TABLE opcao
(
  id numeric NOT NULL,
  id_pergunta numeric,
  texto character(256),
  CONSTRAINT pk_opcao PRIMARY KEY (id)
);

CREATE TABLE pergunta
(
  id numeric NOT NULL,
  id_questionario numeric,
  texto character(256),
  CONSTRAINT pk_pergunta PRIMARY KEY (id),
  CONSTRAINT fk_id_questionario FOREIGN KEY (id_questionario)
      REFERENCES questionario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE questionario
(
  id numeric NOT NULL,
  nome character varying(256),
  CONSTRAINT pk_questionario PRIMARY KEY (id)
);

CREATE TABLE resposta
(
  id numeric NOT NULL,
  id_envio numeric,
  id_pergunta numeric,
  CONSTRAINT pk_resposta PRIMARY KEY (id),
  CONSTRAINT fk_envio FOREIGN KEY (id_envio)
      REFERENCES envio (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_pergunta FOREIGN KEY (id_pergunta)
      REFERENCES pergunta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT un_envio_pergunta UNIQUE (id_envio, id_pergunta)
);

CREATE TABLE resposta_imagem
(
  id numeric NOT NULL,
  id_resposta numeric,
  imagem character varying,
  CONSTRAINT pk_imagem_resposta PRIMARY KEY (id),
  CONSTRAINT fk_resposta FOREIGN KEY (id_resposta)
      REFERENCES resposta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE resposta_opcao
(
  id numeric NOT NULL,
  id_resposta numeric,
  id_opcao numeric,
  CONSTRAINT pk_resposta_opcao PRIMARY KEY (id),
  CONSTRAINT fk_opcao FOREIGN KEY (id_opcao)
      REFERENCES opcao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_resposta FOREIGN KEY (id_resposta)
      REFERENCES resposta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE usuario
(
  bu character varying(15),
  cpf character(15),
  id numeric NOT NULL,
  CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE SEQUENCE sq_comentario
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE SEQUENCE sq_envio
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 4
  CACHE 1;

CREATE SEQUENCE sq_like
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 6
  CACHE 1;
  
CREATE SEQUENCE sq_linha
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1315
  CACHE 1;
  
CREATE SEQUENCE sq_resposta
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 6
  CACHE 1;
  
CREATE SEQUENCE sq_resposta_opcao
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 10
  CACHE 1;
  
CREATE SEQUENCE sq_usuario
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 13
  CACHE 1;



