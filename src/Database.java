import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Database {
    private static Path localClientes = Path.of("resources/database/clientes.ifbets");
    private static Path localJogos = Path.of("resources/database/jogos.ifbets");
    private static Path localApostas = Path.of("resources/database/apostas.ifbets");
    private static List<Cliente> clientes = new ArrayList<Cliente>(0);
    private static List<Jogo> jogos = new ArrayList<Jogo>(0);
    private static List<Aposta> apostas = new ArrayList<Aposta>(0);

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
        cpf = cpf.replace(".", "").replace("-", "");
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }

        return null;
    }

    public static List<Cliente> getClientes() {
        return clientes;
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

            jogos.add(new Jogo(infoJogo[0], infoJogo[1],
                    LocalDateTime.parse(infoJogo[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    Double.parseDouble(infoJogo[3]), Double.parseDouble(infoJogo[4]), Double.parseDouble(infoJogo[5]),
                    Integer.parseInt(infoJogo[6]), Integer.parseInt(infoJogo[7]), Integer.parseInt(infoJogo[8]),
                    Double.parseDouble(infoJogo[9]), Double.parseDouble(infoJogo[10])));
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
            if (jogo.getNomeTimeA().equals(nomeTimeA) && jogo.getNomeTimeB().equals(nomeTimeB)) {
                return jogo;
            }
        }
        return null;
    }

    public static List<Jogo> getJogos() {
        return jogos;
    }

    // Aposta
    public static void carregarApostas() throws Exception {
        String stringApostas = Files.readString(localApostas);

        String[] stringAposta = stringApostas.split("\n");

        for (String aposta : stringAposta) {
            if (aposta.equals("")) {
                continue;
            }
            String[] infoAposta = aposta.split(",");

            for (int i = 0; i < infoAposta.length; i++) {
                while (infoAposta[i].startsWith(" ")) {
                    infoAposta[i] = infoAposta[i].substring(1);
                }
            }

            String nomeTimeA = infoAposta[1].split(":")[0];
            String nomeTimeB = infoAposta[1].split(":")[1];

            apostas.add(new Aposta(LocalDateTime.parse(infoAposta[0], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    getJogoByNome(nomeTimeA, nomeTimeB), getClienteByCPF(infoAposta[2]), infoAposta[3],
                    Double.parseDouble(infoAposta[4])));
        }

    }

    public static void salvarApostas() throws Exception {
        String stringApostas = "";

        for (int i = 0; i < apostas.size(); ++i) {
            stringApostas += apostas.get(i).toString();

            if (i < apostas.size()) {
                stringApostas += "\n";
            }
        }

        Files.writeString(localApostas, stringApostas);
    }

    public static void addAposta(Aposta aposta) throws Exception {
        apostas.add(aposta);
        salvarApostas();
        
        Jogo jogo = getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB());

        if (aposta.getTimeEscolhido() == jogo.getNomeTimeA()) {
            jogo.setApostasA(jogo.getApostasA() + 1);
            jogo.setTotalApostadoA(jogo.getTotalApostadoA() + aposta.getValorApostado());
        } else if (aposta.getTimeEscolhido() == jogo.getNomeTimeB()) {
            jogo.setApostasB(jogo.getApostasB() + 1);
            jogo.setTotalApostadoB(jogo.getTotalApostadoB() + aposta.getValorApostado());
        }

        Double valorApostaA = jogo.getTotalApostadoA();
        Double valorApostaB = jogo.getTotalApostadoB();

        Double oddA = (((valorApostaB * 100) / valorApostaA) / 100) + 1;
        Double oddB = (((valorApostaA * 100) / valorApostaB) / 100) + 1;

        if(Double.isInfinite(oddA)) {
            oddA = 0.0;
        }
        if(Double.isInfinite(oddB)) {
            oddB = 0.0;
        }

        jogo.setValorVitoriaA(oddA);
        jogo.setValorVitoriaB(oddB);

        editJogo(jogo);
        //Resultados.notificarVitoria(aposta);
    }

    public static void delAposta(Aposta aposta) throws Exception {
        apostas.remove(aposta);
        salvarApostas();
    }

    public static void editAposta(Aposta aposta) throws Exception {
        int index = apostas.indexOf(aposta);
        apostas.set(index, aposta);
        salvarApostas();
    }

    public static Aposta getApostaByClienteJogo(Cliente cliente, Jogo jogo) {
        for (Aposta aposta : apostas) {
            if (aposta.getJogo().equals(jogo) && aposta.getCliente().equals(cliente)) {
                return aposta;
            }
        }
        return null;
    }

    public static List<Aposta> getApostasByCliente(Cliente cliente) {
        List<Aposta> apostasCliente = new ArrayList<>();
        for (Aposta aposta : apostas) {
            if (aposta.getCliente().equals(cliente)) {
                apostasCliente.add(aposta);
            }
        }
        return apostasCliente;
    }

    public static List<Aposta> getApostasByJogo(Jogo jogo) {
        List<Aposta> apostasJogo = new ArrayList<>();
        for (Aposta aposta : apostas) {
            if (aposta.getJogo().equals(jogo)) {
                apostasJogo.add(aposta);
            }
        }
        return apostasJogo;
    }

    public static List<Aposta> getApostas() {
        return apostas;
    }
}