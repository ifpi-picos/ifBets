import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;

public class Administrador {
    private static JFrame frameInterface;

    public static void run(Interface interfaceUsuario) throws Exception {
        List<Jogo> jogos = Database.getJogos();
        frameInterface = new JFrame("IFBets - Controle dos Jogos");
        frameInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameInterface.setSize(950, 500);
        frameInterface.setMinimumSize(new Dimension(950, 300));

        JPanel controle = new JPanel();
        JButton atualizar = new JButton("Atualizar interface");
        JButton cadastrar = new JButton("Cadastrar novo jogo");
        atualizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        atualizar.setAlignmentY(Component.CENTER_ALIGNMENT);
        cadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadastrar.setAlignmentY(Component.CENTER_ALIGNMENT);

        controle.add(atualizar);
        controle.add(cadastrar);

        atualizar.addActionListener(l -> {
            try {
                atualizar(interfaceUsuario);
            } catch (Exception e) {
            }
        });

        cadastrar.addActionListener(l -> {
            try {
                cadastrar();
                atualizar(interfaceUsuario);
            } catch (Exception e) {
            }
        });

        JPanel jogosPanel = new JPanel();
        for (Jogo jogo : jogos) {
            JPanel jogoBox = new JPanel(new GridLayout(1, 1, 5, 5));

            JLabel nomeJogo = new JLabel(String.format(" %s x %s - %s ", jogo.getNomeTimeA(), jogo.getNomeTimeB(),
                    jogo.getData().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))));
            nomeJogo.setBorder(BorderFactory.createEtchedBorder());
            nomeJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
            nomeJogo.setAlignmentY(Component.CENTER_ALIGNMENT);
            jogoBox.add(nomeJogo);
            JButton vitoriaA = new JButton(String.format(" Declarar vitoria de %s ", jogo.getNomeTimeA()));
            JButton vitoriaB = new JButton(String.format(" Declarar vitoria de %s ", jogo.getNomeTimeB()));
            JButton empate = new JButton(" Declarar empate ");

            jogoBox.add(vitoriaA);
            jogoBox.add(vitoriaB);
            jogoBox.add(empate);

            jogoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            jogoBox.setBorder(new EmptyBorder(3, 3, 3, 3));
            jogosPanel.add(jogoBox);

            vitoriaA.addActionListener(l -> {
                finalizarJogo(jogo, jogo.getNomeTimeA());
                try {
                    atualizar(interfaceUsuario);
                } catch (Exception e) {}
            });

            vitoriaB.addActionListener(l -> {
                finalizarJogo(jogo, jogo.getNomeTimeB());
                try {
                    atualizar(interfaceUsuario);
                } catch (Exception e) {}
            });

            empate.addActionListener(l -> {
                finalizarJogo(jogo, "Empate");
                try {
                    atualizar(interfaceUsuario);
                } catch (Exception e) {}
            });
        }

        JPanel contentPane = new JPanel(new BorderLayout());

        contentPane.add(controle, BorderLayout.NORTH);
        contentPane.add(jogosPanel, BorderLayout.CENTER);

        frameInterface.remove(frameInterface.getContentPane());
        frameInterface.setContentPane(contentPane);
        frameInterface.setVisible(true);
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
        boolean criarJogo = true;
        while (criarJogo) {
            JTextField nomeTimeA = new JTextField();
            JTextField nomeTimeB = new JTextField();
            JFormattedTextField data = new JFormattedTextField("dd/MM/yyyy HH:mm");

            JPanel cadastro = new JPanel();
            cadastro.setLayout(new BoxLayout(cadastro, BoxLayout.Y_AXIS));
            cadastro.add(new JLabel("Nome do time A: "));
            cadastro.add(nomeTimeA);
            cadastro.add(new JLabel("Nome do time B: "));
            cadastro.add(nomeTimeB);
            cadastro.add(new JLabel("Data: "));
            cadastro.add(data);
            cadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
            cadastro.setAlignmentY(Component.CENTER_ALIGNMENT);

            int option = JOptionPane.showOptionDialog(
                    frameInterface,
                    cadastro,
                    "Cadastrar um jogo",
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
                    criarJogo = false;
                    try {
                        Jogo jogo = new Jogo(nomeTimeA.getText(), nomeTimeB.getText(),
                                LocalDateTime.parse(data.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                                0.0, 0.0, 0.0, 0, 0, 0, 0.0, 0.0);

                        Database.addJogo(jogo);
                        JOptionPane.showMessageDialog(frameInterface, "Jogo cadastrado com sucesso!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frameInterface, "Erro ao cadastrar cliente");
                        cadastrar();
                    }
                } else {
                    criarJogo = true;
                }
            } else {
                criarJogo = false;
            }
        }

    }

    public static void finalizarJogo(Jogo jogo, String resultado){
        Boolean confirmar = escolha(
                new Object[] { "Confirmar", "Voltar" },
                "Tem certeza que deseja finalizar o jogo?",
                "Confirmar finalização");
        if (confirmar) {
            try {
                List<Aposta> apostas = Database.getApostasByJogo(jogo);
                for (Aposta aposta : apostas) {
                    if(aposta.getTimeEscolhido().equals(resultado)){
                        Resultados.notificarVitoria(aposta);
                        Database.delAposta(aposta);
                    } else {
                        if(resultado.equals("Empate")){
                            Resultados.notificarEmpate(aposta);
                            Database.delAposta(aposta);
                        } else {
                            Resultados.notificarDerrota(aposta);
                            Database.delAposta(aposta);
                        }
                    }
                }
                Database.delJogo(jogo);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frameInterface, "Erro ao finalizar jogo");
            }
        }
    }

    public static void atualizar(Interface interfaceUsuario) throws Exception {
        interfaceUsuario.atualizar();
        frameInterface.dispose();
        frameInterface.setVisible(false);
        try {
            run(interfaceUsuario);
        } catch (Exception e) {
        }
    }
}
