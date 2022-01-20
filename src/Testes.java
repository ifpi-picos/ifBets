import java.time.LocalDate;

public class Testes {
    public static void run() throws Exception {
        Database.carregarClientes();

        // Database.addCliente(new Cliente("22233344400", "Teste teste", "teste@hotmail.com", LocalDate.of(2000, 01, 01), new Endereco("apartamento", "picos", "centro", "rua", 0, "pi")));
        // Database.addCliente(new Cliente("22233344401", "Teste teste", "teste@hotmail.com", LocalDate.of(2000, 01, 01), new Endereco("apartamento", "picos", "centro", "rua", 0, "pi")));
        // Database.addCliente(new Cliente("22233344402", "Teste teste", "teste@hotmail.com", LocalDate.of(2000, 01, 01), new Endereco("apartamento", "picos", "centro", "rua", 0, "pi")));

        Cliente cliente = Database.getClienteByCPF("22233344400");

        
        Database.delCliente(cliente);
    }
}
