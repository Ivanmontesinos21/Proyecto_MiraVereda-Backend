/*

Endpoint para añadir una nueva línea de compra al carrito. Si se ha podido añadir
la nueva línea, se deberá devolver en la respuesta el cálculo total actualizado de
la factura.

Hacer una funcion en la que muestre el precio del contenido_Audiovisual que ha 
comprado el cliente y aumenta la factura


Inserta el id_Cliente

*/


CREATE or replace function anyadir_carrito(email_input varchar2, password_input varchar2, id_contenido integer)
    return integer
    is begin
        declare 
            id_cliente_input integer;
            precio_carrito integer := 0;
            alquila_count integer := 0;
        BEGIN
            SELECT id_cliente INTO id_cliente_input FROM CLIENTE WHERE email = email_input AND contrasenya = password_input;
            SELECT COUNT(*) INTO alquila_count FROM ALQUILA WHERE id_conteaudiovi = id_contenido AND id_cliente = id_cliente_input;
            IF alquila_count = 0 THEN
                INSERT INTO ALQUILA VALUES (
                    id_contenido,
                    id_cliente_input,
                    0,
                    NULL,
                    NULL,
                    (SELECT CURRENT_TIMESTAMP FROM DUAL),
                    1
                );
            ELSE
                UPDATE ALQUILA
                    SET en_carrito = 1
                    WHERE id_conteaudiovi = id_contenido AND id_cliente = id_cliente_input;
            END IF;
            SELECT sum(ca.precio + NVL(t.increment_tarifa, 0))into precio_carrito FROM CLIENTE c join Alquila a
                on c.id_cliente=a.id_cliente join CONTENIDO_AUDIOVISUAL ca
                on ca.id_ca=a.id_conteaudiovi
                join tarifa t on ca.id_tarifa = t.id_tarifa
                WHERE c.email = email_input AND c.contrasenya = password_input
                and a.en_carrito = 1; 
            return precio_carrito;
        end;
    END;

