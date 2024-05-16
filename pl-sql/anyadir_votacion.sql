create or replace function votar_pelicula(email_input varchar2,
password_input varchar2, valoracion_cliente number,id_contenido_input integer)
return integer

is
begin

declare
    valoracion_cliente number;
    valoracion_total integer;
    
               begin
                
                select avg(valoracion) into valoracion_total FROM alquila
                WHERE id_conteaudiovi = id_contenido_input;
                
          return valoracion_total;
end;
end;


/*

return null;
end votar_pelicula;
*/
