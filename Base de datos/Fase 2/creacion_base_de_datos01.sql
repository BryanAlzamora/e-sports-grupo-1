/* ELIMINACION DE TABLAS TABLAS */
DROP TABLE COMPETICION CASCADE CONSTRAINTS;
DROP TABLE JUEGO CASCADE CONSTRAINTS;
DROP TABLE JORNADA CASCADE CONSTRAINTS;
DROP TABLE ENFRENTAMIENTO CASCADE CONSTRAINTS;
DROP TABLE EQUIPO CASCADE CONSTRAINTS;
DROP TABLE PATROCINADOR CASCADE CONSTRAINTS;
DROP TABLE USUARIO CASCADE CONSTRAINTS;
DROP TABLE JUGADOR CASCADE CONSTRAINTS;
DROP TABLE ENTRENADOR CASCADE CONSTRAINTS;
DROP TABLE ASISTENTE CASCADE CONSTRAINTS;
DROP TABLE CLASIFICACION CASCADE CONSTRAINTS;



/*---------CREACION DE TABLA JUEGO--------*/

    CREATE TABLE JUEGO(
        ID_JUEGO NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR2(30) not null,
        EMPRESA VARCHAR2(30) not null,
        FECHA_LANZAMIENTO DATE,
        CONSTRAINT PK_JUEGO PRIMARY KEY (ID_JUEGO)
    );

    /*---------CREACION DE TABLA COMPETICION--------*/

    CREATE TABLE COMPETICION(
        ID_COMPETICION NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR(40) not null,
        FECHA_INICIO DATE not null,
        FECHA_FIN DATE not null,
        ESTADO VARCHAR2(20) CHECK (ESTADO IN ('abierto', 'cerrado')) not null,
        ID_JUEGO NUMBER(3),
        CONSTRAINT PK_COMPETICION PRIMARY KEY (ID_COMPETICION),
        CONSTRAINT FK_JUEGO FOREIGN KEY (ID_JUEGO) REFERENCES JUEGO(ID_JUEGO)
    );

    /*---------CREACION DE TABLA JORNADA--------*/

    CREATE TABLE JORNADA(
        ID_JOR_COMP NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        ID_JORNADA NUMBER(3),
        ID_COMPETICION NUMBER(3),
        FECHA_ENFRENTAMIENTO DATE,
        CONSTRAINT PK_JORNADA PRIMARY KEY(ID_JOR_COMP),
        CONSTRAINT FK_COMPETICIONN FOREIGN KEY (ID_COMPETICION) REFERENCES COMPETICION(ID_COMPETICION)
    );

    /*---------CREACION DE TABLA PATROCINADOR--------*/

    CREATE TABLE PATROCINADOR(
        ID_PATROCINADOR NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR2(30) not null,
        CONSTRAINT PATRO_ID_PK PRIMARY KEY (ID_PATROCINADOR)
    );

    /*---------CREACION DE TABLA EQUIPO--------*/

    CREATE TABLE EQUIPO(
        ID_EQUIPO NUMBER(3)GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR2(30) not null,
        FECHA_FUNDACION DATE,
        ID_PATROCINADOR NUMBER(3),
        CONSTRAINT EQUI_ID_PK PRIMARY KEY (ID_EQUIPO),
        CONSTRAINT FK_PATROCINADOR FOREIGN KEY (ID_PATROCINADOR) REFERENCES PATROCINADOR(ID_PATROCINADOR)
    );

    /*---------CREACION DE TABLA clasificacion--------*/

    CREATE TABLE CLASIFICACION(
        ID_CLASIFICACION NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        ID_COMPETICION NUMBER(3),
        ID_EQUIPO NUMBER(3),
        PUNTOS NUMBER(3) DEFAULT 0,
        CONSTRAINT ID_CLASIFICACION PRIMARY KEY (ID_CLASIFICACION),
        CONSTRAINT FK_ID_COMPETICION FOREIGN KEY (ID_COMPETICION) REFERENCES COMPETICION(ID_COMPETICION),
        CONSTRAINT FK_ID_EQUIPO FOREIGN KEY (ID_EQUIPO) REFERENCES EQUIPO(ID_EQUIPO)
    );

    /*---------CREACION DE TABLA ENFRENTAMIENTO--------*/

    CREATE TABLE ENFRENTAMIENTO (
        ID_ENF_JOR NUMBER(3)  GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        ID_JOR_COMP NUMBER(3),
        ID_ENFRENTAMIENTO NUMBER(3),--ESTA PROGRAMARLA EN JAVA
        HORA VARCHAR2(5),
        RESULTADO_LOCAL NUMBER(2),
        RESULTADO_VISITANTE NUMBER(2),
        ID_LOCAL NUMBER(3),
        ID_VISITANTE NUMBER(3),
        CONSTRAINT PK_ID_ENFRENTAMIENTO PRIMARY KEY (ID_ENF_JOR),
        CONSTRAINT FK_ID_JOR_COMP FOREIGN KEY (ID_JOR_COMP) REFERENCES JORNADA(ID_JOR_COMP),
        CONSTRAINT FK_EQUIPO_LOCAL FOREIGN KEY (ID_LOCAL) REFERENCES EQUIPO(ID_EQUIPO),
        CONSTRAINT FK_EQUIPO_VISITANTE FOREIGN KEY (ID_VISITANTE) REFERENCES EQUIPO(ID_EQUIPO)
    );  

    /*---------CREACION DE TABLA USUARIO--------*/

    CREATE TABLE USUARIO(
        ID_USUARIO NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR2(30)not null,
        CONTRASENA VARCHAR2(50)not null,
        TIPO VARCHAR2(30) CHECK (tipo IN ('administrador', 'usuario')),
        CONSTRAINT USU_ID_PK PRIMARY KEY (ID_USUARIO)
    );

    /*---------CREACION DE TABLA JUGADOR--------*/

    CREATE TABLE JUGADOR(
        ID_INTEGRANTE NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR2(30) not null,
        APELLIDO1 VARCHAR2(30) not null,
        APELLIDO2 VARCHAR2(30) not null,
        SUELDO NUMBER(6) not null,
        NACIONALIDAD VARCHAR2(30)not null,
        FECHA_NACIMIENTO DATE not null,
        NICKNAME VARCHAR2(30) not null,
        ROL VARCHAR2(30) not null,
        ID_EQUIPO NUMBER(3),
        CONSTRAINT JUGA_ID_PK PRIMARY KEY (ID_INTEGRANTE),
        CONSTRAINT JUGA_ID_EQUI_FK FOREIGN KEY (ID_EQUIPO) REFERENCES EQUIPO(ID_EQUIPO)
    );

    /*---------CREACION DE TABLA ENTRENADOR--------*/

    CREATE TABLE ENTRENADOR(
        ID_INTEGRANTE NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR2(30) not null,
        APELLIDO1 VARCHAR2(30) not null,
        APELLIDO2 VARCHAR2(30) not null,
        SUELDO NUMBER(6) not null,
        ID_EQUIPO NUMBER(3),
        CONSTRAINT ENTRE_ID_PK PRIMARY KEY (ID_INTEGRANTE),
        CONSTRAINT ENTRE_ID_EQUI_FK FOREIGN KEY (ID_EQUIPO) REFERENCES EQUIPO(ID_EQUIPO)
    );

    /*---------CREACION DE TABLA ASISTENTE--------*/

    CREATE TABLE ASISTENTE(
        ID_INTEGRANTE NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        NOMBRE VARCHAR2(30),
        APELLIDO1 VARCHAR2(30),
        APELLIDO2 VARCHAR2(30),
        SUELDO NUMBER(6),
        ID_EQUIPO NUMBER(3),
        CONSTRAINT ASIST_ID_PK PRIMARY KEY (ID_INTEGRANTE),
        CONSTRAINT ASIST_ID_EQUI_FK FOREIGN KEY (ID_EQUIPO) REFERENCES EQUIPO(ID_EQUIPO)
    );







