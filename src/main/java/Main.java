import Connection.Master;
import Model.ComPort;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Scanner;

public class Main {

    private static Scanner comand = new Scanner(System.in);
   // private static int number = comand.nextInt();
    private static ComPort comPort;

    public static void main(String[] args) throws ModbusIOException, JAXBException, ParserConfigurationException {
        if (args.length < 1){
            System.out.println("Usage:\r\n" +
                    "java Main start");
            return;
        }

        /*System.out.println("Welcome to program. Please, write comand\r\n" +
                "Run a program <1>\n" +
                "Settings comPort <2>\n" +
                "Settings connect <3>\n" +
                "Exit program <4>");*/

        if (args[0].equals("start")){
            Master master = new Master();
            master.start(0, "BAUD_RATE_19200", 8, "NONE", 1);
            /*switch (number){
                case 1:

                    /*master.run(comPort.getDevice(), comPort.getBaudRate(), comPort.getDataBits(),
                            comPort.getParity(), comPort.getStopBits());
                    System.out.println("File Done");
                case 2:
                    System.out.println(Arrays.toString(SerialPortList.getPortNames()));
                    comPort.setDevice(comand.nextInt());
                case 3:
                    System.out.println("Write parameters it connect. (Man/Auto)");
                    if (comand.nextLine().equals("Man")){
                        System.out.print("Write BaudRate: = ");
                        comPort.setBaudRate(comand.nextLine());

                        System.out.print("Write Data Bits: = ");
                        comPort.setDataBits(comand.nextInt());

                        System.out.print("Write Parity: = ");
                        comPort.setParity(comand.nextLine());

                        System.out.print("Write Stop Bits: = ");
                        comPort.setStopBits(comand.nextInt());

                        return;
                    } else if (comand.nextLine().equals("Auto")){
                        comPort.setBaudRate("BAUD_RATE_19200");
                        comPort.setDataBits(8);
                        comPort.setParity("NONE");
                        comPort.setStopBits(1);

                        System.out.println("Parametres automatic write");
                        return;
                    }
                case 4:
                    System.out.println("Good bye");
                    System.exit(0);
                default:
                    System.out.println("Error comand.Try, again");
                    return;
            }*/
        } else {
            System.out.println("Error Program, try again");
            return;
        }

    }
}
