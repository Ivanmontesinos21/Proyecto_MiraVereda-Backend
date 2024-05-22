CREATE OR REPLACE FUNCTION actualizar_pelicula (
	id_input INTEGER,
	genero_input VARCHAR2,
	fecha_estreno_input DATE,
	duracion_input INTEGER,
	titulo_input VARCHAR2,
	precio_input INTEGER,
	descripcion_input VARCHAR2,
	nombre_director_input VARCHAR2,
	version_idioma VARCHAR2,
	id_tarifa_input INTEGER,
    imagen_url_input VARCHAR2,
	disponible_hasta_input DATE,
	disponible_desde_input DATE,
	codigo_serie_input INTEGER,
	temporada_input INTEGER,
	actores_input VARCHAR2
) RETURN BOOLEAN
IS
BEGIN
	DECLARE
		tipo_ca VARCHAR(10);
		success BOOLEAN := FALSE;
	BEGIN
		SELECT
				CASE
					WHEN p.ID_CONTENIDOAUDIOVISUAL IS NOT NULL THEN 'pelicula'
					WHEN c.ID_CONTENIDOAUDIOVISUAL IS NOT NULL THEN 'capitulo'
					ELSE 'corto'
				END
			INTO tipo_ca
			FROM CONTENIDO_AUDIOVISUAL ca
				LEFT JOIN PELICULA p
				ON ca.ID_CA = p.ID_CONTENIDOAUDIOVISUAL
				LEFT JOIN CAPITULO c
				ON ca.ID_CA = c.ID_CONTENIDOAUDIOVISUAL
			WHERE ca.ID_CA = id_input;
		UPDATE CONTENIDO_AUDIOVISUAL
			SET
				GENERO = genero_input,
				FECHA_ESTRENO = fecha_estreno_input,
				DURACION = duracion_input,
				TITULO = titulo_input,
				PRECIO = precio_input,
				DESCRIPCION = descripcion_input,
				NOMBRE_DIRECTOR = nombre_director_input,
				VERSION_IDIOMA = version_idioma,
				ID_TARIFA = id_tarifa_input,
	            IMAGEN_URL = imagen_url_input
			WHERE ID_CA = id_input;
		success := SQL%ROWCOUNT = 1;
		IF tipo_ca = 'pelicula' THEN
			UPDATE PELICULA
				SET
					DISPONIBLE_HASTA = disponible_hasta_input
				WHERE ID_CONTENIDOAUDIOVISUAL = id_input;
		ELSIF tipo_ca = 'capitulo' THEN
			UPDATE CAPITULO
				SET
					DISPONIBLE_DESDE = disponible_desde_input,
					CODIGO_SERIE = codigo_serie_input,
					TEMPORADA = temporada_input
				WHERE ID_CONTENIDOAUDIOVISUAL = id_input;
		END IF;
		DELETE FROM ACTUACION_CONTENAUDIOVI WHERE ID_CONTEAUDIOVI = id_input;
		DECLARE
			last_index INTEGER := 0;
			curr_index INTEGER := 0;
			finished BOOLEAN := FALSE;
			dni_ ACTOR.DNI%TYPE;
		BEGIN
			WHILE finished = FALSE LOOP
				curr_index := INSTR(actores_input, ',', curr_index + 1);
				finished := curr_index = 0;
				IF finished = FALSE THEN
					dni_ := SUBSTR(actores_input, last_index + 1, curr_index - last_index - 1);
					INSERT INTO ACTUACION_CONTENAUDIOVI VALUES (
						dni_,
						id_input,
						(SELECT CURRENT_TIMESTAMP FROM DUAL)
					);
					last_index := curr_index;
				END IF;
			END LOOP;
		END;
		RETURN success;
	END;
END;
