import javax.swing.*;

public class Resultados {

  public static void notificarVitoria(Aposta aposta) {
    Double ganho;
    Double odd;

    if (aposta.getTimeEscolhido().equals(aposta.getJogo().getNomeTimeA())) {
      ganho = aposta.getValorApostado() * Database
          .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB()).getValorVitoriaA();

      odd = Database.getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB()).getValorVitoriaA();
    } else {
      ganho = aposta.getValorApostado() * Database
          .getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB()).getValorVitoriaB();
      odd = Database.getJogoByNome(aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB()).getValorVitoriaB();
    }

    JOptionPane.showMessageDialog(null,
        String.format(
            "Parabéns %s, você acertou o resultado do jogo %s x %s, o %s venceu e com uma aposta de R$ %.2f a %.2fx você irá receber R$ %.2f",
            aposta.getCliente().getNome(), aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(),
            aposta.getTimeEscolhido(), aposta.getValorApostado(), odd, ganho));
  }

  public static void notificarDerrota(Aposta aposta) {
    String vencedor = aposta.getTimeEscolhido() == aposta.getJogo().getNomeTimeA() ? aposta.getJogo().getNomeTimeB()
        : aposta.getJogo().getNomeTimeA();

    JOptionPane.showMessageDialog(null,
        String.format("Infelizmente %s, você errou o resultado do jogo %s x %s, o %s venceu e você perdeu R$ %.2f",
            aposta.getCliente().getNome(), aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(),
            vencedor, aposta.getValorApostado()));
  }

  public static void notificarEmpate(Aposta aposta) {
    JOptionPane.showMessageDialog(null,
        String.format(
            "Infelizmente %s, você errou o resultado do jogo %s x %s, O resultado foi um empate e você perdeu R$ %.2f",
            aposta.getCliente().getNome(), aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(),
            aposta.getValorApostado()));
  }
}
