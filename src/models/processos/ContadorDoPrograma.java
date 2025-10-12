package models.processos;

import models.processos.comandos.Comando;
import java.util.LinkedList;

public class ContadorDoPrograma {
    public LinkedList<Comando> comandosParaExecutar;

    public ContadorDoPrograma() {
        this.comandosParaExecutar = new LinkedList<Comando>();
    }

    public int comandosRestantes() {
        return this.comandosParaExecutar.size();
    }

    public void adicionaComando(String comando) {
        this.comandosParaExecutar.offer(new Comando(comando));
    }

    public boolean temComandosParaExecutar() {
        return !this.comandosParaExecutar.isEmpty();
    }

    public Comando executaComando() {
        return this.comandosParaExecutar.poll();
    }

    @Override
    public String toString() {
        String str = "";
        for(Comando c : this.comandosParaExecutar) {
            str += c.tipo + " " + c.valor + "\n";
        }
        return str;
    }
}
