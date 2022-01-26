import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Interface {

  public static void run() throws Exception {
    JPanel login = new JPanel();
    login.add(new JLabel("Digite seu CPF: "));
    JTextField cpfField = new JTextField(11);
    login.add(cpfField);

    boolean loginSucess = false;
    while (!loginSucess) {
      int loginOption = JOptionPane.showOptionDialog(null,
          login,
          "Login",
          JOptionPane.PLAIN_MESSAGE,
          JOptionPane.QUESTION_MESSAGE, null,
          new Object[] { "Entrar", "Registrar", "Sair" },
          "Entrar");

      if (loginOption == 0) {
        Cliente cliente = Database.getClienteByCPF(cpfField.getText());
        if (cliente == null) {
          JPanel semCpf = new JPanel();
          semCpf.add(new JLabel("CPF não encontrado! Deseja se registrar?"));
          int escolhaSemCpf = opcoes(
              semCpf,
              "CPF não encontrado",
              new Object[] { "Registrar", "Voltar", "Sair" });
          if (escolhaSemCpf == 0) {
            loginSucess = true;
            cadastrar();
          } else if (escolhaSemCpf == 1) {
            continue;
          } else {
            System.exit(0);
          }
        } else {
          JOptionPane.showMessageDialog(
              null,
              "Bem vindo, " + cliente.getNome());
          loginSucess = true;
        }
      } else if (loginOption == 1) {
        loginSucess = true;
        cadastrar();
      } else if (loginOption == 2) {
        System.exit(0);
      }
    }
  }

  public static boolean escolha(
      Object[] opcoes,
      String mensagem,
      String titulo) {
    int escolha = JOptionPane.showOptionDialog(
        null,
        mensagem,
        titulo,
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        opcoes,
        opcoes[0]);
    if (escolha == JOptionPane.YES_OPTION) {
      return true;
    } else {
      return false;
    }
  }

  public static int opcoes(JPanel panel, String titulo, Object[] opcoes) {
    int option = JOptionPane.showOptionDialog(
        null,
        panel,
        titulo,
        JOptionPane.PLAIN_MESSAGE,
        JOptionPane.QUESTION_MESSAGE,
        null,
        opcoes,
        opcoes[0]);

    return option;
  }

  public static void cadastrar() throws Exception {
    boolean criarCliente = true;
    while (criarCliente) {
      JTextField cpf = new JTextField(11);
      JTextField nome = new JTextField();
      JFormattedTextField email = new JFormattedTextField("exemplo@gmail.com");
      JFormattedTextField dataDeNascimento = new JFormattedTextField("##/##/####");
      JTextField logradouro = new JTextField();
      JTextField cidade = new JTextField();
      JTextField bairro = new JTextField();
      JTextField rua = new JTextField();
      JTextField numero = new JTextField();
      JTextField uf = new JTextField();

      JPanel cadastro = new JPanel();
      cadastro.setLayout(new BoxLayout(cadastro, BoxLayout.Y_AXIS));
      cadastro.add(new JLabel("CPF: "));
      cadastro.add(cpf);
      cadastro.add(new JLabel("Nome: "));
      cadastro.add(nome);
      cadastro.add(new JLabel("Email: "));
      cadastro.add(email);
      cadastro.add(new JLabel("Data de Nascimento: "));
      cadastro.add(dataDeNascimento);
      cadastro.add(new JLabel("Logradouro: "));
      cadastro.add(logradouro);
      cadastro.add(new JLabel("Cidade: "));
      cadastro.add(cidade);
      cadastro.add(new JLabel("Bairro: "));
      cadastro.add(bairro);
      cadastro.add(new JLabel("Rua: "));
      cadastro.add(rua);
      cadastro.add(new JLabel("Numero: "));
      cadastro.add(numero);
      cadastro.add(new JLabel("UF: "));
      cadastro.add(uf);
      cadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
      cadastro.setAlignmentY(Component.CENTER_ALIGNMENT);

      int option = JOptionPane.showOptionDialog(
          null,
          cadastro,
          "Cadastro",
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          new Object[] { "Continuar", "Sair" },
          "Continuar");

      if (option == JOptionPane.OK_OPTION) {
        Boolean confirmar = escolha(
            new Object[] { "Confirmar", "Voltar" },
            "Tem certeza que deseja cadastrar essas informações?",
            "Confirmar cadastro");
        if (confirmar) {
          criarCliente = false;

          Cliente cliente = new Cliente(
              cpf.getText().replace(".", "").replace("-", ""),
              nome.getText(),
              email.getText(),
              LocalDate.parse(dataDeNascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
              new Endereco(
                  logradouro.getText(),
                  cidade.getText(),
                  bairro.getText(),
                  rua.getText(),
                  Integer.parseInt(numero.getText()),
                  uf.getText()));

          Database.addCliente(cliente);

        } else {
          criarCliente = true;
        }
      } else {
        criarCliente = false;
        System.exit(0);
      }
    }
  }
}
