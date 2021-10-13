import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class L2 {

    static String[] createTable(String key) {
        String[] arr = new String[(int) Math.round(key.length() / 6.0)];
        for (int i = 0; i < arr.length; i++) {
            try {
                arr[i] = key.substring(i + (i * 6) - i, i + (i * 6) + 6 - i);
//                System.out.print(arr[i] + "\n");
            } catch (StringIndexOutOfBoundsException e) {
                arr[i] = key.substring(i + (i * 6) - i, key.length());
//                System.out.print(arr[i] + "\n");
            }
        }
        return arr;
    }

    static String incrypt(String text, String key) {
        StringBuffer res = new StringBuffer("");
        String [] table = createTable(key);

        for(int i = 0; i <text.length(); i++){
            int l;
            if (text.substring(i, i+1).equals(" ")){
                res.append(" ");
                continue;
            }
            for (int j = 0; j<table.length; j++){
                if ((l = table[j].indexOf(text.substring(i, i+1)))>=0){
                    try {
                        res.append(table[j+1].substring(l, l+1));
                    }catch (ArrayIndexOutOfBoundsException e) {
                        res.append(table[0].substring(l, l+1));
                    }
                    break;
                }
            }
        }
        return res.toString();
    }

    static String decrypt(String text, String key){
        StringBuffer res = new StringBuffer("");
        String [] table = createTable(key);

        for(int i = 0; i <text.length(); i++){
            int l;
            if (text.substring(i, i+1).equals(" ")){
                res.append(" ");
                continue;
            }
            for (int j = 0; j<table.length; j++){
                if ((l = table[j].indexOf(text.substring(i, i+1)))>=0){
                    try {
                        res.append(table[j-1].substring(l, l+1));
                    }catch (ArrayIndexOutOfBoundsException e) {
                        res.append(table[table.length-1].substring(l, l+1));
                    }
                    break;
                }
            }
        }

        return res.toString();
    }

    public static void main(String[] args) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = sc.nextLine();
        String key = sc.nextLine();
        System.out.println("start text: "+text);
        String incrypt = incrypt(text, key);
        System.out.println("icrypted text: "+incrypt);
        System.out.println("decrypted text: "+decrypt(incrypt, key));

    }
}
