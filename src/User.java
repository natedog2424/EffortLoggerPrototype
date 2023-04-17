import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable{
    public String fullName;
    public String password;

    //  overloaded constructor
    public User(String fullName, String password){
        this.fullName = fullName;
        this.password = password;
    }

    // serialize method to convert object to byte array
    public byte[] serialize() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    // deserialize method to convert byte array to object
    public static User deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        User user = null;
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            user = (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}
