package agendacultural.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * Class de suporte ao acesso a base de dados
 *
 */
public class DBUtils {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String BD = "jdbc:mysql://localhost/AgendaCultural";
	private static final String LOGIN = "agenda";
	private static final String PASSWORD = "agenda";
			

	/**
	 * Método que permite abrir ligação à base de dado
	 * @return @Connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
    public static Connection open() throws SQLException, ClassNotFoundException {
        Connection con = null;
        
        Class.forName(DRIVER);
        con = DriverManager.getConnection(BD,LOGIN,PASSWORD);

        return con;
    }
 
    /**
     * Método que permite fechar ligação à base de dados
     * @param con @Connection
     * @throws SQLException
     */
    public static void close(Connection con) throws SQLException{
        if (con != null) con.close();
    }

    /**
     *  Método que permite fechar ligação à base de dados
     * @param pst @PreparedStatement
     * @param con @Connection
     * @throws SQLException
     */
    public static void close(PreparedStatement pst, Connection con) throws SQLException{    	
        if (pst != null) pst.close();
        if (con != null) con.close();
    }

    /**
     * Método que permite fechar ligação à base de dados
     * @param st @Statement
     * @param con @Connection
     * @throws SQLException
     */
    public static void close(Statement st, Connection con) throws SQLException{
       if (st != null) st.close();
       if (con != null) con.close();
    }
}
