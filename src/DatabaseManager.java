// Assigned to: Nathan Anderson
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DatabaseManager {

	private Connection conn = null;
	private Project connectedProject = null;

	/**
	 * Connect to the database using the given project's database information.
	 * If the database doesn't exist, create it.
	 * If the database is already connected, disconnect from it first.
	 * 
	 * @param project
	 * 
	 * @throws SQLException
	 * 		if the database cannot be created or connected to
	 */
	public void connect(Project project) throws SQLException {

		if (connectedProject == project) {
			return;
		}

		if (conn != null) {
			disconnect();
		}
	
		// Fetch database information from the project
		String DBName = project.DatabaseName;
		String DBPath = App.DBPath + DBName + ".db";
	
		// Connect to the database
		conn = DriverManager.getConnection(DBPath);
		connectedProject = project;
	}
	

	/**
	 * Disconnect any existing connection to the database.
	 * 
	 * @throws SQLException
	 *                      if the database cannot be disconnected from.
	 * 
	 */
	public void disconnect() throws SQLException {
		if (conn != null) {
			conn.close();
		}

		conn = null;
		connectedProject = null;

	}

	/**
	 * This method is used to execute a database query with the specified parameters.
	 * 
	 * @param query  The query to run.
	 * @param params The parameters to use in the query.
	 * @return A ResultSet containing the result of the query.
	 * @throws SQLException
	 * @throws InvalidKeyException
	 * 		if the key is invalid
	 */
	public ResultSet executeQuery(String query, Object... params) throws SQLException, InvalidKeyException {
		PreparedStatement statement = conn.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			statement.setObject(i + 1, param);
		}

		ResultSet results = statement.executeQuery();

		// return the result set
		return results;
	}

	
	/**
	 * This method is used to execute a database update with the specified
	 * parameters.
	 * 
	 * @param query  The query to execute
	 * @param params The parameters to use for the query
	 * @return The number of rows affected
	 * @throws SQLException If there is an error executing the query
	 * @throws InvalidKeyException
	 * 		if the key is invalid
	 */
	public int executeUpdate(String query, Object... params) throws SQLException, InvalidKeyException {
		// Prepare the statement
		PreparedStatement statement = conn.prepareStatement(query);
		// Iterate through the parameters
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			statement.setObject(i + 1, param);
		}
		// Execute the update and return the number of rows affected
		return statement.executeUpdate();
	}

	public Project getConnectedProject() {
		if(conn == null) return null;
		return connectedProject;
	}
}

