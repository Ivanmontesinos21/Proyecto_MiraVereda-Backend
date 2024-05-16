CREATE OR REPLACE PROCEDURE login (email_input VARCHAR2, password_input VARCHAR2, tarjetas OUT SYS_REFCURSOR, clientes OUT SYS_REFCURSOR)
IS
BEGIN
	DECLARE
		clientes_c SYS_REFCURSOR;
		tarjetas_c SYS_REFCURSOR;
	BEGIN 
		OPEN clientes_c FOR SELECT * FROM CLIENTE WHERE email = email_input AND contrasenya = password_input;
		OPEN tarjetas_c FOR SELECT * FROM TARJETA t JOIN CLIENTE c ON c.email = email_input AND c.contrasenya = password_input AND t.ID_CLIENTE = c.ID_CLIENTE;
		clientes := clientes_c;
		tarjetas := tarjetas_c;
	END;
END;
