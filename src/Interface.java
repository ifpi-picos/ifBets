import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;

public class Interface {
    private static JFrame frameInterface;
    private static Cliente cliente;

    public Interface() throws Exception {
        run();
    }

    public static void run() throws Exception {
        frameInterface = new JFrame("IFBets");
        frameInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameInterface.setSize(750, 500);
        frameInterface.setMinimumSize(new Dimension(750, 300));

        JPanel login = new JPanel();
        login.add(new JLabel("Digite seu CPF: "));
        JTextField cpfField = new JTextField(11);
        login.add(cpfField);

        boolean loginSucess = false;
        while (!loginSucess) {
            int loginOption = JOptionPane.showOptionDialog(frameInterface,
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
                    loginSucess = true;
                    Interface.cliente = cliente;
                    jogos(cliente);
                }
            } else if (loginOption == 1) {
                loginSucess = true;
                cadastrar();
            } else if (loginOption == 2) {
                System.exit(0);
            }
        }
    }

    public static boolean escolha(Object[] opcoes, String mensagem, String titulo) {
        int escolha = JOptionPane.showOptionDialog(
                frameInterface,
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
                frameInterface,
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
                    frameInterface,
                    cadastro,
                    "Cadastro",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[] { "Continuar", "Voltar" },
                    "Continuar");

            if (option == JOptionPane.OK_OPTION) {
                Boolean confirmar = escolha(
                        new Object[] { "Confirmar", "Voltar" },
                        "Tem certeza que deseja cadastrar essas informações?",
                        "Confirmar cadastro");
                if (confirmar) {
                    criarCliente = false;
                    try {
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
                        jogos(cliente);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frameInterface, "Erro ao cadastrar cliente");
                        cadastrar();
                    }
                } else {
                    criarCliente = true;
                }
            } else {
                criarCliente = false;
                frameInterface.dispose();
                run();
            }
        }
    }

    public static void jogos(Cliente cliente) {
        List<Jogo> jogos = Database.getJogos();

        JPanel clientePanel = new JPanel();
        JLabel nomeCliente = new JLabel(String.format(" Logado como: %s ", cliente.getNome()));
        nomeCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomeCliente.setAlignmentY(Component.CENTER_ALIGNMENT);
        nomeCliente.setBorder(BorderFactory.createEtchedBorder());
        JButton verApostas = new JButton("Ver suas apostas");
        JButton sair = new JButton("Sair");
        clientePanel.add(nomeCliente);
        clientePanel.add(verApostas);
        clientePanel.add(sair);

        verApostas.addActionListener(e -> {
            verApostas(cliente);
        });

        sair.addActionListener(e -> {
            try {
                frameInterface.dispose();
                run();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        JPanel jogosPanel = new JPanel();
        for (Jogo jogo : jogos) {
            JPanel jogoBox = new JPanel(new GridLayout(1, jogos.size() + 1, 5, 5));

            JLabel nomeJogo = new JLabel(String.format(" %s x %s - %s ", jogo.getNomeTimeA(), jogo.getNomeTimeB(),
                    jogo.getData().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))));
            nomeJogo.setBorder(BorderFactory.createEtchedBorder());
            nomeJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
            nomeJogo.setAlignmentY(Component.CENTER_ALIGNMENT);
            jogoBox.add(nomeJogo);
            JButton apostaA = new JButton(String.format(" Apostar em %s ", jogo.getNomeTimeA()));
            JButton apostaB = new JButton(String.format(" Apostar em %s ", jogo.getNomeTimeB()));
            // JButton apostaEmpate = new JButton(String.format(" Apostar em Empate "));
            jogoBox.add(apostaA);
            jogoBox.add(apostaB);
            // jogoBox.add(apostaEmpate);
            jogoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            jogoBox.setBorder(new EmptyBorder(3, 3, 3, 3));
            jogosPanel.add(jogoBox);

            apostaA.addActionListener(e -> {
                apostar(cliente, jogo, jogo.getNomeTimeA());
            });

            apostaB.addActionListener(e -> {
                apostar(cliente, jogo, jogo.getNomeTimeB());
            });

            // apostaEmpate.addActionListener(e -> {
            // apostar(cliente, jogo, "Empate");
            // });
        }

        JPanel contentPane = new JPanel(new BorderLayout());

        contentPane.add(clientePanel, BorderLayout.NORTH);
        contentPane.add(jogosPanel, BorderLayout.CENTER);

        frameInterface.remove(frameInterface.getContentPane());
        frameInterface.setTitle("IFBets - Jogos");
        frameInterface.setContentPane(contentPane);
        frameInterface.setVisible(true);

    }

    private static void apostar(Cliente cliente, Jogo jogo, String escolhaResultado) {
        Aposta verificarAposta = Database.getApostaByClienteJogo(cliente, jogo);

        if (verificarAposta == null) {
            JPanel valorParaAposta = new JPanel();
            valorParaAposta.setLayout(new BoxLayout(valorParaAposta, BoxLayout.Y_AXIS));
            valorParaAposta.add(new JLabel(String.format("Valor da aposta no %s: ", escolhaResultado)));
            JTextField inputValor = new JTextField();
            valorParaAposta.add(inputValor);

            int escolha = JOptionPane.showOptionDialog(frameInterface,
                    valorParaAposta,
                    "Valor para ser apostado",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.QUESTION_MESSAGE, null,
                    new Object[] { "Apostar", "Voltar" },
                    "Entrar");
            if (escolha == 0) {
                Double valorApostado = Double.parseDouble(inputValor.getText().replace(",", "."));
                if (valorApostado > 0) {
                    Aposta aposta = new Aposta(LocalDateTime.now(), jogo, cliente, escolhaResultado, valorApostado);
                    try {
                        Database.addAposta(aposta);
                    } catch (Exception e1) {
                        throw new RuntimeException(e1);
                    }
                    JOptionPane.showMessageDialog(frameInterface, "Aposta realizada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(frameInterface, "Valor inválido!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(frameInterface, "Você já apostou nesse jogo!");
        }
    }

    public static void verApostas(Cliente cliente) {
        List<Aposta> apostas = Database.getApostasByCliente(cliente);
        JPanel apostasPanel = new JPanel();

        JPanel clientePanel = new JPanel();
        JLabel nomeCliente = new JLabel(String.format(" Logado como: %s ", cliente.getNome()));
        nomeCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomeCliente.setAlignmentY(Component.CENTER_ALIGNMENT);
        nomeCliente.setBorder(BorderFactory.createEtchedBorder());
        JButton voltarApostas = new JButton("Voltar para o menu de apostas");
        JButton sair = new JButton("Sair");
        clientePanel.add(nomeCliente);
        clientePanel.add(voltarApostas);
        clientePanel.add(sair);

        voltarApostas.addActionListener(e -> {
            jogos(cliente);
        });

        sair.addActionListener(e -> {
            try {
                frameInterface.dispose();
                run();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        if (apostas.size() >= 1) {
            for (Aposta aposta : apostas) {
                JPanel apostaBox = new JPanel(new GridLayout(1, 1, 5, 5));

                Double ganhoPotencial;

                if (aposta.getTimeEscolhido().equals(aposta.getJogo().getNomeTimeA())) {
                    ganhoPotencial = aposta.getValorApostado() * Database
                            .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB())
                            .getValorVitoriaA();
                } else if (aposta.getTimeEscolhido().equals(aposta.getJogo().getNomeTimeB())) {
                    ganhoPotencial = aposta.getValorApostado() * Database
                            .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB())
                            .getValorVitoriaB();
                } else {
                    ganhoPotencial = aposta.getValorApostado() * Database
                            .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB())
                            .getValorEmpate();
                }

                JLabel infoAposta = new JLabel(
                        String.format(
                                " %s x %s - %s | Escolha: %s | Valor apostado: R$ %.2f | Ganho potencial: R$ %.2f ",
                                aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(),
                                aposta.getJogo().getData().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                                aposta.getTimeEscolhido(),
                                aposta.getValorApostado(), ganhoPotencial));
                infoAposta.setBorder(BorderFactory.createEtchedBorder());
                infoAposta.setAlignmentX(Component.CENTER_ALIGNMENT);
                infoAposta.setAlignmentY(Component.CENTER_ALIGNMENT);
                apostaBox.add(infoAposta);

                apostaBox.setAlignmentX(Component.CENTER_ALIGNMENT);
                apostaBox.setBorder(new EmptyBorder(3, 3, 3, 3));
                apostasPanel.add(apostaBox);
            }
        } else {
            JPanel apostaBox = new JPanel(new GridLayout(1, 1, 5, 5));
            apostaBox.add(new JLabel("Você ainda não fez nenhuma aposta!"));

            apostaBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            apostaBox.setBorder(new EmptyBorder(3, 3, 3, 3));
            apostasPanel.add(apostaBox);
        }
        JPanel contentPane = new JPanel(new BorderLayout());

        contentPane.add(clientePanel, BorderLayout.NORTH);
        contentPane.add(apostasPanel, BorderLayout.CENTER);

        frameInterface.remove(frameInterface.getContentPane());
        frameInterface.setContentPane(contentPane);
        frameInterface.setTitle("IFBets - Suas Apostas");
        frameInterface.setVisible(true);
    }

    public void atualizar() {
        frameInterface.dispose();
        frameInterface.setVisible(false);

        String local = frameInterface.getTitle().replace("IFBets - ", "");
        if (local.equals("Suas Apostas")) {
            verApostas(cliente);
        } else if (local.equals("Jogos")) {
            jogos(cliente);
        }
    }

    public static JFrame getFrame() {
        return frameInterface;
    }
}
