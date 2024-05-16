/*

Endpoint para añadir una nueva línea de compra al carrito. Si se ha podido añadir
la nueva línea, se deberá devolver en la respuesta el cálculo total actualizado de
la factura.

Hacer una funcion en la que muestre el precio del contenido_Audiovisual que ha 
comprado el cliente y aumenta la factura


Inserta el id_Cliente

*/


declare
    function anyadir_Carito( email_input varchar2,password_input varchar2)
    return  number
    is begin
    
        declare 
            precio_carrito number:=0;
        begin
             SELECT sum(ca.precio)into precio_carrito FROM CLIENTE c join Alquila a
                on c.id_cliente=a.id_cliente join CONTENIDO_AUDIOVISUAL ca
                on ca.id_ca=a.id_conteaudiovi
                WHERE c.email = email_input AND c.contrasenya = password_input
                and a.cobrado=0 and a.facturado=0; 
                
            return precio_carrito;
        end ;
    END;
    
begin
    null;
end;
