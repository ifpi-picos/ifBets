import java.io.IOException;

public class Testes {
    public static void run() throws IOException{
        //Testando metodos para ler o database
        String[] usuarios = Database.getUsuarios();

        for (String usuario : usuarios) {
            System.out.println(usuario);
        }
    }
}
