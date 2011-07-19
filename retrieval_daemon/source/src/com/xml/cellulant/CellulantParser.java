package com.xml.cellulant;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * @author devk This Java class is supposed to act as a web daemon. Reads data
 *         from XML files and saves them in a database This data is then
 *         displayed on the Nyumbani Stock Traders web page
 */
public class CellulantParser implements Runnable {
	String textElement = "";
	String valuesElement = "";
	String typeElement = "";

	String tikerName = "";
	String comName = "";
	String stockType = "";
	String shareQuant = "";
	String shareDesc = "";
	String tradingValue = "";
	String highestYear = "";
	String lowestYear = "";
	String opening2day = "";
	String currentPrice = "";
	String changePrice = "";
	String sharesMoved2day = "";

	String days_tickerName = "";
	String days_companyName = "";
	String days_shareDesc = "";
	String days_stockType = "";
	String days_shareQuant = "";

	String days_CompanyTradingVal = "";
	String days_highestValue = "";
	String days_lowestValue = "";
	String days_openingClosingPrice = "";
	String days_sharesMoved = "";

	boolean textGet = false;
	boolean valuesGet = false;
	boolean typeGet = false;

	Connection conn;
	Statement st;

	ReadProperties props;
	GenerateRSS rssFeed;
	private static org.apache.log4j.Logger log = Logger.getLogger(CellulantParser.class); // saves all processes in this class to the nyumbani.log file

	public CellulantParser() {
		props = new ReadProperties();// reading external user variables

		rssFeed = new GenerateRSS();// generate rss xml file
		log.info("rss.xml file successfully generated");
		getConnection();// get connection to the database

	}

