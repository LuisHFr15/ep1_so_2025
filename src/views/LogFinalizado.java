package views;

import models.BCP;
import models.processos.Registrador;

public class LogFinalizado implements LogStrategy {
    private BCP processo;
    public LogFinalizado(BCP processo){
        this.processo = processo;
    }

    public String log() {
        String logString = processo.nomeProcesso + " terminado. ";
        for(Registrador reg : processo.getRegistradores()) {
            logString += reg.toString() + ". ";
        }
        return logString;
    }
}
