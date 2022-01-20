import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Database {
    private static Path localClientes = Path.of("resources/clientes.ifbets");
    private static String localJogos = "resources/jogos.ifbets";
    private static List<Cliente> clientes = new ArrayList<Cliente>(0);

    public static void carregarClientes() throws Exception {
        String stringClientes = Files.readString(localClientes);

        String[] stringCliente = stringClientes.split("\n");

        for (String cliente : stringCliente){
            String[] infoCliente = cliente.split(",");

            for (int i = 0; i < infoCliente.length; i++){
                while (infoCliente[i].startsWith(" ")){
                    infoCliente[i] = infoCliente[i].substring(1);
                }	
            }
            
            clientes.add(new Cliente(infoCliente[0], infoCliente[1], infoCliente[2], LocalDate.parse(infoCliente[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")), new Endereco(infoCliente[4], infoCliente[5], infoCliente[6], infoCliente[7], Integer.parseInt(infoCliente[8]), infoCliente[9])));
        }

    }

    public static void salvarClientes() throws Exception {
        String stringClientes = "";

        for (int i = 0; i < clientes.size(); ++i) {
            stringClientes += clientes.get(i).toString();

            if(i < clientes.size()){
                stringClientes += "\n";
            }
        }

        Files.writeString(localClientes, stringClientes);  
    }

    public static void addCliente(Cliente cliente) throws Exception {
        clientes.add(cliente);
        salvarClientes();
    }

    public static void delCliente(Cliente cliente) throws Exception {
        clientes.remove(cliente);
        salvarClientes();
    }

    public static Cliente getClienteByCPF(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }

        return null;
    }



}