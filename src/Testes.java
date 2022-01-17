import java.util.Map;

public class Testes {
    public static void run() throws Exception {
        Database.carregarClientes();
        
        //Getters Cliente
        System.out.println(Database.getNomeCliente("11122233300"));
        System.out.println(Database.getDataNascimentoCliente("11122233300"));
        Map<String, String> endereco = Database.getEnderecoCliente("11122233300");

        System.out.println(endereco.get("logradouro"));
        System.out.println(endereco.get("cidade"));
        System.out.println(endereco.get("bairro"));
        System.out.println(endereco.get("rua"));
        System.out.println(endereco.get("numero"));
        System.out.println(endereco.get("uf"));

        //Setters Cliente
        Database.setNomeCliente("11122233300", "Alan Leal");
        Database.setDataNascimentoCliente("11122233300", "00/00/0000");
        //Falta a classe Endereco para setar o endereco

        //Falta a classe Cliente para adiconar um cliente
        //Database.delCliente("11122233300");
    }
}
