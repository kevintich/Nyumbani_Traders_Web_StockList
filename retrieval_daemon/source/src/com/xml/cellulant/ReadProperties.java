package com.xml.cellulant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is supposed to read the values from the properties file
 * It has getter and settter method which are to be used by other classes to obtain data
 * 
 * 
 *
 */
public class ReadProperties {

	String serverName, databaseLoginUserName, databasePassword;
	Properties props;
	String portNumber;
	String webServerAddress;

	public ReadProperties() {
		//loads the properties file
		props = new Properties();
		try {
			props
					.load(new BufferedReader(new FileReader(
							"nyumbani.properties")));
			serverName = props.getProperty("serverName");
			databaseLoginUserName = props.getProperty("username");
			portNumber = props.getProperty("port");
			databasePassword = props.getProperty("password");
			webServerAddress = props.getProperty("webhostaddress");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getWebServerAddress() {
		return webServerAddress;
	}

	public void setWebServerAddress(String webServerAddress) {
		this.webServerAddress = webServerAddress;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getServerName() {

		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
		props.getProperty("databaseName");
	}

	

	public String getDatabaseLoginUserName() {
		return databaseLoginUserName;
	}

	public void setDatabaseLoginUserName(String databaseLoginUserName) {
		this.databaseLoginUserName = databaseLoginUserName;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

}
