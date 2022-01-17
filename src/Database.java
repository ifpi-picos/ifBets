import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Database {
    private static String localClientes = "resources/clientes.ifbets";
    private static Map<String, ArrayList<ArrayList<String>>> clientes = new HashMap<>(0);

    public static void carregarClientes() throws IOException {
        Path path = Path.of(localClientes);
        String s = Files.readString(path);

        String[] usuarios = s.split("\\|");

        for (String usuario : usuarios) {
            String[] infoUsuario = usuario.replace("{", "").replace("}", "").split(",");

            ArrayList<String> valores = new ArrayList<>();
            ArrayList<String> endereco = new ArrayList<>();

            for (int i = 0; i < infoUsuario.length; i++) {
                String[] info = infoUsuario[i].split(":");

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

}
