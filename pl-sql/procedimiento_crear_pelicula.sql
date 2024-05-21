CREATE OR REPLACE PROCEDURE crear_pelicula (
	tipo_input VARCHAR2,
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
	actores_input VARCHAR2,
	actores OUT SYS_REFCURSOR,
	peliculas OUT SYS_REFCURSOR
)
IS
BEGIN
	DECLARE
		nueva_id_pelicula INTEGER;
	BEGIN
		nueva_id_pelicula := ca_sequence.nextval;
		INSERT INTO CONTENIDO_AUDIOVISUAL VALUES (
			nueva_id_pelicula,
			genero_input,
			fecha_estreno_input,
			duracion_input,
			titulo_input,
			precio_input,
			descripcion_input,
			0,
			nombre_director_input,
			version_idioma,
			id_tarifa_input,
            imagen_url_input,
			(SELECT CURRENT_TIMESTAMP FROM DUAL)
		);
		IF tipo_input = 'pelicula' THEN
			INSERT INTO PELICULA VALUES (
				nueva_id_pelicula,
				disponible_hasta_input,
				(SELECT CURRENT_TIMESTAMP FROM DUAL)
			);
		ELSIF tipo_input = 'capitulo' THEN
			INSERT INTO CAPITULO VALUES (
				nueva_id_pelicula,
				disponible_desde_input,
				codigo_serie_input,
				temporada_input,
				(SELECT CURRENT_TIMESTAMP FROM DUAL)
			);
		END IF;
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
						nueva_id_pelicula,
						(SELECT CURRENT_TIMESTAMP FROM DUAL)
					);
					last_index := curr_index;
				END IF;
			END LOOP;
		END;
		COMMIT;
		get_pelicula(nueva_id_pelicula, actores, peliculas);
	END;
END;