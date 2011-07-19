package com.xml.cellulant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author devk this file generates the rss.xml file to be used for feeds. It
 *         depends of ReadProperties class to obtain information about the web
 *         host
 */
public class GenerateRSS {

	public GenerateRSS() {
		try {
			ReadProperties props = new ReadProperties();
			String webhostAdddress = props.getWebServerAddress();
			// Create instance of DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// Get the DocumentBuilder
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			// Create blank DOM Document
			Document doc = docBuilder.newDocument();

			// create the root element
			Element root = doc.createElement("rss");
			root.setAttribute("version", "2.0");
			doc.appendChild(root);

			Element channel = doc.createElement("channel");
			root.appendChild(channel);

			Element title = doc.createElement("title");
			title.setTextContent("RSS Feed For Nyumbani Traders");
			channel.appendChild(title);

			Element desc = doc.createElement("descriptions");
			desc.setTextContent("Current Info on stocks");
			channel.appendChild(desc);

			Element lang = doc.createElement("language");
			lang.setTextContent("en-us");
			channel.appendChild(lang);

			Element item1 = doc.createElement("item");
			channel.appendChild(item1);
			Element titleCompany1 = doc.createElement("title");
			titleCompany1.setTextContent("Trading Companies Details");
			item1.appendChild(titleCompany1);
			Element link1 = doc.createElement("link");
			link1.setTextContent("http://" + webhostAdddress
					+ "/source/companies.php");
			item1.appendChild(link1);
			Element companyDesc1 = doc.createElement("description");
			companyDesc1.setTextContent("Traders in Nyumbani Stock");
			item1.appendChild(companyDesc1);

			Element item2 = doc.createElement("item");
			channel.appendChild(item2);
			Element titleCompany2 = doc.createElement("title");
			titleCompany2.setTextContent("Top 5 Movers");
			item2.appendChild(titleCompany2);
			Element link2 = doc.createElement("link");
			link2.setTextContent("http://" + webhostAdddress
					+ "/source/top5Movers.php");
			item2.appendChild(link2);
			Element companyDesc2 = doc.createElement("description");
			companyDesc2
					.setTextContent("5 Trading Companies With the highest trading values");
			item2.appendChild(companyDesc2);

			Element item3 = doc.createElement("item");
			channel.appendChild(item3);
			Element titleCompany3 = doc.createElement("title");
			titleCompany3.setTextContent("Bottom 5 Movers");
			item3.appendChild(titleCompany3);
			Element link3 = doc.createElement("link");
			link3.setTextContent("http://" + webhostAdddress
					+ "/source/bottom5Movers.php");
			item3.appendChild(link3);
			Element companyDesc3 = doc.createElement("description");
			companyDesc3
					.setTextContent("5 Trading Companies With the lowest trading values");
			item3.appendChild(companyDesc3);

			Element item4 = doc.createElement("item");
			channel.appendChild(item4);
			Element titleCompany4 = doc.createElement("title");
			titleCompany4.setTextContent("Top Five Prices");
			item4.appendChild(titleCompany4);
			Element link4 = doc.createElement("link");
			link4.setTextContent("http://" + webhostAdddress
					+ "/source/top5prices.php");
			item4.appendChild(link4);
			Element companyDesc4 = doc.createElement("description");
			companyDesc4
					.setTextContent("Top five prices traded by different companies");
			item4.appendChild(companyDesc4);

			Element item5 = doc.createElement("item");
			channel.appendChild(item5);
			Element titleCompany5 = doc.createElement("title");
			titleCompany5.setTextContent("Bottom Five Prices");
			item5.appendChild(titleCompany5);
			Element link5 = doc.createElement("link");
			link5.setTextContent("http://" + webhostAdddress
					+ "/source/bottom5Prices.php");
			item5.appendChild(link5);
			Element companyDesc5 = doc.createElement("description");
			companyDesc5
					.setTextContent("Lowest five prices traded by different companies");
			item5.appendChild(companyDesc5);

			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			Source src = new DOMSource(doc);
			Result dest = new StreamResult(new BufferedOutputStream(
					new FileOutputStream(new File("rss.xml"))));
			aTransformer.transform(src, dest);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
