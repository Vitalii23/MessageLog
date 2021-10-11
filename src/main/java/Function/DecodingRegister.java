package Function;

import Model.MetaData;

public class DecodingRegister {

    private MetaData data;

    public String decryption(int code){
            data = new MetaData();
        switch (code){
            case 1:
                data.setDecipher("А01");
                break;
            case 2:
                data.setDecipher("А02");
                break;
            case 3:
                data.setDecipher("A03");
                break;
            case 4:
                data.setDecipher("A04");
                break;
            case 5:
                data.setDecipher("A05");
                break;
            case 6:
                data.setDecipher("A06");
                break;
            case 7:
                data.setDecipher("A07");
                break;
            case 8:
                data.setDecipher("A08");
                break;
            case 9:
                data.setDecipher("A09");
                break;
            case 10:
                data.setDecipher("A10");
                break;
            case 11:
                data.setDecipher("A11");
                break;
            case 12:
                data.setDecipher("A12");
                break;
            case 13:
                data.setDecipher("A13");
                break;
            case 14:
                data.setDecipher("A14");
                break;
            case 15:
                data.setDecipher("A15");
                break;
            case 16:
                data.setDecipher("A16");
                break;
            case 17:
                data.setDecipher("A17");
                break;
            case 18:
                data.setDecipher("A18");
                break;
            case 19:
                data.setDecipher("A19");
                break;
            case 20:
                data.setDecipher("A20");
                break;
            case 21:
                data.setDecipher("A21");
                break;
            case 22:
                data.setDecipher("A22");
                break;
            case 23:
                data.setDecipher("A23");
                break;
            case 24:
                data.setDecipher("A24");
                break;
            case 25:
                data.setDecipher("A25");
                break;
            case 26:
                data.setDecipher("A26");
                break;
            case 27:
                data.setDecipher("A27");
                break;
            case 28:
                data.setDecipher("A28");
                break;
            case 29:
                data.setDecipher("A29");
                break;
            case 30:
                data.setDecipher("A30");
                break;
            case 31:
                data.setDecipher("A31");
                break;
            case 33:
                data.setDecipher("П33");
                break;
            case 34:
                data.setDecipher("П34");
                break;
            case 35:
                data.setDecipher("П35");
                break;
            case 36:
                data.setDecipher("П36");
                break;
            case 37:
                data.setDecipher("П37");
                break;
            case 38:
                data.setDecipher("П38");
                break;
            case 39:
                data.setDecipher("П39");
                break;
            case 40:
                data.setDecipher("П40");
                break;
            case 41:
                data.setDecipher("П41");
                break;
            case 42:
                data.setDecipher("П42");
                break;
            case 43:
                data.setDecipher("П43");
                break;
            case 44:
                data.setDecipher("П44");
                break;
            case 45:
                data.setDecipher("П45");
                break;
            case 64:
                data.setDecipher("Выкл");
                break;
            case 65:
                data.setDecipher("Вкл");
                break;
            case 66:
                data.setDecipher("Смена положения");
                break;
            case 65535:
                data.setDecipher("-1");
                break;
            default:
                System.out.println("Ошибка функции decryption");
                break;
        }
        return data.getDecipher();
    }
}
