package aruna.archFX;


import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        runapp();


    }
    static void runapp()
    {


        System.out.println("_____________________________");
        System.out.println("|       Product Cipher      |");
        System.out.println("|   Press E for encryption  |");
        System.out.println("|   Press D for encryption  |");
        System.out.println("|___________________________|");
        System.out.print("| Enter E/D ==> ");
        Scanner indx=new Scanner(System.in);
        String index=indx.next();
        System.out.println("|___________________________|");

        try
        {
            if(index.equals("E")||index.equals("e"))
            {
                encrypt("text.txt", getKey());

                System.out.println("|___________________________|");
                System.out.println("|   Encryption successful   |");
                System.out.println("|___________________________|");

            }
            else if(index.equals("D")||index.equals("d"))
            {
                decrypt("encrypted.txt", getKey());
                System.out.println("|___________________________|");
                System.out.println("|   Decryption successful   |");
                System.out.println("|___________________________|");
            }
            else
                {
                    runapp();
                }

        }
        catch (Exception e)
        {

            System.out.println("|  Invalid Encryption Key   |");
            System.out.println("| ** Key must be at least 8 |");
            System.out.println("|   characters long **      |");
            System.out.println("|___________________________|");
        }
        indx.close();
    }

    static String getKey()
    {



        System.out.println("|      Enter your Key :     |");
        System.out.print("|   Your Key ==> ");
        Scanner sc=new Scanner(System.in);
        String key=sc.next();

        sc.close();
        return key;

    }

    static boolean saveFile(String messege, String name)
    {
        // The name of the file to open.
        String fileName = name;

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                    new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            bufferedWriter.write(messege);
            bufferedWriter.close();
            return true;
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
            return false;
        }

    }

    static String readFile(String file_name)
    {
        // The name of the file to open.
        String fileName = file_name;

        // This will reference one line at a time
        String line = null;
        String messege="";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                messege=messege+line;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return messege;
    }

    static String generateKey(String key)
    {
        int sum=0;
        for (int index = 0; index < key.length(); index++) {
            int tempnum = (int)key.charAt(index);
            sum+=tempnum*(Math.pow(2,index+key.length()));
        }

        return Integer.toString(sum%100000000);
    }

    static String substitution(String messege, String generatedKey)
    {
        String ciphertxt="";
        for (int index = 0; index < messege.length(); index++) {
            int tempnum = (int)generatedKey.charAt(index%8)-48;
            //System.out.println(tempnum);
            if(((int)messege.charAt(index))>126 || ((int)messege.charAt(index))<32)
            {
                ciphertxt += Character.toString((char) ((((int) messege.charAt(index)))));
            }
            else if(((int)messege.charAt(index)+tempnum)>126)
            {
                ciphertxt += Character.toString((char) ((((int) messege.charAt(index) + tempnum-94))));
            }
            else
                {
                ciphertxt += Character.toString((char) ((((int) messege.charAt(index) + tempnum))));
            }
        }


        return ciphertxt;
    }

    static String invertSubstitution(String messege, String generatedKey)
    {
        String ciphertxt="";
        for (int index = 0; index < messege.length(); index++) {
            int tempnum = (int)generatedKey.charAt(index%8)-48;
            //System.out.println(tempnum);
            if(((int)messege.charAt(index))>126 || ((int)messege.charAt(index))<32)
            {
                ciphertxt += Character.toString((char) ((((int) messege.charAt(index)))));
            }
            else if(((int)messege.charAt(index)-tempnum)<32)
            {
                ciphertxt += Character.toString((char) ((((int) messege.charAt(index) - tempnum + 94))));
            }
            else
            {
                ciphertxt += Character.toString((char) ((((int) messege.charAt(index) - tempnum))));
            }
        }


        return ciphertxt;
    }

    static int[] permutaionKey(String generatedKey)
    {
        int [] permute=new int[8];
        int [] temperory=new int[8];
        int [] returnarray=new int[8];
        for (int index = 0; index < generatedKey.length(); index++) {
            int tempnum = (int)generatedKey.charAt(index%8)-48;
            temperory[index]=tempnum;
            permute[index]=tempnum;

        }
        Arrays.sort(temperory);
        for (int i = 0; i < temperory.length; i++)
        {
            //System.out.println(i + ": " + temperory[i]);
            for (int y = 0; y < temperory.length; y++)
            {
                if(permute[y]==temperory[i])
                {
                    returnarray[y]=i;
                    permute[y]= 100;
                    break;
                }
            }
        }
        //for (int i = 0; i < returnarray.length; i++)
        //    System.out.println(i + ": " + returnarray[i]);
        return returnarray;
    }

    static String permutaion(String messege, int[] permutaionKey)
    {

        String ciphertxt="";
        //messege = messege.substring(0, messege.length() - 1);
        //System.out.println(messege.length());
        int msglngth=messege.length();

        if(msglngth%8!=0)
        {
            for(int i=0;i<(8-msglngth%8);i++)
            {
                messege+=" ";
            }
        }
        //System.out.println(messege.length());
        for (int index = 0; index < messege.length()/8; index++) {
            String temp="";
            for (int indexy = 0; indexy < permutaionKey.length; indexy++)
            {
                temp += messege.charAt(permutaionKey[indexy] + index *8);


            }
            //System.out.println(tempnum);

            ciphertxt+=temp;

            //System.out.println(ciphertxt);
        }

       // System.out.println(ciphertxt);
        return ciphertxt;
    }


    static String invertPermutaion(String messege, int[] permutaionKey)
    {

        String ciphertxt="";
       // messege = messege.substring(0, messege.length() - 1);
        //System.out.println(messege.length());
        int msglngth=messege.length();

        if(msglngth%8!=0)
        {
            for(int i=0;i<(8-msglngth%8);i++)
            {
                messege+=" ";
            }
        }

        for (int index = 0; index < messege.length()/8; index++) {
            String[] temp=new String[8];
            for (int indexy = 0; indexy < permutaionKey.length; indexy++)
            {
                temp[permutaionKey[indexy]] = Character.toString(messege.charAt(indexy + index *8));


            }
            String temp2 = String.join("", temp);
            //System.out.println(temp2);

            ciphertxt+=temp2;

            //System.out.println(ciphertxt);
        }


        return ciphertxt;
    }

    static void encrypt(String File, String key)
    {
        saveFile(permutaion(substitution(permutaion(substitution(permutaion(readFile(File),permutaionKey(generateKey(key))),key),permutaionKey(generateKey(key))),key),permutaionKey(generateKey(key))),"encrypted.txt");
        //saveFile(substitution(readFile(File),key),"encrypted.txt");

    }

    static void decrypt(String File, String key)
    {
        saveFile(invertPermutaion(invertSubstitution(invertPermutaion(invertSubstitution(invertPermutaion(readFile(File),permutaionKey(generateKey(key))),key),permutaionKey(generateKey(key))),key),permutaionKey(generateKey(key))),"decrypted.txt");
        //saveFile(invertSubstitution(readFile(File),key),"decrypted.txt");

    }
}