CREATE OR REPLACE VIEW vista_resultado AS
SELECT
  j.ID_JOR_COMP,
  enf.id_enfrentamiento as id_enfrentamiento,
  e.ID_EQUIPO AS local_id,
  e.NOMBRE AS local_name,
  enf.RESULTADO_LOCAL AS local_result,
  enf.RESULTADO_VISITANTE AS visitante_result,
  ev.NOMBRE AS visitante_name,
  ev.ID_EQUIPO AS visitante_id
  
FROM
  JORNADA j
JOIN
  ENFRENTAMIENTO enf ON j.ID_JORNADA = enf.ID_JOR_COMP
JOIN
  EQUIPO e ON e.ID_EQUIPO = enf.ID_LOCAL
JOIN
  EQUIPO ev ON ev.ID_EQUIPO = enf.ID_VISITANTE;
  
  
select * from vista_resultado




CREATE OR REPLACE VIEW VIEW_3_EUIPOS_MAS_PUNTOS AS
SELECT c.ID_COMPETICION,
       cl.ID_EQUIPO,
       e.NOMBRE AS NOMBRE_EQUIPO,
       cl.PUNTOS,
       ROW_NUMBER() OVER (PARTITION BY c.ID_COMPETICION ORDER BY cl.PUNTOS DESC) AS ranking
