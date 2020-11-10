package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Контроллеры")
public class DataFiles {
    private List<DataFile> dataFiles = null;

    public List<DataFile> getDataFiles() {
        return dataFiles;
    }

    @XmlElement(name = "Контроллер")
    public void setDataFiles(List<DataFile> dataFiles) {
        this.dataFiles = dataFiles;
    }
}
