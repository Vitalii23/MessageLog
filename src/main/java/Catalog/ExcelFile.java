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

    // Status convert hex data
    public static String statusHex(int x){
        x = 12384;
        String hex = Integer.toHexString(x);
        System.out.println(x);
        return hex;
    }

    public static void main(String[] args) {
        String xmlFilePath = "E:/Projects/IdeaProjects/MessageLog/src/main/java/Catalog/controller.xml";
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("controller");
            document.appendChild(root);

            Element number = document.createElement("Number");
            number.appendChild(document.createTextNode("№"));
            root.appendChild(number);

            Element code = document.createElement("CodeRegister");
            code.appendChild(document.createTextNode("Код записи"));
            root.appendChild(code);

            Element date = document.createElement("Date");
            date.appendChild(document.createTextNode("Время"));
            root.appendChild(date);

            Element status = document.createElement("Status");
            status.appendChild(document.createTextNode("Регистер статуса"));
            root.appendChild(status);

            Element r = document.createElement("R");
            r.appendChild(document.createTextNode("Фаза R"));
            root.appendChild(r);

            Element s = document.createElement("S");
            s.appendChild(document.createTextNode("Фаза S"));
            root.appendChild(s);

            Element t = document.createElement("T");
            t.appendChild(document.createTextNode("Фаза T"));
            root.appendChild(t);

            Element u = document.createElement("U");
            u.appendChild(document.createTextNode("Фаза U"));
            root.appendChild(u);

            Element v = document.createElement("V");
            v.appendChild(document.createTextNode("Фаза V"));
            root.appendChild(v);

            Element w = document.createElement("W");
            w.appendChild(document.createTextNode("Фаза W"));
            root.appendChild(w);

            Element moment = document.createElement("Moment");
            moment.appendChild(document.createTextNode("Момент"));
            root.appendChild(moment);

            Element position = document.createElement("Position");
            position.appendChild(document.createTextNode("Положение"));
            root.appendChild(position);

            Element cycle = document.createElement("Cycle");
            cycle.appendChild(document.createTextNode("Счетчик циклов"));
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
