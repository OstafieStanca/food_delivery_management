package dataLayer;

import java.io.*;

public class Serializator implements Serializable {


    public void serialize(Object object, String filename) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        //file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(object);
        out.close();
        file.close();

        System.out.println("Object has been serialized");
    }

    public Object deserialization(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);
        Object object1;
        // Method for deserialization of object
        object1 = in.readObject();
        in.close();
        file.close();
        return object1;
    }


}