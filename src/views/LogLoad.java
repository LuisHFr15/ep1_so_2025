package views;

import models.BCP;

public class LogLoad implements LogStrategy {
    private BCP processo;
    public LogLoad(BCP processo) {
        this.processo = processo;
    }

    public String log() {
        String logString = "Carregando " + processo.nomeProcesso;
        return logString;
    }
}
