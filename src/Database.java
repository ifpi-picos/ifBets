import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static String localClientes = "resources/clientes.txt";
    private static Map<String, ArrayList> clientes = new HashMap<>();

    public static void getUsuarios() throws IOException {
        Path path = Path.of(localClientes);
        String s = Files.readString(path);

        String[] usuarios = s.split("\\|");

        for (String usuario : usuarios) {
            usuario = usuario.replace("{", "");
            usuario = usuario.replace("}", "");
            String[] infoUsuario = usuario.split(",");

            ArrayList<String> valores = new ArrayList();
            ArrayList<String> endereco = new ArrayList();

            for (int i = 0; i < 3; i++) {
                String[] info = infoUsuario[i].split(":");
                valores.add(info[1]);
            }

            for (int i = 3; i < infoUsuario.length; i++) {
                String[] info = infoUsuario[i].split(":");
                endereco.add(info[1]);
            }

            String key = valores.get(0).replace(" ", "");

            valores.remove(0);
            ArrayList<ArrayList> data = new ArrayList<>();

            data.add(valores);
            data.add(endereco);

            clientes.put(key, data);
        }
    }

    public static ArrayList getCliente(String cpf) {
        return clientes.get(cpf);
    }
}
