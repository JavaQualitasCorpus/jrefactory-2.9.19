package pretty;

/**
 *  Description of the Class
 *
 *@author     Mike Atkinson (Mike)
 *@created    07 July 2003
 */
class Bug_517495 {

	/**
	 *  Constructor for the Bug_517495 object
	 */
	Bug_517495() {
		try {
			//*

			// If the Driver Class is not known, try to load it.
			if (driverMap != null && driverMap.containsKey(jdbcDriver)) {
				driverMap.put(jdbcDriver, Class.forName(jdbcDriver));
			}
			//*/
			connection = DriverManager.getConnection(connectionProperties.getJdbcUrlStr());
		}
		catch (Exception e) {
			throw new ConnectionException("Connection to " + connectionProperties.getJdbcUrlStr() + " could not be established.");
		}
	}

}

