package views;

import models.BCP;

public class LogExecutando implements LogStrategy {
    private BCP processo;
    public LogExecutando(BCP processo) {
        this.processo = processo;
    }

    public String log() {
        String nome = processo.nomeProcesso;
        String logString = "Executando " + nome;

        return logString;
    }
}
