

/*------CREACION DE TRIGGER QUE COMPRUBA SI HAY MAS DE 6 JUGADORES--------*/
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
END TR_NUM_JUGADORES_MAYOR6;

