package Catalog;

import Model.DataFile;
import Model.DataFiles;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelFile {

    private static DataFile dataFile;
    private static List<Integer> integerArrayList;
    private static int[] number;

    static DataFiles dataFiles = new DataFiles();
    static {
        dataFiles.setDataFiles(new ArrayList<DataFile>());
        dataFiles.getDataFiles().add(dataFile);
    }

   /* public DataFile write(DataFile dataFile) {
        if (this.dataFiles == null){
            this.dataFiles = new ArrayList<>();
        }
        return dataFiles.add;
    }*/

    public void writeExcel(ArrayList<DataFile> list) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Controllers");
            document.appendChild(root);

            for (DataFile cList : list){
                Element controllers = document.createElement("Controller");
                root.appendChild(controllers);
               // for (int i = 0; i < list.size(); i++){
                   /* Element keys = document.createElement("id");
                    keys.appendChild(document.createTextNode(String.valueOf(list.get(i))));
                    controllers.appendChild()*/

                    Element number = document.createElement("Number");
                    number.appendChild(document.createTextNode(String.valueOf(cList.getNumber())));
                    controllers.appendChild(number);

                    Element code = document.createElement("Code");
                    code.appendChild(document.createTextNode(String.valueOf(cList.getCode())));
                    controllers.appendChild(code);

                    Element time = document.createElement("Time");
                    time.appendChild(document.createTextNode(String.valueOf(cList.getPeriod())));
                    controllers.appendChild(time);

                    Element status = document.createElement("Status");
                    status.appendChild(document.createTextNode(String.valueOf(cList.getStatus())));
                    controllers.appendChild(status);

                    Element r = document.createElement("R");
                    r.appendChild(document.createTextNode(String.valueOf(cList.getR())));
                    controllers.appendChild(r);

                    Element s = document.createElement("S");
                    s.appendChild(document.createTextNode(String.valueOf(cList.getS())));
                    controllers.appendChild(s);

                    Element t = document.createElement("T");
                    t.appendChild(document.createTextNode(String.valueOf(cList.getT())));
                    controllers.appendChild(t);

                    Element u = document.createElement("U");
                    u.appendChild(document.createTextNode(String.valueOf(cList.getU())));
                    controllers.appendChild(u);

                    Element v = document.createElement("V");
                    v.appendChild(document.createTextNode(String.valueOf(cList.getV())));
                    controllers.appendChild(v);

                    Element w = document.createElement("W");
                    w.appendChild(document.createTextNode(String.valueOf(cList.getW())));
                    controllers.appendChild(w);

                    Element moment = document.createElement("Moment");
                    moment.appendChild(document.createTextNode(String.valueOf(cList.getMoment())));
                    controllers.appendChild(moment);

                    Element position = document.createElement("Position");
                    position.appendChild(document.createTextNode(String.valueOf(cList.getPosition())));
                    controllers.appendChild(position);

                    Element cycle = document.createElement("Cycle");
                    cycle.appendChild(document.createTextNode(String.valueOf(cList.getCycleCount())));
                    controllers.appendChild(cycle);
               // }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            try {
                FileWriter writer = new FileWriter("./controller.xml");
                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);
            } catch (TransformerException | IOException e) {
                e.printStackTrace();
            }

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        }

        /*JAXBContext context = JAXBContext.newInstance(DataFiles.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(dataFiles, new File("controller.xml"));
        marshaller.marshal(dataFiles, System.out);*/
    }
}

