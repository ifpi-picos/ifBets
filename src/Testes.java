import java.io.IOException;

public class Testes {
    public static void run() throws IOException{
        //Testando metodos para ler o database
        Database.getUsuarios();

        System.out.println(Database.getCliente("11122233300"));
    }
}
