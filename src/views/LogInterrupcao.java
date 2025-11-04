package views;

import models.BCP;

public class LogInterrupcao implements LogStrategy {
    private BCP processo;
    private int instrucoes;
    public LogInterrupcao(BCP processo, int instrucoes) {
        this.processo = processo;
        this.instrucoes = instrucoes;
    }

    public String log() {
        String logString = "Interrompendo " + processo.nomeProcesso + " após " + this.instrucoes + " instruções";
        return logString;
    }
}
