import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class L1 {

    public static void sysoTable(String[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String[][] myArray(String text, String key1, String key2) {
        String[] arrtext = text.split("");
        String[][] arr = new String[key2.length() + 1][key1.length() + 1];
        arr[0][0] = " ";
        int l = 0;
        for (int j = 0; j < key1.length(); j++) {
            try {
                arr[0][j + 1] = key1.substring(j, j + 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        for (int i = 0; i < key2.length(); i++) {
            try {
                arr[i + 1][0] = key2.substring(i, i + 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        for (int i = 1; i < key2.length() + 1; i++) {
            for (int j = 1; j < key1.length() + 1; j++) {
                try {
                    arr[i][j] = arrtext[l++];
                } catch (ArrayIndexOutOfBoundsException e) {
                    arr[i][j]=" ";
                }
            }
        }
        //sysoTable(arr);
        return arr;
    }

    static StringBuffer incrypt(String text, String key1, String key2) {
        String newT = text.replaceAll(" ", "");
        String table[][] = myArray(newT, key1, key2);
        System.out.println("Start data:");
        sysoTable(table);
        StringBuffer res = new StringBuffer("");
        String[] temp = new String[table.length];
        for (int i = 1; i < table[0].length; i++) {
            for (int j = 0; j < table[0].length - i; j++) {
                if (table[0][j].compareTo(table[0][j + 1]) > 0) {
                    for (int l = 0; l < table.length; l++) {
                        temp[l] = table[l][j];
                    }
                    for (int l = 0; l < table.length; l++) {
                        table[l][j] = table[l][j + 1];
                    }
                    for (int l = 0; l < table.length; l++) {
                        table[l][j + 1] = temp[l];
                    }
                }
            }
        }
        temp = new String[table[0].length];
        for (int i = 1; i < table.length; i++) {
            for (int j = 0; j < table.length - i; j++) {
                if (table[j][0].compareTo(table[j + 1][0]) > 0) {
                    for (int l = 0; l < table[0].length; l++) {
                        temp[l] = table[j][l];
                    }
                    for (int l = 0; l < table[0].length; l++) {
                        table[j][l] = table[j + 1][l];
                    }
                    for (int l = 0; l < table[0].length; l++) {
                        table[j + 1][l] = temp[l];
                    }
                }
            }
        }

//        sysoTable(table);

        for (int i = 1; i < table[0].length; i++) {
            for (int j = 1; j < table.length; j++) {
                if (table[j][i] != null) {
                    res.append(table[j][i] + "");
                } else {
                    res.append(" ");
                }
            }
        }

        return res;
    }

    static StringBuffer decrypt(String test, String key1, String key2) {
        StringBuffer res = new StringBuffer("");

        String[] arChKey = key1.split("");
        Arrays.sort(arChKey);
        String chley1 = "";
        for (int i = 0; i < key1.length(); i++) {
            chley1 += arChKey[i];
        }

        arChKey = key2.split("");
        Arrays.sort(arChKey);
        String chley2 = "";
        for (int i = 0; i < key2.length(); i++) {
            chley2 += arChKey[i];
        }

        String table[][] = myArray(test, chley2, chley1);
//        sysoTable(table);

        for (int j = 1; j < chley2.length() + 1; j++) {
            int col = key2.indexOf(table[0][j]) + 1;
            if (col == j) {
//                System.out.println("skipped col " + col);
                continue;
            }
//            System.out.println("col for " + table[0][j] + "=" + col);

            String[] temp = new String[chley1.length()];
            for (int l = 0; l < chley1.length(); l++) {
                temp[l] = table[l + 1][col];
            }
            for (int l = 0; l < chley1.length(); l++) {
                table[l + 1][col] = table[l + 1][j];
                table[l + 1][j] = temp[l];
            }

            String t = table[0][j];
            table[0][j] = table[0][col];
            table[0][col] = t;
            chley2 = "";
            for (int l = 1; l < table[0].length; l++) {
                chley2 += table[l][0];
            }
//            System.out.println("switched " + j + " and " + col);
//            sysoTable(table);
        }
//        System.out.println("=============");
        for (int j = 1; j < chley1.length() + 1; j++) {
            int row = chley1.indexOf(key1.substring(j - 1, j)) + 1;
            if (row == j) {
//                System.out.println("skipped row " + row);
                continue;
            }
//            System.out.println("row for " + key1.substring(j - 1, j) + "=" + row);
            String[] temp = new String[chley2.length()];
            for (int l = 0; l < chley2.length(); l++) {
                temp[l] = table[j][l + 1];
            }
            for (int l = 0; l < chley2.length(); l++) {
                table[j][l + 1] = table[row][l + 1];
                table[row][l + 1] = temp[l];
            }

            String t = table[j][0];
            table[j][0] = table[row][0];
            table[row][0] = t;
            chley1 = "";
            for (int l = 1; l < table.length; l++) {
                chley1 += table[l][0];
            }

//            System.out.println("switched " + j + " and " + row);
//            sysoTable(table);
        }

        //System.out.println("-------------");
        //sysoTable(table);

        for (int i = 1; i < table[0].length; i++) {
            for (int j = 1; j < table.length; j++) {
                res.append(table[j][i]);
            }
        }

        return res;
    }

    public static void main(String args[]) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("input1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer text = new StringBuffer(sc.nextLine());
        StringBuffer key1 = new StringBuffer(sc.nextLine());
        StringBuffer key2 = new StringBuffer(sc.nextLine());

        String incrypt = incrypt(text.toString(), key1.toString(), key2.toString()).toString();
        System.out.println("\nincrypted: " + incrypt);

        System.out.println("decrypted: " + decrypt(incrypt, key1.toString(), key2.toString()));
    }
}
