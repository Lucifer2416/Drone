package manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PropertyValues {

	String dbnm, ip, port, unm, pwd;
	String result = "";
	InputStream inputStream;
	InputStream in;

	public boolean isProduction() {
		Object o;
		try {
			o = (new InitialContext()).lookup("java:comp/env/isProduction");
		} catch (NamingException e) {
			o = Boolean.FALSE; // assumes FALSE if the value isn't declared
		}
		return o == null ? Boolean.FALSE : (Boolean) o;
	}

	public PropertyValues() {
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";

			if (isProduction()) {
				System.out.print("p");
			inputStream = new FileInputStream("/opt/tomcat/latest/webapps/conf/config.properties");
			} else {
				System.out.print("d");
				inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			}

			if (inputStream != null) {
				prop.load(inputStream);
//				System.out.println(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			Date time = new Date(System.currentTimeMillis());

			// get the property value and print it out
			this.dbnm = prop.getProperty("DB_NAME");
			ip = prop.getProperty("DB_IP");
			port = prop.getProperty("DB_PORT");
			unm = prop.getProperty("DB_USERNAME");
			pwd = prop.getProperty("DB_PASSWORD");

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getDbnm() {
		return dbnm;
	}

	public void setDbnm(String dbnm) {
		this.dbnm = dbnm;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUnm() {
		return unm;
	}

	public void setUnm(String unm) {
		this.unm = unm;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}