FROM CLASIFICACION cl
JOIN COMPETICION c ON cl.ID_COMPETICION = c.ID_COMPETICION
JOIN EQUIPO e ON cl.ID_EQUIPO = e.ID_EQUIPO;

SELECT *
FROM VIEW_3_EUIPOS_MAS_PUNTOS
WHERE ID_COMPETICION = 1;



CREATE VIEW vista_jugadores_por_equipo AS
SELECT E.ID_EQUIPO,
       E.NOMBRE,
       J.ID_INTEGRANTE,
       J.NOMBRE AS NOMBRE_JUGADOR,
       J.APELLIDO1 AS APELLIDO_JUGADOR
FROM EQUIPO E
JOIN JUGADOR J ON E.ID_EQUIPO = J.ID_EQUIPO
WHERE E.ID_EQUIPO = 1;


 SELECT * FROM vista_jugadores_por_equipo;
 
 DROP VIEW vista_jugadores_por_equipo;


 
drop PUBLIC synonym JUEGOS01;
drop PUBLIC synonym COMPETICIONES01;
drop PUBLIC synonym JORNADAS01;
drop PUBLIC synonym  PATROCINADORES01;
drop PUBLIC synonym EQUIPOS01;
drop PUBLIC synonym CLASIFICACIONES01;
drop PUBLIC synonym PARTIDAS01;
drop PUBLIC synonym USUARIOS01;
drop PUBLIC synonym INTEGRANTES01;
drop PUBLIC synonym ENTRENADORES01;
drop PUBLIC synonym AYUDANTE01;


CREATE PUBLIC SYNONYM JUEGOS01 FOR EQUIPO01.JUEGO;
CREATE PUBLIC SYNONYM COMPETICIONES01 FOR EQDAW01.COMPETICION;
CREATE PUBLIC SYNONYM JORNADAS01 FOR EQDAW01.JORNADA;
CREATE PUBLIC SYNONYM PATROCINADORES01 FOR EQDAW01.PATROCINADOR;
CREATE PUBLIC SYNONYM EQUIPOS01 FOR EQDAW01.EQUIPO;
CREATE PUBLIC SYNONYM CLASIFICACIONES01 FOR EQDAW01.CLASIFICACION;
CREATE PUBLIC SYNONYM PARTIDAS01 FOR EQDAW01.ENFRENTAMIENTO;
CREATE PUBLIC SYNONYM USUARIOS01 FOR EQDAW01.USUARIO;
CREATE PUBLIC SYNONYM INTEGRANTES01 FOR EQDAW01.JUGADOR;
CREATE PUBLIC SYNONYM ENTRENADORES01 FOR EQDAW01.ENTRENADOR;
CREATE PUBLIC SYNONYM AYUDANTE01 FOR EQDAW01.ASISTENTE;


