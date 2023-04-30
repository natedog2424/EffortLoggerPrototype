import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ProjectSerializer implements Serializable {
    public static byte[] serialize(Project project) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(project);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static Project deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Project project = null;
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            project = (Project) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return project;
    }
}