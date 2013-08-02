package com.anirudh.ws.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.anirudh.ws.domain.Customer;

/**
 * 
 * @author anirudh
 * this class is used as we are not using JAXB to convert XML into object and object into XML(marshelling and unmarshelling)
 */
@Deprecated //We will use JAXB instead to convert Java Objects to XML
public class Util {

	public static void outputCustomer(OutputStream os, Customer cust) {

		PrintStream writer = new PrintStream(os);
		writer.println("<customer id=\"" + cust.getId() + "\">");
		writer.println(" <first-name>" + cust.getFirstName() + "</first-name>");
		writer.println(" <last-name>" + cust.getLastName() + "</last-name>");
		writer.println("</customer>");

	}

	public static Customer readCustomer(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();

			Customer cust = new Customer();
			if (root.getAttribute("id") != null
					&& !root.getAttribute("id").trim().equals("")) {
				cust.setId(Integer.valueOf(root.getAttribute("id")));
			}

			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				if (element.getTagName().equals("first-name")) {
					cust.setFirstName(element.getTextContent());
				} else if (element.getTagName().equals("last-name")) {
					cust.setLastName(element.getTextContent());
				}
			}
			return cust;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

}
