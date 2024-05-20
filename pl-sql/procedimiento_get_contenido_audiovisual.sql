CREATE OR REPLACE PROCEDURE get_contenido_audiovisual (
    id_input INTEGER,
    serie_out OUT VARCHAR2,
    tarifa_out OUT INTEGER,
    tipo_out OUT VARCHAR2,
    actores OUT SYS_REFCURSOR,
    peliculas OUT SYS_REFCURSOR,
    capitulos OUT SYS_REFCURSOR,
    contenidos_audiovisuales OUT SYS_REFCURSOR
)
IS
BEGIN
    DECLARE
        pelicula_count INTEGER;
        capitulo_count INTEGER;
        raw_capitulo CAPITULO%ROWTYPE;
        raw_ca CONTENIDO_AUDIOVISUAL%ROWTYPE;
    BEGIN
        OPEN contenidos_audiovisuales FOR SELECT * FROM CONTENIDO_AUDIOVISUAL WHERE id_ca = id_input;
        IF (SELECT id_tarifa FROM CONTENIDO_AUDIOVISUAL WHERE id_ca = id_input) IS NOT NULL THEN
            tarifa_out := (SELECT t.increment_tarifa FROM ACTUACION_CONTENIDOAUDIOVI c JOIN TARIFA t WHERE c.id_tarifa = t.id_tarifa);
        END IF;
        SELECT COUNT(*) INTO pelicula_count FROM PELICULA WHERE id_ca = id_input;
        IF pelicula_count = 1 THEN
            tipo_out := 'pelicula';
            OPEN peliculas FOR SELECT * FROM PELICULA WHERE id_ca = id_input;
        ELSE
            SELECT COUNT(*) INTO capitulo_count FROM CAPITULO WHERE id_ca = id_input;
            IF capitulo_count = 1 THEN
                SELECT * INTO raw_capitulo FROM CAPITULO WHERE id_ca = id_input;
                tipo_out := 'capitulo';
                serie_out := (SELECT titulo FROM SERIE WHERE codigo = raw_capitulo.codigo_serie);
                OPEN capitulos FOR SELECT * FROM CAPITULO WHERE id_ca = id_input;
            ELSE
                tipo_out := 'corto';
            END IF;
        END IF;
        OPEN actores FOR SELECT a.DNI, a.NOMBRE, a.APELLIDOS FROM ACTUACION_CONTENIDOAUDIOVI c JOIN ACTOR a ON c.dni_actor = a.dni;
    END;
END;
