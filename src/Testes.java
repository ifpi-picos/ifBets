import java.time.LocalDateTime;
import java.util.Map;

public class Testes {
    public static void run() throws Exception {
        Database.carregarClientes();
        Database.carregarJogos();

        Boolean testarCliente = true;
        Boolean testarJogo = true;

        if (testarCliente) {
            // Getters Cliente
            System.out.println(Database.getNomeCliente("11122233300"));
            System.out.println(Database.getDataNascimentoCliente("11122233300"));
            Map<String, String> endereco = Database.getEnderecoCliente("11122233300");

            System.out.println(endereco.get("logradouro"));
            System.out.println(endereco.get("cidade"));
            System.out.println(endereco.get("bairro"));
            System.out.println(endereco.get("rua"));
            System.out.println(endereco.get("numero"));
            System.out.println(endereco.get("uf"));

            // Setters Cliente
            Database.setNomeCliente("11122233300", "Alan Leal");
            Database.setDataNascimentoCliente("11122233300", "00/00/0000");
            // Falta a classe Endereco para setar o endereco

            // Falta a classe Cliente para adiconar um cliente
            // Database.delCliente("11122233300");
        }

        if (testarJogo) {
            // Getters Jogo
            Map<String, String> jogo = Database.getJogo("timeA-timeB-Dia");

            System.out.println(jogo.get("nomeTimeA"));
            System.out.println(jogo.get("nomeTimeB"));
            System.out.println(jogo.get("data"));
            System.out.println(jogo.get("valorVitoriaA"));
            System.out.println(jogo.get("valorVitoriaB"));
            System.out.println(jogo.get("valorEmpate"));
            System.out.println(jogo.get("apostasA"));
            System.out.println(jogo.get("apostasB"));
            System.out.println(jogo.get("totalApostado"));

            // Setters Jogo
            Database.setNomeTimeAJogo("timeA-timeB-Dia", "timeA");
            Database.setNomeTimeBJogo("timeA-timeB-Dia", "timeB");
            Database.setDataJogo("timeA-timeB-Dia", "00/00/0000-00;00");
            Database.setValorVitoriaAJogo("timeA-timeB-Dia", "1.03");
            Database.setValorVitoriaBJogo("timeA-timeB-Dia", "1.30");
            Database.setValorEmpateJogo("timeA-timeB-Dia", "0.0");
            Database.setApostasAJogo("timeA-timeB-Dia", "0");
            Database.setApostasBJogo("timeA-timeB-Dia", "0");
            Database.setTotalApostadoJogo("timeA-timeB-Dia", "0.0");

            Jogo novoJogo = new Jogo("timeA", "timeB", LocalDateTime.now(), 1.33, 1.33, 1.33, 5, 5, 5, 0.0);

            Database.addJogo(novoJogo);
            Database.delJogo(novoJogo.getId());
            Database.salvarJogos();
        }
    }
}
