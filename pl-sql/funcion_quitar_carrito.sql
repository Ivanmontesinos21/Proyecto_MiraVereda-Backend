CREATE or replace function quitar_carrito(email_input varchar2, password_input varchar2, id_contenido integer)
return integer
is begin
    declare 
        precio_carrito integer := 0;
    BEGIN
        UPDATE ALQUILA
            SET en_carrito = 0
            WHERE
                id_conteaudiovi = id_contenido AND
                id_cliente = (SELECT id_cliente FROM CLIENTE WHERE email = email_input AND contrasenya = password_input);
        SELECT sum(ca.precio)into precio_carrito FROM CLIENTE c join Alquila a
            on c.id_cliente=a.id_cliente join CONTENIDO_AUDIOVISUAL ca
            on ca.id_ca=a.id_conteaudiovi
            WHERE c.email = email_input AND c.contrasenya = password_input
            and a.en_carrito = 1; 
        return precio_carrito;
    end;
END;