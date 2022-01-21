import java.time.LocalDate;
import java.time.LocalDateTime;

public class Testes {
    public static void run() throws Exception {
        Database.carregarClientes();
        Database.carregarJogos();

        Database.addCliente(new Cliente("22233344400", "Teste teste", "teste@hotmail.com", LocalDate.of(2000, 01, 01), new Endereco("apartamento", "picos", "centro", "rua", 0, "pi")));
        Cliente cliente = Database.getClienteByCPF("22233344400");
        System.out.println(cliente.getNome());
        cliente.setNome("Alan");
        Database.editCliente(cliente);
        //Database.delCliente(cliente);

        Database.addJogo(new Jogo("testeA", "testeB", LocalDateTime.of(2022, 01, 21, 15, 0, 0), 0.0, 0.0, 0.0, 0, 0, 0, 0));
        Jogo jogo = Database.getJogoByNome("testeA", "testeB");
        System.out.println(jogo.getNomeTimeB());
        jogo.setNomeTimeB("B");
        Database.editJogo(jogo);
        //Database.delJogo(jogo);
    }
}