-- Inserts para la tabla JUEGO
INSERT INTO JUEGO (NOMBRE, EMPRESA, FECHA_LANZAMIENTO) VALUES ('The Witcher', 'CD Projekt', TO_DATE('2015-05-19', 'YYYY-MM-DD'));
INSERT INTO JUEGO (NOMBRE, EMPRESA, FECHA_LANZAMIENTO) VALUES ('GTAV', 'Rockstar Games', TO_DATE('2013-09-17', 'YYYY-MM-DD'));
INSERT INTO JUEGO (NOMBRE, EMPRESA, FECHA_LANZAMIENTO) VALUES ('Zelda', 'Nintendo', TO_DATE('2017-03-03', 'YYYY-MM-DD'));
INSERT INTO JUEGO (NOMBRE, EMPRESA, FECHA_LANZAMIENTO) VALUES ('Minecraft', 'Mojang Studios', TO_DATE('2011-11-18', 'YYYY-MM-DD'));


-- Inserts para la tabla COMPETICION
INSERT INTO COMPETICION (NOMBRE, FECHA_INICIO, FECHA_FIN, ESTADO, ID_JUEGO) VALUES ('Liga de Campeones', TO_DATE('2023-05-15', 'YYYY-MM-DD'), TO_DATE('2023-06-30', 'YYYY-MM-DD'), 'abierto', 1);
INSERT INTO COMPETICION (NOMBRE, FECHA_INICIO, FECHA_FIN, ESTADO, ID_JUEGO) VALUES ('Torneo del Rey', TO_DATE('2023-07-01', 'YYYY-MM-DD'), TO_DATE('2023-07-31', 'YYYY-MM-DD'), 'abierto', 2);
INSERT INTO COMPETICION (NOMBRE, FECHA_INICIO, FECHA_FIN, ESTADO, ID_JUEGO) VALUES ('Desafio del Desierto', TO_DATE('2023-08-01', 'YYYY-MM-DD'), TO_DATE('2023-08-15', 'YYYY-MM-DD'), 'cerrado', 3);
INSERT INTO COMPETICION (NOMBRE, FECHA_INICIO, FECHA_FIN, ESTADO, ID_JUEGO) VALUES ('Copa de las Estrellas', TO_DATE('2023-09-01', 'YYYY-MM-DD'), TO_DATE('2023-09-30', 'YYYY-MM-DD'), 'abierto', 4);

-- Inserts para la tabla PATROCINADOR
INSERT INTO PATROCINADOR (NOMBRE) VALUES ('Amazon');
INSERT INTO PATROCINADOR (NOMBRE) VALUES ('Oysho');
INSERT INTO PATROCINADOR (NOMBRE) VALUES ('Nike');

-- Inserts para la tabla EQUIPO
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Dragones', TO_DATE('2000-01-01', 'YYYY-MM-DD'), 1);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Leones', TO_DATE('2000-02-01', 'YYYY-MM-DD'), 1);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Tigres', TO_DATE('2000-03-01', 'YYYY-MM-DD'), 1);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Lobos', TO_DATE('2000-04-01', 'YYYY-MM-DD'), 1);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Aguilas', TO_DATE('2000-05-01', 'YYYY-MM-DD'), 2);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Pantxos', TO_DATE('2000-06-01', 'YYYY-MM-DD'), 2);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Osos', TO_DATE('2000-07-01', 'YYYY-MM-DD'), 2);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Linces', TO_DATE('2000-08-01', 'YYYY-MM-DD'), 2);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Delfines', TO_DATE('2000-09-01', 'YYYY-MM-DD'), 3);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Tiburones', TO_DATE('2000-10-01', 'YYYY-MM-DD'), 3);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Cocodrilos', TO_DATE('2000-11-01', 'YYYY-MM-DD'), 3);
INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES ('Los Piratas', TO_DATE('2000-12-01', 'YYYY-MM-DD'), 3);



