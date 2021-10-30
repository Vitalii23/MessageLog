package Primary;

import Connection.Master;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

import static com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.BAUD_RATE_19200;
import static com.intelligt.modbus.jlibmodbus.serial.SerialPort.Parity;

public class Main {
    private static Scanner command;
    private static int number;
    private static String parameters;
    private static SerialParameters serialParameters;

    private static int run(){
        System.out.println("Добро пожаловать. Пожалуйста, введите команду\r\n" +
                "Запуск программы <1>\n" +
                "Параметры порта <2>\n" +
                "Выход программы <3>");
        command = new Scanner(System.in);
        number = command.nextInt();
        switch (number){
            case 1:
                Master master = new Master();

                master.settingModbus(serialParameters.getDevice(),
                             serialParameters.getBaudRate(),
                             serialParameters.getDataBits(),
                             serialParameters.getStopBits(),
                             serialParameters.getParity());
                return run();
            case 2:
                System.out.print("Выберите параметры подключения. (m или a): ");
                command = new Scanner(System.in);
                parameters = command.nextLine();
                if (parameters.equals("m")){
                    SerialPort comPort = SerialPort.getCommPorts()[0];
                    comPort.openPort();

                    System.out.println(System.getProperty("os.name"));
                    System.out.println("Name: " + comPort.getDescriptivePortName());
                    System.out.println("ComPort: " + comPort.getSystemPortName());

                    serialParameters = new SerialParameters();

                    command = new Scanner(System.in);
                    System.out.print("Введите номер порта: " );
                    serialParameters.setDevice(command.nextLine());

                    command = new Scanner(System.in);
                    System.out.print("Бит в секунду: ");
                    serialParameters.setBaudRate(
                            com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.valueOf(command.nextLine()));

                    command = new Scanner(System.in);
                    System.out.print("Биты данных: ");
                    serialParameters.setDataBits(command.nextInt());

                    command = new Scanner(System.in);
                    System.out.print("Четность: ");
                    serialParameters.setParity(Parity.valueOf(command.nextLine()));

                    command = new Scanner(System.in);
                    System.out.print("Стоп биты: ");
                    serialParameters.setStopBits(command.nextInt());

                    System.out.println("Параметры записаны" + "\n");
                    comPort.closePort();
                    return run();

                } else if (parameters.equals("a")){
                    SerialPort comPort = SerialPort.getCommPorts()[0];
                    comPort.openPort();

                    System.out.println(System.getProperty("os.name"));
                    System.out.println("Name: " + comPort.getDescriptivePortName());
                    System.out.println("ComPort: " + comPort.getSystemPortName());

                    serialParameters = new SerialParameters();

                    if (comPort.getDescriptivePortName().equals("USB-to-Serial Port (pl2303)") ||
                            comPort.getDescriptivePortName().equals("Prolific USB-to-Serial Comm Port " + "("
                                    + comPort.getSystemPortName() + ")")){
                        if (System.getProperty("os.name").equals("Linux")){
                            serialParameters.setDevice("/dev/" + comPort.getSystemPortName());
                        } else {
                            serialParameters.setDevice(comPort.getSystemPortName());
                        }
                    }

                    serialParameters.setBaudRate(BAUD_RATE_19200);
                    serialParameters.setDataBits(8);
                    serialParameters.setParity(Parity.NONE);
                    serialParameters.setStopBits(1);

                    System.out.println("Параметры автоматически записаны" + "\n");
                    comPort.closePort();
                    return run();
                } else if (parameters.equals("back")){
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
