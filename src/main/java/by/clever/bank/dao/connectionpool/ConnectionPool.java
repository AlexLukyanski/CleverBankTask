package by.clever.bank.dao.connectionpool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * This class represent connection pool for JDBC. As simple as it looks.
 */
public final class ConnectionPool {

	private BlockingQueue<Connection> freeConnectionQueue;
	private BlockingQueue<Connection> takenConnectionQueue;
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;

	private volatile static boolean closingConnectionFlag = false;

	private ConnectionPool() {

		DBResourceManager dbResourseManager = DBResourceManager.getInstance();
		this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourseManager.getValue(DBParameter.DB_URL);
		this.user = dbResourseManager.getValue(DBParameter.DB_USER);
		this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);

		try {
			this.poolSize = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POLL_SIZE));
		} catch (NumberFormatException e) {
			poolSize = 5;
		}
	}

	private static class ConnectionPoolHepler {

		private static ConnectionPool instance = new ConnectionPool();

	}

	public static ConnectionPool getInstance() throws ConnectionPoolException {

		return ConnectionPoolHepler.instance;

	}

	/**
	 *
	 * @throws ConnectionPoolException
	 */
	public void initPoolData() throws ConnectionPoolException {

		try {
			Class.forName(driverName);
			takenConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			freeConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);

				PooledConnection pooledConnection = new PooledConnection(connection);
				freeConnectionQueue.add(pooledConnection);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException("SQLException in ConnectionPool", e);
		} catch (ClassNotFoundException e) {
			throw new ConnectionPoolException("Can't find database driver class", e);
		}
	}

	/**
	 *
	 * @throws ConnectionPoolException
	 */
	public void clearConnectionQueue() throws ConnectionPoolException {

		closingConnectionFlag = true;

		try {
			closeConnectionsQueue(takenConnectionQueue);
			closeConnectionsQueue(freeConnectionQueue);
		} catch (SQLException e) {
			throw new ConnectionPoolException("Error closing the connection.", e);

		}
	}

	/**
	 *
	 * @return Connection
	 * @throws ConnectionPoolException
	 */
	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;

		try {
			if (!closingConnectionFlag) {

				connection = freeConnectionQueue.take();
				takenConnectionQueue.add(connection);

			} else {
				throw new ConnectionPoolException("Attempt to take closing connection");
			}
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error connecting to the data source.", e);
		}
		return connection;
	}

	/**
	 *
	 * @param con
	 * @param st
	 * @param rs
	 * @throws ConnectionPoolException
	 */
	public void closeConnection(Connection con, Statement st, ResultSet rs) throws ConnectionPoolException {
		try {
			con.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Connection isn't return to the pool.", e);
		}

		try {
			rs.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("ResultSet isn't closed.", e);
		}

		try {
			st.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Statement isn't closed.", e);
		}
	}

	/**
	 *
	 * @param con
	 * @param ps
	 * @throws ConnectionPoolException
	 */
	public void closeConnection(Connection con, PreparedStatement ps) throws ConnectionPoolException {
		try {
			con.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Connection isn't return to the pool.", e);
		}

		try {
			ps.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("PreparedStatement isn't closed.", e);
		}
	}

	/**
	 *
	 * @param con
	 * @param ps
	 * @param rs
	 * @throws ConnectionPoolException
	 */
	public void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) throws ConnectionPoolException {
		try {
			con.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Connection isn't return to the pool.", e);
		}
		try {
			rs.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("ResultSet isn't closed.", e);
		}

		try {
			ps.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("PreparedStatement isn't closed.", e);
		}
	}

	/**
	 *
	 * @param con
	 * @param st
	 * @throws ConnectionPoolException
	 */
	public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {

		try {
			con.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Connection isn't return to the pool.", e);
		}

		try {
			st.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException("Statement isn't closed.", e);
		}
	}

	/**
	 *
	 * @param queue
	 * @throws SQLException
	 */
	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {

		Connection connection;

		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}
	}

	private class PooledConnection implements Connection {
		private Connection connection;

		public PooledConnection(Connection c) throws SQLException {
			this.connection = c;
			this.connection.setAutoCommit(true);
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		@Override
		public void close() throws SQLException {
			if (connection.isClosed()) {
				throw new SQLException("Attempting to close closed	connection.");
			}
			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}
			if (!takenConnectionQueue.remove(this)) {
				throw new SQLException("Error deleting connection from the given away connections pool.");
			}
			if (!freeConnectionQueue.offer(this)) {
				throw new SQLException("Error allocating connection in the pool.");
			}
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor arg0) throws SQLException {
			connection.abort(arg0);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public void releaseSavepoint(Savepoint arg0) throws SQLException {
			connection.releaseSavepoint(arg0);
		}

		@Override
		public void rollback(Savepoint arg0) throws SQLException {
			connection.rollback(arg0);
		}

		@Override
		public void setClientInfo(Properties arg0) throws SQLClientInfoException {
			connection.setClientInfo(arg0);
		}

		@Override
		public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
			connection.setNetworkTimeout(arg0, arg1);
		}

		@Override
		public void setSchema(String arg0) throws SQLException {
			connection.setSchema(arg0);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
			connection.setTypeMap(arg0);
		}

	}

}
