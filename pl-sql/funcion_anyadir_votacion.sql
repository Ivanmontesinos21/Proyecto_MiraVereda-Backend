create or replace function votar_pelicula (
	email_input varchar2,
	password_input varchar2,
	valoracion_input NUMBER(2,1),
	id_contenido_input integer
)
return NUMBER(2,1)
is
begin
	declare
		valoracion_media_ NUMBER(2,1);
	BEGIN
		UPDATE ALQUILA a
			SET VALORACION = valoracion_input
			FROM CLIENTE c
			WHERE c.email = email_input AND c.contrasenya = password_input
			AND a.ID_CLIENTE = c.ID_CLIENTE
			AND a.ID_CONTEAUDIOVI = id_contenido_input;
		select avg(valoracion) into valoracion_media_ FROM alquila
			WHERE id_conteaudiovi = id_contenido_input;
		UPDATE CONTENIDO_AUDIOVISUAL
			SET valoracion_media = valoracion_media_
			WHERE id_ca = id_contenido_input;
		return valoracion_media_;
	end;
end;
