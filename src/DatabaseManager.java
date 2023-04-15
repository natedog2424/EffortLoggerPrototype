// Assigned to: Nathan Anderson

import java.sql.*;

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
	 *                      if the database cannot be created or connected to
	 * 
	 */
	public void connect(Project project) throws SQLException {

		if(connectedProject == project) {
			return;
		}

		// Fetch database information from the project
		String DBName = project.DatabaseName;

		//If no database connection established
		if (conn == null) {
			String DBUrl = App.DBUrl;
			String DBUser = App.user.fullName;
			String DBPassword = App.user.password;
			// Create a connection to the database
			conn = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
		}



		// Check if the database already exists
		boolean databaseExists = false;
		ResultSet resultSet = conn.getMetaData().getCatalogs();
		while (resultSet.next()) {
			String databaseNameInCatalog = resultSet.getString(1);
			if (databaseNameInCatalog.equals(DBName)) {
				databaseExists = true;
				break;
			}
		}
		resultSet.close();

		// If the database doesn't exist, create it
		if (!databaseExists) {
			Statement stmt = conn.createStatement();
			String createDatabaseSql = "CREATE DATABASE " + DBName;
			stmt.executeUpdate(createDatabaseSql);
			stmt.close();
		}

		// Switch to the database
		conn.setCatalog(DBName);
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

	public ResultSet executeQuery(String SQL) throws SQLException{
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(SQL);
	}

	public int executeUpdate(String SQL) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(SQL);
	}

}
