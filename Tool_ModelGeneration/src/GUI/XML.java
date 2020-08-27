package GUI;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import GUI.FirstInputWindow;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class XML {



		private static String addr = "";
		private static ArrayList<String> classes;
		private static ArrayList<String> attr;
			  public static void main(String[] args) throws IOException {

				  
			    try {

				File fXmlFile = new File("E:/ECLIPSE/WorkSpace/Tool_ModelGeneration/src/Models/car.ecore");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
						
				//optional, but recommended
				//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
				doc.getDocumentElement().normalize();

				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
						
				NodeList nList = doc.getElementsByTagName("eClassifiers");
						
				System.out.println("----------------------------");
				classes = new ArrayList<String>();
				attr = new ArrayList<String>();
				int i = 0;
				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);
							
					System.out.println("\nCurrent Element :" + nNode.getAttributes().getNamedItem("name"));
							
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						//String name = eElement.getAttribute("name");
						classes.add(i,eElement.getAttribute("name"));
						Element eEle = (Element) (eElement.getElementsByTagName("eStructuralFeatures").item(0));
						if (eEle != null)
						{
							System.out.println("estructural : " + eEle.getAttribute("name"));
							
							attr.add(i,eEle.getAttribute("name"));
							
						}
						System.out.println("Classes: " + i + "-" + classes.get(i));
							i++;
						
					/*	System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
						System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
						System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
						System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());		*/
					}
			}
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			  }

			}

