package es.ieslavereda.miraveredabackend.model;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
/**
 * Clase de la Conexion a la base de datos
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */


@Configuration
public class MyDataSource {
    @Bean
    public static DataSource getMyDataSource() {
        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setURL("jdbc:oracle:thin:@172.28.201.239:1521:xe");
            dataSource.setUser("C##1DAWMONTESINOS");
            dataSource.setPassword("1234");
            return dataSource;
        }
        catch(SQLException err) {
            err.printStackTrace();
            return null;
        }
    }

}
