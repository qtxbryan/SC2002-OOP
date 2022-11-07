package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    
    /**
     * 
     * Serialize object to filename.dat file
     * @param object to be serialize
     * @param filename to save object as 
     * 
     */
    public static void serializeObject (Object object, String fileName) {
        
        try {
            
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(object);
            out.close();
            fileOut.close();
            
        }catch (IOException e) {
            
            e.printStackTrace();
            
            
        }
        
    }
     
     /**
      * 
      * Deserialize object from filename.dat
      * @param fileName of object to be deserailize 
      * @return deseralized object 
      * 
      */
    public static Object deserializeObject(String fileName){
        
        try {
            
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            
            return object;
            
        }catch (FileNotFoundException e) {
            
            System.out.println("File not found ");
            e.printStackTrace();
            return null; 
            
        }catch (IOException i)
        {
            System.out.println("IOException caught!");
            i.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found!");
            c.printStackTrace();
            return null;
        }
    }
    
}
