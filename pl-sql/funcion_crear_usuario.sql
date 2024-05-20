CREATE OR REPLACE FUNCTION crear_usuario (
    nombre_input VARCHAR2,
    apellidos_input VARCHAR2,
    contrasenya_input VARCHAR2,
    email_input VARCHAR2,
    domicilio_input VARCHAR2,
    codigo_postal_input VARCHAR2,
    fecha_nacimiento_input DATE,
    clientes OUT SYS_REFCURSOR
)
RETURN BOOLEAN
IS
BEGIN
    DECLARE
        nueva_id_cliente INTEGER;
        clientes_con_email INTEGER;
    BEGIN
        SELECT COUNT(*) INTO clientes_con_email FROM CLIENTE WHERE email = email_input;
        IF clientes_con_email = 0 THEN
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
	        OPEN clientes FOR SELECT * FROM CLIENTE WHERE id_cliente = nueva_id_cliente;
	        RETURN TRUE;
	    ELSE
	    	RETURN FALSE;
	    END IF;
    END;
END;
