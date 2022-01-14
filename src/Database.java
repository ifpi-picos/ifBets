import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Database {
    private static String usuarios = "resources/usuarios.txt";

    public static String[] getUsuarios() throws IOException {
        Path path = Path.of(usuarios);
        String s = Files.readString(path);

        String[] parts = s.split("\\|");

        return parts;
    }

}


