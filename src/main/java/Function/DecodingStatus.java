package Function;

import Model.MetaData;

import java.util.ArrayList;
import java.util.List;

public class DecodingStatus {

    private String n, w, t;
    private char[] arrayText, array;
    private final List<String> binaryText = new ArrayList<String>();
    private final ArrayList<MetaData> metaDataList = new ArrayList<MetaData>();
    private final MetaData metaData = new MetaData();

    private String binaryNumber(String number){
        String binary = null;
        switch (number){
            case "0":
                binary = "0000";
                return binary;
            case "1":
                binary = "0001";
                return binary;
            case "2":
                binary = "0010";
                return binary;
            case "3":
                binary = "0011";
                return binary;
            case "4":
                binary = "0100";
                return binary;
            case "5":
                binary = "0101";
                return binary;
            case "6":
                binary = "0110";
                return binary;
            case "7":
                binary = "0111";
                return binary;
            case "8":
                binary = "1000";
                return binary;
            case "9":
                binary = "1001";
                return binary;
            case "a":
                binary = "1010";
                return binary;
            case "b":
                binary = "1011";
                return binary;
            case "c":
                binary = "1100";
                return binary;
            case "d":
                binary = "1101";
                return binary;
            case "e":
                binary = "1110";
                return binary;
            case "f":
                binary = "1111";
                return binary;
            default:
                System.out.println("Ошибка конвертирования");
        }
        return binary;
    }

    //2#
    private void appointment(int one, int two, int three, int four, int five){
        switch (one){
            case 0:
                metaData.setEd("Остановлен в промежуточном положении");
                break;
            case 1:
                metaData.setEd("Остановлен в положении «Открыто»");
                break;
            case 2:
                metaData.setEd("Остановлен в положении «Закрыто»");
                break;
            case 3:
                metaData.setEd("Движется в направлении «Открыто»(окрывается)");
                break;
            case 4:
                metaData.setEd("Движется в направлении «Закрыто»(закрывается)");
                break;
            default:
                System.out.println("Ошибка функции appointment (int one)");
                metaData.setEd("????");
                break;
        }

        switch (two){
            case 0:
                metaData.setCalibration("Блок полностью не калиброван");
                break;
            case 1:
                metaData.setCalibration("Задано только положении «Открыто»");
                break;
            case 2:
                metaData.setCalibration("Задано только положении «Закрыто»");
                break;
            case 3:
                metaData.setCalibration("Полная калибровка");
                break;
            default:
                System.out.println("Ошибка функции appointment (int two)");
                metaData.setCalibration("????");
                break;
        }

        switch (three){
            case 0:
                metaData.setSourceCommand("Не задан");
                break;
            case 1:
                metaData.setSourceCommand("МПУ");
                break;
            case 2:
                metaData.setSourceCommand("Цифровые входы");
                break;
            case 3:
                metaData.setSourceCommand("RS-485");
                break;
            case 4:
                metaData.setSourceCommand("4..20 мА");
                break;
            case 5:
                metaData.setSourceCommand("Резерв");
                break;
            case 6:
                metaData.setSourceCommand("Экстренный сигнал на цифровом входе «Вход 4»");
                break;
            case 7:
                metaData.setSourceCommand("Тактовый режим");
                break;
            case 8:
                metaData.setSourceCommand("ТЧХ");
                break;
            default:
                System.out.println("Ошибка функции appointment (int three)");
                metaData.setSourceCommand("????");
                break;
        }

        switch (four){
            case 0:
                metaData.setLastCommand("Нет команды");
                break;
            case 1:
                metaData.setLastCommand("Стоп");
                break;
            case 2:
                metaData.setLastCommand("Открыть");
                break;
            case 3:
                metaData.setLastCommand("Закрыть");
                break;
            case 4:
                metaData.setLastCommand("Перейти в заданное положение");
                break;
            default:
                System.out.println("Ошибка функции appointment (int four)");
                metaData.setLastCommand("????");
                break;
        }

        switch (five){
            case 0:
                metaData.setStopping("Не установлена");
                break;
            case 1:
                metaData.setStopping("Команда «Стоп» от текущего источника команд");
                break;
            case 2:
                metaData.setStopping("Достигнуто конечное положение «Окрыто»");
                break;
            case 3:
                metaData.setStopping("Достигнуто конечное положение «Закрыто»");
                break;
            case 4:
                metaData.setStopping("Достигнуто заданное положение");
                break;
            case 5:
                metaData.setStopping("Авария блока или ЭП");
                break;
            case 6:
                metaData.setStopping("Сработала момментная муфта(Превышение момента)");
                break;
            default:
                System.out.println("Ошибка функции appointment (int five)");
                metaData.setStopping("????");
                break;
        }
    }

    public void arrayWriteStatus(String status){
        decryption(status);
    }

    public MetaData getMetaData(){
        return new MetaData(
                        metaData.getEd(),
                        metaData.getCalibration(),
                        metaData.getSourceCommand(),
                        metaData.getLastCommand(),
                        metaData.getStopping());
    }

    //1#
    public void decryption(String text){
        array = text.toCharArray();

        for (int i = 0; i <= 3; i++){
            n = String.valueOf(array[i]);
            w = binaryNumber(n);
            binaryText.add(i, w);
        }

        t = binaryText.get(0) + binaryText.get(1) + binaryText.get(2) + binaryText.get(3);
        arrayText = t.toCharArray();

        int five = Integer.parseInt(String.valueOf(arrayText[0]) +
                String.valueOf(arrayText[1]) +
                String.valueOf(arrayText[2]) +
                String.valueOf(arrayText[3]), 2);
        int four = Integer.parseInt(String.valueOf(arrayText[4]) +
                String.valueOf(arrayText[5]) +
                String.valueOf(arrayText[6]), 2);
        int three = Integer.parseInt(String.valueOf(arrayText[7]) +
                String.valueOf(arrayText[8]) +
                String.valueOf(arrayText[9]) +
                String.valueOf(arrayText[10]), 2);
        int two = Integer.parseInt(String.valueOf(arrayText[11]) +
                String.valueOf(arrayText[12]), 2);
        int one = Integer.parseInt(String.valueOf(arrayText[13]) +
                String.valueOf(arrayText[14]) +
                String.valueOf(arrayText[15]), 2);

        appointment(one, two, three, four, five);
    }
}
