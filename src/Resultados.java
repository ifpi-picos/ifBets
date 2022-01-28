import javax.swing.*;

public class Resultados {

  public static void notificarVitoria(Aposta aposta) {
    Double ganho;

    if (aposta.getTimeEscolhido().equals(aposta.getJogo().getNomeTimeA())) {
      ganho = aposta.getValorApostado() * Database
          .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB()).getValorVitoriaA();
    } else if (aposta.getTimeEscolhido().equals(aposta.getJogo().getNomeTimeB())) {
      ganho = aposta.getValorApostado() * Database
          .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB()).getValorVitoriaB();
    } else {
      ganho = aposta.getValorApostado() * Database
          .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB()).getValorEmpate();
    }

    JOptionPane.showMessageDialog(null,
        String.format("Parabéns %s, você acertou o resultado do jogo %s x %s, o %s venceu e você irá receber R$ %.2f",
            aposta.getCliente().getNome(), aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(),
            aposta.getTimeEscolhido(), aposta.getValorApostado(), ganho));
  }

  public static void notificarDerrota(Aposta aposta) {
    JOptionPane.showMessageDialog(null,
        String.format("Infelizmente %s, você errou o resultado do jogo %s x %s, o %s venceu e você perdeu R$ %.2f",
            aposta.getCliente().getNome(), aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(),
            aposta.getTimeEscolhido(), aposta.getValorApostado()));
  }

  public static void notificarEmpate(Aposta aposta) {
    JOptionPane.showMessageDialog(null,
        String.format(
            "Infelizmente %s, você errou o resultado do jogo %s x %s, O resultado foi um empate e você perdeu R$ %.2f",
            aposta.getCliente().getNome(), aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(), aposta.getValorApostado()));
  }
}
