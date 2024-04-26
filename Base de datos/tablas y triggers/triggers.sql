/*-----------------------------CREACION DE TRIGGERS-----------------------------------*/

create or replace TRIGGER TR_NUM_JUGADORES_MAYOR6
BEFORE INSERT OR UPDATE ON JUGADOR
FOR EACH ROW
DECLARE
    V_NUMJUGADORES NUMBER(2);
    V_ESTADO COMPETICION.ESTADO%TYPE;
    E_NUMJUGADORINCORRECTO EXCEPTION;
BEGIN 
    SELECT COUNT(*) INTO V_NUMJUGADORES
    FROM JUGADOR
    WHERE ID_EQUIPO = :NEW.ID_EQUIPO;

    IF V_NUMJUGADORES > 6 THEN 
        RAISE E_NUMJUGADORINCORRECTO;
    END IF; 

EXCEPTION 
    WHEN TOO_MANY_ROWS THEN 
        RAISE_APPLICATION_ERROR(-20001,'HAY MAS DE UN VALOR');
    WHEN NO_DATA_FOUND THEN 
        RAISE_APPLICATION_ERROR(-20002,'VALOR NO ENCONTRADO');
    WHEN E_NUMJUGADORINCORRECTO THEN 
        RAISE_APPLICATION_ERROR(-20003,'EL NUMERO DE JUGADORES ES INCORRECTO');
    WHEN OTHERS THEN 
        RAISE_APPLICATION_ERROR(-20004,'ERROR INESPERADO');
END;


/*-----creacion de trigger bloqueo de equipos cuando la competicion esta iniciada-----*/

CREATE OR REPLACE TRIGGER bloqueo_competicion_cerrada
BEFORE INSERT OR DELETE OR UPDATE ON EQUIPO
FOR EACH ROW
DECLARE
    v_estado_competicion VARCHAR2(20);
BEGIN
    SELECT ESTADO INTO v_estado_competicion FROM COMPETICION WHERE ID_COMPETICION = :NEW.ID_COMPETICION;
    
    IF v_estado_competicion = 'cerrado' THEN
        RAISE_APPLICATION_ERROR(-20001, 'No se pueden realizar operaciones en EQUIPO cuando la competición está cerrada.');
    END IF;
END;

/*-------*/

CREATE OR REPLACE TRIGGER lock_jugador_table 
BEFORE INSERT OR UPDATE OR DELETE ON JUGADOR 
FOR EACH ROW 
DECLARE 
  v_estado VARCHAR2(20); 
BEGIN 
  SELECT E.estado 
  INTO v_estado 
  FROM COMPETICION E 
  WHERE E.ID_COMPETICION = :NEW.ID_EQUIPO; 
 
  IF v_estado = 'cerrado' THEN 
    RAISE_APPLICATION_ERROR(-20001, 'No se pueden realizar cambios en la tabla JUGADOR cuando la competición está cerrada'); 
  END IF; 
END;


INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO) VALUES ('Jugador1', 'Apellido1', 'Apellido2', 1000,
 'Nacionalidad1', TO_DATE('1990-11-01', 'YYYY-MM-DD'), 'Nick1', 'Rol1', 3);


 /*Trigger para que la suma de sueldos de todos los jugadores de un mismo equipo no pueda ser superior a 200.000€*/

 CREATE OR REPLACE TRIGGER TR_MAX_SAL
BEFORE INSERT OR UPDATE ON JUGADOR
FOR EACH ROW
DECLARE
    V_SUELDO_TOTAL_EQUIPO JUGADOR.SUELDO%TYPE;
    E_SUELDOMAXINCORRECTO EXCEPTION;
BEGIN 
    SELECT SUM(SUELDO) INTO V_SUELDO_TOTAL_EQUIPO
    FROM JUGADOR
    WHERE ID_EQUIPO = :NEW.ID_EQUIPO;
    
    IF V_SUELDO_TOTAL_EQUIPO + NVL(:NEW.SUELDO, 0) - NVL(:OLD.SUELDO, 0) > 200000 THEN
        RAISE E_SUELDOMAXINCORRECTO;
    END IF;
    
EXCEPTION
    WHEN TOO_MANY_ROWS THEN 
        RAISE_APPLICATION_ERROR(-20001,'HAY MAS DE UN VALOR');
    WHEN NO_DATA_FOUND THEN 
        RAISE_APPLICATION_ERROR(-20002,'VALOR NO ENCONTRADO');
    WHEN E_SUELDOMAXINCORRECTO THEN 
        RAISE_APPLICATION_ERROR(-20003,'LA SUMA DE LOS SUELDOS DE UN MISMO EQUIPO NO PUEDE SUPERAR LOS 200.000€');
    WHEN OTHERS THEN 
        RAISE_APPLICATION_ERROR(-20004,'ERROR INESPERADO'); 
END;

/*Trigger sueldo Minimo Interprofesional*/

CREATE OR REPLACE TRIGGER TR_VALIDAR_SUELDO_MINIMO
BEFORE INSERT OR UPDATE ON JUGADOR
FOR EACH ROW
DECLARE
    E_SUELDO_BAJO EXCEPTION;
BEGIN
    IF :NEW.SUELDO < 1134 THEN
        RAISE E_SUELDO_BAJO;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN 
        RAISE_APPLICATION_ERROR(-20022, 'VALOR NO ENCONTRADO');
    WHEN E_SUELDO_BAJO THEN
        RAISE_APPLICATION_ERROR(-20023, 'EL SUELDO NO PUEDE SER MENOR AL MINIMO INTERPROFESIONAL');
    WHEN OTHERS THEN 
        RAISE_APPLICATION_ERROR(-20024, 'ERROR INESPERADO');
END;