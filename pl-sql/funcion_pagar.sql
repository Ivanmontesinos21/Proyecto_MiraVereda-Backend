CREATE OR REPLACE FUNCTION pagar (email_input VARCHAR2, password_input VARCHAR2) RETURN INTEGER
IS
BEGIN
    DECLARE
        id_cliente_input INTEGER;
        id_factura INTEGER;
        alquilas SYS_REFCURSOR;
        reg_alquila ALQUILA%ROWTYPE;
        total_pagados INTEGER;
    BEGIN
        SELECT id_cliente INTO id_cliente_input FROM CLIENTE WHERE email = email_input AND contrasenya = password_input;
        OPEN alquilas FOR SELECT * FROM ALQUILA
            WHERE
                id_cliente = id_cliente_input AND
                en_carrito = 1;
        SELECT COUNT(*) INTO total_pagados FROM ALQUILA
            WHERE
                id_cliente = id_cliente_input AND
                en_carrito = 1;
        IF total_pagados > 0 THEN
            DECLARE
                total_carrito INTEGER;
            BEGIN
                SELECT sum(ca.precio + NVL(t.increment_tarifa, 0)) into total_carrito FROM Alquila a
                    join CONTENIDO_AUDIOVISUAL ca
                        on ca.id_ca=a.id_conteaudiovi
                    join tarifa t
                        on ca.id_tarifa = t.id_tarifa
                    WHERE
                        a.id_cliente = id_cliente_input AND
                        a.en_carrito = 1; 
                DECLARE
                    reg_factura FACTURA%ROWTYPE;
                BEGIN
                    SELECT * INTO reg_factura FROM FACTURA
                        WHERE (SELECT CURRENT_DATE FROM DUAL) < fecha + 30
                        ORDER BY FECHA DESC
                        FETCH FIRST 1 ROWS ONLY;
                    id_factura := reg_factura.numero_factura;
                    UPDATE FACTURA
                        SET
                            importe_base = importe_base + total_carrito,
                            importe_con_iva = (importe_base + total_carrito) + ((importe_base + total_carrito) * CONSTANTES.IVA)
                        WHERE numero_factura = id_factura;
                END;
                EXCEPTION WHEN NO_DATA_FOUND THEN
                    id_factura := factura_sequence.nextval;
                    INSERT INTO FACTURA VALUES (
                        id_factura,
                        total_carrito,
                        total_carrito + (total_carrito * CONSTANTES.IVA),
                        (SELECT CURRENT_DATE FROM DUAL),
                        id_cliente_input,
                        (SELECT CURRENT_TIMESTAMP FROM DUAL)
                    );
            END;
            LOOP
                FETCH alquilas INTO reg_alquila;
                EXIT WHEN alquilas%NOTFOUND;
                INSERT INTO LINEA_FACTURA VALUES (
                    id_factura,
                    reg_alquila.id_conteaudiovi,
                    linea_factura_sequence.nextval,
                    (SELECT CURRENT_TIMESTAMP FROM DUAL)
                );
            END LOOP;
            UPDATE ALQUILA
                SET
                    en_carrito = 0,
                    cobrado = 1,
                    disponible_hasta = NULL
                WHERE id_cliente = id_cliente_input;
        END IF;
        RETURN total_pagados;
    END;
END;
