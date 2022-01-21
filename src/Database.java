import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Database {
    private static Path localClientes = Path.of("resources/clientes.ifbets");
    private static Path localJogos = Path.of("resources/jogos.ifbets");
    private static List<Cliente> clientes = new ArrayList<Cliente>(0);
    private static List<Jogo> jogos = new ArrayList<Jogo>(0);

    // Cliente
    public static void carregarClientes() throws Exception {
        String stringClientes = Files.readString(localClientes);

        String[] stringCliente = stringClientes.split("\n");

        for (String cliente : stringCliente) {
            String[] infoCliente = cliente.split(",");

            for (int i = 0; i < infoCliente.length; i++) {
                while (infoCliente[i].startsWith(" ")) {
                    infoCliente[i] = infoCliente[i].substring(1);
                }
            }

            clientes.add(new Cliente(infoCliente[0], infoCliente[1], infoCliente[2],
                    LocalDate.parse(infoCliente[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    new Endereco(infoCliente[4], infoCliente[5], infoCliente[6], infoCliente[7],
                            Integer.parseInt(infoCliente[8]), infoCliente[9])));
        }

    }

    public static void salvarClientes() throws Exception {
        String stringClientes = "";

        for (int i = 0; i < clientes.size(); ++i) {
            stringClientes += clientes.get(i).toString();

            if (i < clientes.size()) {
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

    public static void editCliente(Cliente cliente) throws Exception {
        int index = clientes.indexOf(cliente);
        clientes.set(index, cliente);
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

    // Jogo
    public static void carregarJogos() throws Exception {
        String stringJogos = Files.readString(localJogos);

        String[] stringJogo = stringJogos.split("\n");

        for (String jogo : stringJogo) {
            if (jogo.equals("")) {
                continue;
            }
            String[] infoJogo = jogo.split(",");

            for (int i = 0; i < infoJogo.length; i++) {
                while (infoJogo[i].startsWith(" ")) {
                    infoJogo[i] = infoJogo[i].substring(1);
                }
            }

            jogos.add(new Jogo(infoJogo[0], infoJogo[1], LocalDateTime.parse(infoJogo[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), Double.parseDouble(infoJogo[3]), Double.parseDouble(infoJogo[4]), Double.parseDouble(infoJogo[5]), Integer.parseInt(infoJogo[6]), Integer.parseInt(infoJogo[7]), Integer.parseInt(infoJogo[8]), Double.parseDouble(infoJogo[9])));
        }

    }

    public static void salvarJogos() throws Exception {
        String stringJogos = "";

        for (int i = 0; i < jogos.size(); ++i) {
            stringJogos += jogos.get(i).toString();

            if (i < jogos.size()) {
                stringJogos += "\n";
            }
        }

        Files.writeString(localJogos, stringJogos);
    }

    public static void addJogo(Jogo jogo) throws Exception {
        jogos.add(jogo);
        salvarJogos();
    }

    public static void delJogo(Jogo jogo) throws Exception {
        jogos.remove(jogo);
        salvarJogos();
    }

    public static void editJogo(Jogo jogo) throws Exception {
        int index = jogos.indexOf(jogo);
        jogos.set(index, jogo);
        salvarJogos();
    }

    public static Jogo getJogoByNome(String nomeTimeA, String nomeTimeB) {
        for (Jogo jogo : jogos) {
            if (jogo.getNomeTimeA().equalsIgnoreCase(nomeTimeA) && jogo.getNomeTimeB().equalsIgnoreCase(nomeTimeB)) {
                return jogo;
            }
        }
        return null;
    }
}