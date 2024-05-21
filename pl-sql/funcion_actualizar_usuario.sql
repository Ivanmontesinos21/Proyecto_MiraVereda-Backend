CREATE OR REPLACE FUNCTION actualizar_usuario (
    id_input INTEGER,
    nombre_input VARCHAR2,
    apellidos_input VARCHAR2,
    contrasenya_input VARCHAR2,
    email_input VARCHAR2,
    domicilio_input VARCHAR2,
    codigo_postal_input VARCHAR2,
    fecha_nacimiento_input DATE
)
RETURN BOOLEAN
IS
BEGIN
    DECLARE
        clientes_con_email INTEGER;
    BEGIN
        SELECT COUNT(*) INTO clientes_con_email FROM CLIENTE WHERE email = email_input AND id_cliente <> id_input;
        IF clientes_con_email = 0 THEN
            IF contrasenya_input IS NULL THEN
                UPDATE CLIENTE SET
                    nombre = nombre_input,
                    apellidos = apellidos_input,
                    email = email_input,
                    domicilio = domicilio_input,
                    codigo_postal = codigo_postal_input,
                    fecha_nacimiento = fecha_nacimiento_input
                    WHERE id_cliente = id_input;
            ELSE
                UPDATE CLIENTE SET
                    nombre = nombre_input,
                    apellidos = apellidos_input,
                    contrasenya = contrasenya_input,
                    email = email_input,
                    domicilio = domicilio_input,
                    codigo_postal = codigo_postal_input,
                    fecha_nacimiento = fecha_nacimiento_input
                    WHERE id_cliente = id_input;
            END IF;
            RETURN TRUE;
        ELSE
            RETURN FALSE;
        END IF;
    END;
END;