	/**
	 * this method sets up the MySQL database connection properties using the
	 * MySQL database connecter driver
	 */
	public void getConnection() {
		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(props.getServerName());
			ds.setPortNumber(3306);
			ds.setDatabaseName("Nyumbani");
			ds.setUser(props.getDatabaseLoginUserName());
			ds.setPassword(props.getDatabasePassword());
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed to connect to the MySQL database.....");

		}
	}

	/**
	 * this method handles the delay when parsing of stock_prices.xml file and
	 * saving records to the database. It is supposed to pause execution from 5
	 * minutes.
	 */
	public void parseAndDelayStockPricesXML() {

		try {
			clearAllTables(); // prevents bug (duplicates in PK column) when records are being inserted.
			handleParseStockPricesXML("http://zion.cellulant.com/training/stock_prices.xml");
			log.info("parsing halted for 5 minutes");
			Thread.sleep(300000); // five minutes = 300000millsec;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while parsing stock_prices.xml(network error)");
		}
	}

	public void parseDaysStocksXML() {
		try{
			clearAllTables(); // prevents bug (duplicates in PK column) when records are being inserted.
       		handleParseDayStocks("http://zion.cellulant.com/training/days_stocks.xml");
		}catch(Exception e){
			log.error("error while parsing daysStocksXML (network error)");
		}
		// TODO: handle exception
	}

	/**
	 * This method is supposed to delete all records from the table using the
	 * MySQL function truncate()
	 */
	public void clearAllTables() {
		//log.info("clearing all database tables");
		String truncateDetails = "truncate TradingCompanies";
		String truncateStock = "truncate CompanyTradingHistory";
		try {
			Statement st = conn.createStatement();
			st.execute(truncateDetails);
			st.execute(truncateStock);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error while clearing tables");
		}
	}

	/**
	 * This method is supposed to insert all records obtained from the
	 * stock_prices.xml file. It is called once the parsing is complete.
	 */
	public void insertStockXML() {

		String insertCompanyDetails = "insert into TradingCompanies(Tiker_Name,Company_Name,Stock_Type,Share_Quantity) values ('"
				+ tikerName
				+ "','"
				+ comName
				+ "','"
				+ stockType
				+ "','"
				+ shareQuant + "')";
		int moved = Integer.parseInt(sharesMoved2day);

		String insertStockDetails = "insert into CompanyTradingHistory values('"
				+ tradingValue
				+ "','"
				+ highestYear
				+ "','"
				+ lowestYear
				+ "','"
				+ opening2day
				+ "','"
				+ currentPrice
				+ "','"
				+ changePrice + "','" + moved + "','" + tikerName + "')";
		try {
			Statement st = conn.createStatement();
			st.execute(insertCompanyDetails);
			st.execute(insertStockDetails);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("error inserting stock_prices.xml data");
		}
	}

	public void insertDaysStockXMLdata() {
		String insertDaysCompanyDetails = "insert into TradingCompanies values ('"
				+ days_tickerName
				+ "','"
				+ days_companyName
				+ "','"
				+ days_stockType
				+ "','"
				+ days_shareQuant
				+ "','"
				+ days_shareDesc + "')";
		int moved = Integer.parseInt(days_sharesMoved);
		String insertDaysStockDetails = "insert into CompanyTradingHistory(Company_Trading_Value,Highest_Value_This_Year,Lowest_Value_This_Year,Opening_Price_Today,Current_Trading_Price,Shares_Moved,Tiker_Name)values('"
				+ days_CompanyTradingVal
				+ "','"
				+ days_highestValue
				+ "','"
				+ days_lowestValue
				+ "','"
				+ days_openingClosingPrice
				+ "','"
				+ days_openingClosingPrice
				+ "',"
				+ moved
				+ ",'"
				+ days_tickerName + "')";
		try {
			Statement st = conn.createStatement();
			st.execute(insertDaysCompanyDetails);
			st.execute(insertDaysStockDetails);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Error while inserting days_stock.xml data");
		}
	}

	/**
	 * This method is supposed to read the XML file, parse it and then split the
	 * obtained data into tokens using the string tokenizer.It is delayed for 5
	 * minutes once executed once. The DefaultHandler methods deal with the
	 * parsing events.
	 * 
	 */
	public void handleParseStockPricesXML(String fileToParse) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			log.info("Parsing stock_prices.xml document,splitting data obtained into tokens and inserting records to the database");
			DefaultHandler handler = new DefaultHandler() {
				StringBuilder builderText = new StringBuilder();
				StringBuilder builderVals = new StringBuilder();
				StringBuilder builderType = new StringBuilder();

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("TEXT")) {

						textGet = true;
						builderText.setLength(0);

					}
					if (qName.equalsIgnoreCase("VALUES")) {
						valuesGet = true;
						builderVals.setLength(0);
					}
					if (qName.equalsIgnoreCase("TYPE")) {
						typeGet = true;
						builderType.setLength(0);
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (textGet) {

						StringTokenizer tokenizer1 = new StringTokenizer(
								builderText.toString(), "|");
						int counter = 0;

						// loop handles splitting of data into respective fields
						while (tokenizer1.hasMoreTokens()) {

							switch (counter) {
							case 0:
								tikerName = tokenizer1.nextToken();
							case 1:
								comName = tokenizer1.nextToken();
							case 2:
								stockType = tokenizer1.nextToken();
							case 3:
								shareQuant = tokenizer1.nextToken();
							}
							counter++;

						}
						textGet = false;
					}

					if (valuesGet) {
						StringTokenizer tokenizer2 = new StringTokenizer(
								builderVals.toString(), "|");
						int valuescount = 0;
						while (tokenizer2.hasMoreTokens()) {
							switch (valuescount) {
							case 0:
								String newSt = tokenizer2.nextToken().replace(
										"'", ",");
								tradingValue = newSt;

							case 1:
								highestYear = tokenizer2.nextToken();
							case 2:
								lowestYear = tokenizer2.nextToken();
							case 3:
								opening2day = tokenizer2.nextToken();

							case 4:
								currentPrice = tokenizer2.nextToken();
							case 5:
								changePrice = tokenizer2.nextToken();
							case 6:
								sharesMoved2day = tokenizer2.nextToken();
							}
							valuescount++;
						}

						valuesGet = false;
					}
					if (qName.equals("STOCK")) {
						insertStockXML();
						
					}

				}

				/**
				 * appends all character to the StringBuilder;
				 */
				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (textGet) {
						builderText.append(ch, start, length);
					}
					if (valuesGet) {
						builderVals.append(ch, start, length);
					}
					if (typeGet) {
						builderType.append(ch, start, length);
					}

				}

			};
			saxParser.parse(fileToParse, handler);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error during parsing of stock_prices.xml... network connection is unavailable or weak **connection should be strong**");
		}

	}

	public void handleParseDayStocks(String fileToParse) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			log.info("Parsing days_stock.xml document and splitting data obtained into tokens");

			DefaultHandler handler = new DefaultHandler() {
				StringBuilder builderDaysText = new StringBuilder();
				StringBuilder builderDaysVals = new StringBuilder();

				boolean getTextTag = false;
				boolean getValueTag = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (qName.equalsIgnoreCase("TEXT")) {
						builderDaysText.setLength(0);
						getTextTag = true;
					}
					if (qName.equalsIgnoreCase("VALUES")) {
						builderDaysVals.setLength(0);
						getValueTag = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if (getTextTag) {
						StringTokenizer tokenizer1 = new StringTokenizer(
								builderDaysText.toString(), "|");
						int counter = 0;

						// loop handles splitting of data into respective fields
						while (tokenizer1.hasMoreTokens()) {

							switch (counter) {
							case 0:
								days_tickerName = tokenizer1.nextToken();
							case 1:
								days_companyName = tokenizer1.nextToken();
							case 2:
								days_shareDesc = tokenizer1.nextToken();
							case 3:
								days_stockType = tokenizer1.nextToken();
							case 4:
								days_shareQuant = tokenizer1.nextToken();
							}
							counter++;

						}
						getTextTag = false;

					}
					if (getValueTag) {
						StringTokenizer tokenizer2 = new StringTokenizer(
								builderDaysVals.toString(), "|");
						int valuescount = 0;

						while (tokenizer2.hasMoreTokens()) {
							switch (valuescount) {
							case 0:
								String newSt = tokenizer2.nextToken().replace(
										"'", ",");
								days_CompanyTradingVal = newSt;

							case 1:
								days_highestValue = tokenizer2.nextToken();
							case 2:
								days_lowestValue = tokenizer2.nextToken();
							case 3:
								days_openingClosingPrice = tokenizer2
										.nextToken();
							case 4:
								days_sharesMoved = tokenizer2.nextToken();
							}
							valuescount++;
						}

						getValueTag = false;
					}

					if (qName.equals("STOCK")) {

						insertDaysStockXMLdata();// saves all details contained
						// in the days_stocks.xml
						// file

					}
				}

				/**
				 * appends all character to the StringBuilder;
				 */
				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (getTextTag) {
						builderDaysText.append(ch, start, length);
					}
					if (getValueTag) {
						builderDaysVals.append(ch, start, length);
					}

				}

			};
			saxParser.parse(fileToParse, handler);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error during parsing of days_stocks.xml... network connection is unavailable or weak ** connection should be strong**");
		}
	}

	/**
	 * Software starts execution from this point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("Software daemon has been started");
		System.out.println("Monitor Processes via nyumbani.log file");
		CellulantParser saxcell = new CellulantParser();
		log.info("CellulantParse class successfully initialised");

		Thread timer = new Thread(saxcell);
		timer.start();
		log.info("Time thread instance started");
	}

	// run method is suppose to check if days are Monday to Friday and if it is
	// trading hours or not trading hours
	public void run() {
		try {
			while (true) {
				String time = new SimpleDateFormat(
						"EEE yyyy.MM.dd G 'at' HH:mm:ss").format(new Date(
						System.currentTimeMillis()));
			    log.info("current time" + time);
				String segment = time.substring(21, 23);
				String dayOfWeek = time.substring(0, 3);
				if (dayOfWeek.equals("Mon") || dayOfWeek.equals("Tue")
						|| dayOfWeek.equals("Wed") || dayOfWeek.equals("Thu")
						|| dayOfWeek.equals("Fri")) {
					if (segment.equals("09") || segment.equals("10")
							|| segment.equals("11") || segment.equals("12")
							|| segment.equals("13") || segment.equals("14")) {
						log.info("Trading Hours,Parsing stock_prices.xml:" + time);
						parseAndDelayStockPricesXML();
					}

					else {
						log.info("Not Trading Hours 3 pm to 9a.m(tomorrow), now parsing days_stocks.xml file" + time);
						parseDaysStocksXML();
						Thread.sleep(1000);
					}
				} else {
					log.info("Not trading day, details not obtained.Week days only" + time );
					Thread.sleep(1000);
					// clearAllTables();
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			log.error("Error while starting timer thread" );
		}
	}
}