-- Inserts para la tabla USUARIO
INSERT INTO USUARIO (NOMBRE, CONTRASENA, TIPO) VALUES ('ander', 'contrasena1', 'administrador');
INSERT INTO USUARIO (NOMBRE, CONTRASENA, TIPO) VALUES ('oier', 'contrasena2', 'usuario');
INSERT INTO USUARIO (NOMBRE, CONTRASENA, TIPO) VALUES ('inigo', 'contrasena3', 'usuario');

-- Inserts para la tabla JUGADOR
-- Equipo 1
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Alex', 'Gonzalez', 'Martinez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Alex', 'Delantero', 1);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Sophia', 'Lopez', 'Rodriguez', 1800, 'Estados Unidos', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Sophy', 'Portero', 1);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Daniel', 'Fernandez', 'Sanchez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Danny', 'Defensa', 1);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Mia', 'Martinez', 'Garcia', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Mia', 'Centrocampista', 1);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('James', 'Johnson', 'Brown', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'James', 'Defensa', 1);

-- Equipo 2
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Emma', 'Taylor', 'Martinez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Emmy', 'Delantero', 2);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Liam', 'Rodriguez', 'Brown', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Liam', 'Portero', 2);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Olivia', 'Garcia', 'Sanchez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Olly', 'Defensa', 2);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Noah', 'Martinez', 'Rodriguez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Noah', 'Centrocampista', 2);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Isabella', 'Johnson', 'Gonzalez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Isa', 'Defensa', 2);
-- Equipo 3
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Ethan', 'Perez', 'Gutierrez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Ethan', 'Delantero', 3);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Ava', 'Sanchez', 'Martinez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Ava', 'Portero', 3);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Alexander', 'Gutierrez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Alex', 'Defensa', 3);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Sophie', 'Martinez', 'Rodriguez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Sophie', 'Centrocampista', 3);

INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Mason', 'Rodriguez', 'Garcia', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Mason', 'Defensa', 3);


-- Equipo 4
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Amelia', 'Garcia', 'Rodriguez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Amy', 'Delantero', 4);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Logan', 'Martinez', 'Lopez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Logan', 'Portero', 4);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Chloe', 'Sanchez', 'Gutierrez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Chloe', 'Defensa', 4);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Lucas', 'Gutierrez', 'Rodriguez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Lucas', 'Centrocampista', 4);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Avery', 'Rodriguez', 'Garcia', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Avery', 'Defensa', 4);


-- Equipo 5
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Elijah', 'Lopez', 'Martinez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Eli', 'Delantero', 5);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Harper', 'Martinez', 'Rodriguez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Harper', 'Portero', 5);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Benjamin', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Benji', 'Defensa', 5);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Evelyn', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Eve', 'Centrocampista', 5);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Daniel', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Danny', 'Defensa', 5);

-- Equipo 6
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Jackson', 'Rodriguez', 'Garcia', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Jack', 'Delantero', 6);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Penelope', 'Gutierrez', 'Martinez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Penny', 'Portero', 6);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Sebastian', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Seb', 'Defensa', 6);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Scarlett', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Scar', 'Centrocampista', 6);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('William', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Will', 'Defensa', 6);


-- Equipo 7
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Natalie', 'Garcia', 'Martinez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Nate', 'Delantero', 7);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Evan', 'Martinez', 'Rodriguez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Evan', 'Portero', 7);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Samantha', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Sam', 'Defensa', 7);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Henry', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Harry', 'Centrocampista', 7);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Abigail', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Abby', 'Defensa', 7);


