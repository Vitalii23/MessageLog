package Catalog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ExcelFile {

    public static void main(String[] args) {
        String xmlFilePath = "E:/Projects/IdeaProjects/MessageLog/src/main/java/Catalog/controller.xml";

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("controller");
            document.appendChild(root);

            Element number = document.createElement("Number");
            root.appendChild(number);

            Element code = document.createElement("CodeRegister");
            root.appendChild(code);

            Element date = document.createElement("Date");
            root.appendChild(date);

            Element status = document.createElement("Status");
            root.appendChild(status);

            Element r = document.createElement("R");
            root.appendChild(r);

            Element s = document.createElement("S");
            root.appendChild(s);

            Element t = document.createElement("T");
            root.appendChild(t);

            Element u = document.createElement("U");
            root.appendChild(u);

            Element v = document.createElement("V");
            root.appendChild(v);

            Element w = document.createElement("W");
            root.appendChild(w);

            Element moment = document.createElement("Moment");
            root.appendChild(moment);

            Element position = document.createElement("Position");
            root.appendChild(position);

            Element cycle = document.createElement("Cycle");
            root.appendChild(cycle);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

}
