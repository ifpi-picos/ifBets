import java.util.Map;

public class Testes {
    public static void run() throws Exception {
        Database.carregarClientes();

        System.out.println(Database.getNomeCliente("11122233300"));
        System.out.println(Database.getDataNascimentoCliente("11122233300"));
        Map<String, String> endereco = Database.getEnderecoCliente("11122233300");

        System.out.println(endereco.get("logradouro"));
        System.out.println(endereco.get("cidade"));
        System.out.println(endereco.get("bairro"));
        System.out.println(endereco.get("rua"));
        System.out.println(endereco.get("numero"));
        System.out.println(endereco.get("uf"));

        //Falta criar os settrs

        Database.salvarClientes();
    }
}
