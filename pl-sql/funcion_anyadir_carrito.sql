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
            precio_carrito integer := 0;
        BEGIN
	        INSERT INTO ALQUILA VALUES (
	        	id_contenido,
	        	(SELECT id_cliente FROM CLIENTE WHERE email = email_input AND contrasenya = password_input),
	        	0,
	        	NULL,
	        	NULL,
	        	(SELECT CURRENT_TIMESTAMP FROM DUAL),
	        	1
	        );
	        SELECT sum(ca.precio)into precio_carrito FROM CLIENTE c join Alquila a
	        	on c.id_cliente=a.id_cliente join CONTENIDO_AUDIOVISUAL ca
	            on ca.id_ca=a.id_conteaudiovi
	            WHERE c.email = email_input AND c.contrasenya = password_input
	            and a.en_carrito = 1; 
            return precio_carrito;
        end;
    END;
