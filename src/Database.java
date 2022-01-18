import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Database {
    private static String localClientes = "resources/clientes.ifbets";
    private static String localJogos = "resources/jogos.ifbets";
    private static Map<String, ArrayList<ArrayList<String>>> clientes = new HashMap<>(0);
    private static Map<String, ArrayList<ArrayList<String>>> jogos = new HashMap<>(0);

    public static void carregarClientes() throws IOException {
        Path path = Path.of(localClientes);
        String s = Files.readString(path);

        String[] clienteString = s.split("\\|");

        for (String cliente : clienteString) {
            String[] infoCliente = cliente.replace("{", "").replace("}", "").split(",");

            ArrayList<String> valores = new ArrayList<>();
            ArrayList<String> endereco = new ArrayList<>();

            for (int i = 0; i < infoCliente.length; i++) {
                String[] info = infoCliente[i].split(":");

                if (i < 3) {
                    valores.add(info[1]);
                } else {
                    endereco.add(info[1]);
                }
            }

            String key = valores.get(0).replace(" ", "");

            valores.remove(0);
            ArrayList<ArrayList<String>> data = new ArrayList<>();

            data.add(valores);
            data.add(endereco);

            clientes.put(key, data);
        }
    }

    public static void salvarClientes() throws IOException {
        Path path = Path.of(localClientes);

        Set<Map.Entry<String, ArrayList<ArrayList<String>>>> entrySet = clientes.entrySet();
        String stringClientes = "";
        int count = 0;

        for (Object entry : entrySet) {
            String cliente = entry.toString();

            String cpf = cliente.split("=")[0];
            String[] info = cliente.split("=")[1].split("],");

            String nome = info[0].replace("[[", "").split(",")[0];

            while (nome.startsWith(" ")) {
                nome = nome.substring(1);
            }

            String dataNascimento = info[0].replace("[[", "").split(",")[1];

            while (dataNascimento.startsWith(" ")) {
                dataNascimento = dataNascimento.substring(1);
            }

            String enderecoString = info[1].replace("[", "").replace("]", "");

            while (enderecoString.startsWith(" ")) {
                enderecoString = enderecoString.substring(1);
            }

            String[] endereco = enderecoString.split(",");

            for (int i = 0; i < endereco.length; i++) {
                while (endereco[i].startsWith(" ")) {
                    endereco[i] = endereco[i].substring(1);
                }
            }

            stringClientes += String.format(
                    "{cpf:%s, nome:%s, dataDeNascimento:%s, endereco = logradouro:%s, cidade:%s, bairro:%s, rua:%s, numero:%s, uf:%s}",
                    cpf, nome, dataNascimento, endereco[0], endereco[1], endereco[2], endereco[3], endereco[4],
                    endereco[5]);

            if (count < entrySet.size() - 1) {
                stringClientes += "|";
            }

            count++;
        }
        ;

        Files.writeString(path, stringClientes);
    }

    public static boolean existeCliente(String cpf) {
        return clientes.containsKey(cpf);
    }

    public static void addCliente() {
        // TODO Preciso da classe Cliente antes de criar o método addCliente
    }

    public static void delCliente(String cpf) throws Exception {
        if (existeCliente(cpf)) {
            clientes.remove(cpf);
            salvarClientes();
        } else {
            throw new IllegalArgumentException("Cliente não existe");
        }
    }

    public static String getNomeCliente(String cpf) throws Exception {
        if (!existeCliente(cpf)) {
            throw new Exception("Cliente não encontrado");
        }

        Object info = clientes.get(cpf).get(0);
        String[] infoCliente = info.toString().replace("[", "").replace("]", "").split(",");
        String nome = infoCliente[0];

        while (nome.startsWith(" ")) {
            nome = nome.substring(1);
        }

        return nome;
    }

    public static String getDataNascimentoCliente(String cpf) throws Exception {
        if (!existeCliente(cpf)) {
            throw new Exception("Cliente não encontrado");
        }

        Object info = clientes.get(cpf).get(0);
        String[] infoCliente = info.toString().replace("[", "").replace("]", "").split(",");
        String dataNascimento = infoCliente[1];

        while (dataNascimento.startsWith(" ")) {
            dataNascimento = dataNascimento.substring(1);
        }

        return dataNascimento;
    }

    public static Map<String, String> getEnderecoCliente(String cpf) throws Exception {
        if (!existeCliente(cpf)) {
            throw new Exception("Cliente não encontrado");
        }

        Object info = clientes.get(cpf).get(1);
        String[] infoCliente = info.toString().replace("[", "").replace("]", "").split(",");

        for (int i = 0; i < infoCliente.length; i++) {
            while (infoCliente[i].startsWith(" ")) {
                infoCliente[i] = infoCliente[i].substring(1);
            }
        }

        Map<String, String> endereco = new HashMap<>(0) {
            {
                put("logradouro", infoCliente[0]);
                put("cidade", infoCliente[1]);
                put("bairro", infoCliente[2]);
                put("rua", infoCliente[3]);
                put("numero", infoCliente[4]);
                put("uf", infoCliente[5]);
            }
        };

        return endereco;
    }

    public static void setNomeCliente(String cpf, String nome) throws Exception {
        if (!existeCliente(cpf)) {
            throw new Exception("Cliente não encontrado");
        }

        Object info = clientes.get(cpf).get(0);
        String[] infoCliente = info.toString().replace("[", "").replace("]", "").split(",");
        infoCliente[0] = nome;

        ArrayList<String> newInfo = new ArrayList<>();

        while (infoCliente[0].startsWith(" ")) {
            infoCliente[0] = infoCliente[0].substring(1);
        }

        while (infoCliente[1].startsWith(" ")) {
            infoCliente[1] = infoCliente[1].substring(1);
        }

        newInfo.add(infoCliente[0]);
        newInfo.add(infoCliente[1]);

        clientes.get(cpf).set(0, newInfo);
        salvarClientes();
    }

    public static void setDataNascimentoCliente(String cpf, String data) throws Exception {
        if (!existeCliente(cpf)) {
            throw new Exception("Cliente não encontrado");
        }

        Object info = clientes.get(cpf).get(0);
        String[] infoCliente = info.toString().replace("[", "").replace("]", "").split(",");
        infoCliente[1] = data;

        ArrayList<String> newInfo = new ArrayList<>();

        while (infoCliente[0].startsWith(" ")) {
            infoCliente[0] = infoCliente[0].substring(1);
        }

        while (infoCliente[1].startsWith(" ")) {
            infoCliente[1] = infoCliente[1].substring(1);
        }

        newInfo.add(infoCliente[0]);
        newInfo.add(infoCliente[1]);

        clientes.get(cpf).set(0, newInfo);
        salvarClientes();
    }

    public static void setEnderecoCliente(String cpf) throws Exception {
        if (!existeCliente(cpf)) {
            throw new Exception("Cliente não encontrado");
        }

        Object info = clientes.get(cpf).get(1);
        String[] infoCliente = info.toString().replace("[", "").replace("]", "").split(",");

        for (int i = 0; i < infoCliente.length; i++) {
            while (infoCliente[i].startsWith(" ")) {
                infoCliente[i] = infoCliente[i].substring(1);
            }
        }

        // TODO Preciso da classe Endereco antes de criar o método setEnderecoCliente

    }

    public static void carregarJogos() throws IOException {
        Path path = Path.of(localJogos);
        String s = Files.readString(path);

        String[] jogosString = s.split("\\|");

        for (String jogo : jogosString) {
            String[] infoJogo = jogo.replace("{", "").replace("}", "").split(",");

            ArrayList<String> valores = new ArrayList<>();

            for (int i = 0; i < infoJogo.length; i++) {
                String[] info = infoJogo[i].split(":");

                valores.add(info[1]);
            }

            String key = valores.get(0).replace(" ", "");

            valores.remove(0);
            ArrayList<ArrayList<String>> data = new ArrayList<>();

            data.add(valores);

            jogos.put(key, data);
        }
    }

    public static void salvarJogos() throws IOException {
        Path path = Path.of(localJogos);

        Set<Map.Entry<String, ArrayList<ArrayList<String>>>> entrySet = jogos.entrySet();
        String stringJogos = "";
        int count = 0;

        for (Object entry : entrySet) {
            String jogo = entry.toString();

            String id = jogo.split("=")[0];
            String info = jogo.split("=")[1].replace("[[", "").replace("]]", "");

            String[] infoJogo = info.split(",");

            for (int i = 0; i < infoJogo.length; i++) {
                while (infoJogo[i].startsWith(" ")) {
                    infoJogo[i] = infoJogo[i].substring(1);
                }
            }

            stringJogos += String.format(
                    "{id:%s, nomeTimeA:%s, nomeTimeB:%s, data:%s, valorVitoriaA:%s, valorVitoriaB:%s, valorEmpate:%s, apostasA:%s, apostasB:%s, apostasEmpate:%s, totalApostado:%s}",
                    id, infoJogo[0], infoJogo[1], infoJogo[2], infoJogo[3], infoJogo[4], infoJogo[5], infoJogo[6],
                    infoJogo[7], infoJogo[8], infoJogo[9]);

            if (count < entrySet.size() - 1) {
                stringJogos += "|";
            }

            count++;
        }
        ;

        Files.writeString(path, stringJogos);
    }

    public static boolean existeJogo(String id) {
        return jogos.containsKey(id);
    }

    public static void addJogo(Jogo jogo) throws IOException {
        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        data.add(jogo.getNomeTimeA());
        data.add(jogo.getNomeTimeB());
        data.add(jogo.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH;mm")));
        data.add(String.format("%s", jogo.getValorVitoriaA()));
        data.add(String.format("%s", jogo.getValorVitoriaB()));
        data.add(String.format("%s", jogo.getValorEmpate()));
        data.add(String.format("%s", jogo.getApostasA()));
        data.add(String.format("%s", jogo.getApostasB()));
        data.add(String.format("%s", jogo.getApostasEmpate()));
        data.add(String.format("%s", jogo.getTotalApostado()));

        newInfo.add(data);

        jogos.put(String.format("%s-%s-%s", data.get(0), data.get(1), data.get(2)), newInfo);
        salvarJogos();
    }

    public static void delJogo(String id) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        jogos.remove(id);
        salvarJogos();
    }

    public static Map<String, String> getJogo(String id) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }
        }

        Map<String, String> jogo = new HashMap<>(0) {
            {
                put("nomeTimeA", infoJogo[0]);
                put("nomeTimeB", infoJogo[1]);
                put("data", infoJogo[2]);
                put("valorVitoriaA", infoJogo[3]);
                put("valorVitoriaB", infoJogo[4]);
                put("valorEmpate", infoJogo[5]);
                put("apostasA", infoJogo[6]);
                put("apostasB", infoJogo[7]);
                put("apostasEmpate", infoJogo[8]);
                put("totalApostado", infoJogo[9]);
            }
        };

        return jogo;
    }

    public static void setNomeTimeAJogo(String id, String nome) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[0] = nome;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setNomeTimeBJogo(String id, String nome) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[1] = nome;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setDataJogo(String id, String data) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[2] = data;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> dataJogo = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            dataJogo.add(infoJogo[i]);
        }

        newInfo.add(dataJogo);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setValorVitoriaAJogo(String id, String valor) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[3] = valor;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setValorVitoriaBJogo(String id, String valor) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[4] = valor;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setValorEmpateJogo(String id, String valor) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[5] = valor;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setApostasAJogo(String id, String valor) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[6] = valor;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setApostasBJogo(String id, String valor) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[7] = valor;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setApostasEmpateJogo(String id, String valor) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[8] = valor;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            data.add(infoJogo[i]);
        }

        newInfo.add(data);

        jogos.put(id, newInfo);
        salvarJogos();
    }

    public static void setTotalApostadoJogo(String id, String valor) throws Exception {
        if (!existeJogo(id)) {
            throw new Exception("Jogo não encontrado");
        }

        Object info = jogos.get(id).get(0);
        String[] infoJogo = info.toString().replace("[", "").replace("]", "").split(",");
        infoJogo[9] = valor;

        ArrayList<ArrayList<String>> newInfo = new ArrayList<>();
        ArrayList<String> dataJogo = new ArrayList<>();

        for (int i = 0; i < infoJogo.length; i++) {
            while (infoJogo[i].startsWith(" ")) {
                infoJogo[i] = infoJogo[i].substring(1);
            }

            dataJogo.add(infoJogo[i]);
        }

        newInfo.add(dataJogo);

        jogos.put(id, newInfo);
        salvarJogos();
    }
}
