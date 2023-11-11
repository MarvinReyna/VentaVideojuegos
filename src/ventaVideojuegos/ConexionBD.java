package ventaVideojuegos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mariadb://localhost:3306/videojuegos";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "1234";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    
}

