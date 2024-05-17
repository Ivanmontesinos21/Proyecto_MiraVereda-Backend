CREATE OR REPLACE FUNCTION crear_usuario (
    nombre_input VARCHAR2,
    apellidos_input VARCHAR2,
    contrasenya_input VARCHAR2,
    email_input VARCHAR2,
    domicilio_input VARCHAR2,
    codigo_postal_input VARCHAR2,
    fecha_nacimiento_input DATE
)
RETURN SYS_REFCURSOR
IS
BEGIN
    DECLARE
        cliente_cursor SYS_REFCURSOR;
        nueva_id_cliente INTEGER;
    BEGIN
        nueva_id_cliente := cliente_sequence.nextval;
        INSERT INTO CLIENTE VALUES (
            nueva_id_cliente,
            nombre_input,
            apellidos_input,
            contrasenya_input,
            email_input,
            domicilio_input,
            codigo_postal_input,
            fecha_nacimiento_input,
            (SELECT CURRENT_TIMESTAMP FROM DUAL)
        );
        COMMIT;
        OPEN cliente_cursor FOR SELECT * FROM CLIENTE WHERE id_cliente = nueva_id_cliente;
        RETURN cliente_cursor;
    END;
END;
