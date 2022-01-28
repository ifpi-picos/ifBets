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
        String.format("Parabéns %s, você acertou o resultado do jogo %s x %s, o %s venceu e você irá receber %.2f",
            aposta.getCliente().getNome(), aposta.getJogo().getNomeTimeA(), aposta.getJogo().getNomeTimeB(),
            aposta.getTimeEscolhido(), aposta.getValorApostado(), ganho));
  }
}
