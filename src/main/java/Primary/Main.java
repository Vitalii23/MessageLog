package Primary;

import Connection.Master;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import jssc.SerialPortList;

import java.util.Scanner;

public class Main {
    private static Scanner comand;
    private static int number;
    private static String parameters;
    private static SerialParameters serialParameters;

    private static int run(){
        System.out.println("Добро пожаловать. Пожалуйста, введите команду\r\n" +
                "Запуск программы <1>\n" +
                "Параметры порта <2>\n" +
                "Выход программы <3>");
        comand = new Scanner(System.in);
        number = comand.nextInt();
        switch (number){
            case 1:
                Master master = new Master();

                master.start(serialParameters.getDevice(),
                        SerialPort.BaudRate.getBaudRate(serialParameters.getBaudRate()),
                             serialParameters.getDataBits(),
                             serialParameters.getStopBits(),
                             serialParameters.getParity());
                return run();
            case 2:
                System.out.print("Выберите параметры подключения. (m или a): ");
                comand = new Scanner(System.in);
                parameters = comand.nextLine();
                if (parameters.equals("m")){
                    String[] devList = SerialPortList.getPortNames();

                    for (String d : devList){
                        System.out.print(d + "\n");
                    }

                    if (devList.length > 0) {

                        serialParameters = new SerialParameters();

                        comand = new Scanner(System.in);
                        System.out.print("Введите номер порта: " );
                        serialParameters.setDevice(comand.nextLine());

                        comand = new Scanner(System.in);
                        System.out.print("Бит в секунду: ");
                        serialParameters.setBaudRate(SerialPort.BaudRate.valueOf(comand.nextLine()));

                        comand = new Scanner(System.in);
                        System.out.print("Биты данных: ");
                        serialParameters.setDataBits(comand.nextInt());

                        comand = new Scanner(System.in);
                        System.out.print("Четность: ");
                        serialParameters.setParity(SerialPort.Parity.valueOf(comand.nextLine()));

                        comand = new Scanner(System.in);
                        System.out.print("Стоп биты: ");
                        serialParameters.setStopBits(comand.nextInt());
                    }
                    System.out.println("Параметры записаны" + "\n");
                    return run();

                } else if (parameters.equals("a")){
                    String[] devList = SerialPortList.getPortNames();

                    for (String d : devList){
                        System.out.print(d + "\n");
                    }
                    comand = new Scanner(System.in);

                    serialParameters = new SerialParameters();

                    comand = new Scanner(System.in);
                    System.out.print("Введите номер порта: " );
                    serialParameters.setDevice(comand.nextLine());
                    serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_19200);
                    serialParameters.setDataBits(8);
                    serialParameters.setParity(SerialPort.Parity.NONE);
                    serialParameters.setStopBits(1);

                    System.out.println("Параметры автоматически записаны" + "\n");
                    return run();
                } else {
                    System.out.println("Неправильно выбран параметр. Попробуйте снова" + "\n");
                    return run();
                }
            case 3:
                System.out.println("Прощайте");
                System.exit(0);
            default:
                System.out.println("Неправильно выбрана команда. Попробуйте снова");
                return run();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("Usage:\r\n" +
                    "java Primary.Main start");
            return;
        }
        if (args[0].equals("start")){
            run();
        } else {
            System.out.println("Неправильно запущено программа. Попробуйте снова");
        }
    }
}