-- Equipo 8
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('David', 'Rodriguez', 'Garcia', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Dave', 'Delantero', 8);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Lily', 'Gutierrez', 'Martinez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Lil', 'Portero', 8);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Lucas', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Luke', 'Defensa', 8);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Zoe', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Zoe', 'Centrocampista', 8);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Daniel', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Dan', 'Defensa', 8);

-- Equipo 9
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Oliver', 'Garcia', 'Martinez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Ollie', 'Delantero', 9);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Emily', 'Martinez', 'Rodriguez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Em', 'Portero', 9);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Mason', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Mase', 'Defensa', 9);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Aria', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Ari', 'Centrocampista', 9);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Ethan', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'E', 'Defensa', 9);


-- Equipo 10
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Charlotte', 'Rodriguez', 'Garcia', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Charlie', 'Delantero', 10);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Jacob', 'Gutierrez', 'Martinez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Jake', 'Portero', 10);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Mia', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Mia', 'Defensa', 10);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Noah', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Noah', 'Centrocampista', 10);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Aurora', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Rory', 'Defensa', 10);

-- Equipo 11
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Liam', 'Garcia', 'Martinez', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Liam', 'Delantero', 11);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Sophia', 'Martinez', 'Rodriguez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Sophie', 'Portero', 11);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Logan', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Logan', 'Defensa', 11);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Ava', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Ava', 'Centrocampista', 11);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Landon', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Lando', 'Defensa', 11);


-- Equipo 12
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Emma', 'Rodriguez', 'Garcia', 2000, 'España', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Em', 'Delantero', 12);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('William', 'Gutierrez', 'Martinez', 1800, 'Francia', TO_DATE('1992-08-20', 'YYYY-MM-DD'), 'Will', 'Portero', 12);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Chloe', 'Sanchez', 'Lopez', 2200, 'Argentina', TO_DATE('1993-02-10', 'YYYY-MM-DD'), 'Chloe', 'Defensa', 12);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Lucas', 'Lopez', 'Gutierrez', 1900, 'Mexico', TO_DATE('1994-04-25', 'YYYY-MM-DD'), 'Luke', 'Centrocampista', 12);
INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO)
VALUES ('Avery', 'Martinez', 'Rodriguez', 2100, 'Inglaterra', TO_DATE('1991-12-03', 'YYYY-MM-DD'), 'Ave', 'Defensa', 12);

-- Continuar con los otros equipos de manera similar...


-- Inserts para la tabla ENTRENADOR
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Carlos', 'Gonzalez', 'Martinez', 2300, 1);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Laura', 'Sanchez', 'Hernandez', 2100, 2);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Daniel', 'Lopez', 'Rodriguez', 2400, 3);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Laura', 'Sanchez', 'Hernandez', 2100, 4);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Daniel', 'Lopez', 'Rodriguez', 2400, 4);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Laura', 'Sanchez', 'Hernandez', 2100, 5);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Daniel', 'Lopez', 'Rodriguez', 2400, 6);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Laura', 'Sanchez', 'Hernandez', 2100, 6);
INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Daniel', 'Lopez', 'Rodriguez', 2400, 7);

-- Inserts para la tabla ASISTENTE
INSERT INTO ASISTENTE (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('David', 'Garcia', 'Martinez', 1100, 1);
INSERT INTO ASISTENTE (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Elena', 'Lopez', 'Hernandez', 1300, 2);
INSERT INTO ASISTENTE (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, ID_EQUIPO) VALUES ('Sergio', 'Fernandez', 'Rodriguez', 1400, 3);
-- Inserts para la tabla CLASIFICACION
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (1, 1, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (1, 3, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (1, 7, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (1, 11, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (2, 2, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (2, 4, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (2, 6, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (2, 8, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (2, 11, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (2, 12, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (3, 5, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (3, 7, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (3, 9, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (3, 10, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (4, 3, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (4, 4, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (4, 9, 0);
INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO, PUNTOS) VALUES (4, 12, 0);

commit;
