package Catalog;

import Model.DataFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelFile {

    private DataFile dataFile = new DataFile();

    public void writeExcel(ArrayList<DataFile> list) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Контроллеры");
            document.appendChild(root);

            for (DataFile cList : list){
                Element controllers = document.createElement("Контроллер");
                root.appendChild(controllers);

                    Element number = document.createElement("Номер");
                    number.appendChild(document.createTextNode(String.valueOf(cList.getNumber())));
                    controllers.appendChild(number);

                    Element code = document.createElement("Код_Регистра");
                    code.appendChild(document.createTextNode(String.valueOf(cList.getCode())));
                    controllers.appendChild(code);

                    Element time = document.createElement("Время");
                    time.appendChild(document.createTextNode(String.valueOf(cList.getPeriod())));
                    controllers.appendChild(time);

                    Element status = document.createElement("Статус");
                    status.appendChild(document.createTextNode(String.valueOf(cList.getStatus())));
                    controllers.appendChild(status);

                    Element r = document.createElement("Фаза_R");
                    r.appendChild(document.createTextNode(String.valueOf(cList.getR())));
                    controllers.appendChild(r);

                    Element s = document.createElement("Фаза_S");
                    s.appendChild(document.createTextNode(String.valueOf(cList.getS())));
                    controllers.appendChild(s);

                    Element t = document.createElement("Фаза_T");
                    t.appendChild(document.createTextNode(String.valueOf(cList.getT())));
                    controllers.appendChild(t);

                    Element u = document.createElement("Фаза_U");
                    u.appendChild(document.createTextNode(String.valueOf(cList.getU())));
                    controllers.appendChild(u);

                    Element v = document.createElement("Фаза_V");
                    v.appendChild(document.createTextNode(String.valueOf(cList.getV())));
                    controllers.appendChild(v);

                    Element w = document.createElement("Фаза_W");
                    w.appendChild(document.createTextNode(String.valueOf(cList.getW())));
                    controllers.appendChild(w);

                    Element moment = document.createElement("Момент");
                    moment.appendChild(document.createTextNode(String.valueOf(cList.getMoment())));
                    controllers.appendChild(moment);

                    Element position = document.createElement("Положение");
                    position.appendChild(document.createTextNode(String.valueOf(cList.getPosition())));
                    controllers.appendChild(position);

                    Element cycle = document.createElement("Счетчик_циклов");
                    cycle.appendChild(document.createTextNode(String.valueOf(cList.getCycleCount())));
                    controllers.appendChild(cycle);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);

            try {
                String name = "./Контроллеры/Контроллер " + dataFile.getName() + ".xml";
                File file = new File(name);
                File directory = file.getParentFile();
                if (null != directory){
                    directory.mkdir();
                }
                FileWriter writer = new FileWriter(file);
                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);
            } catch (TransformerException | IOException e) {
                System.out.print("Ошибка записи файла" + "\n");
                e.printStackTrace();
            }

            System.out.println("Файл записан" + "\n");

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }
}

