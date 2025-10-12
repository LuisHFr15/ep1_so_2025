package models;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProcessosBloqueados {
    // processo : tempo de espera
    private Map<BCP, Integer> listaProcessos;
    private static int quantum;

    public int getQuantum() {
        return quantum;
    }

    public ProcessosBloqueados() {
        this.listaProcessos = new HashMap<BCP, Integer>();
        this.quantum = 10;
    }

    public Map<BCP, Integer> getListaProcessos() {
        return this.listaProcessos;
    }

    public void adicionaProcesso(BCP processo) {
        this.listaProcessos.put(processo, this.getQuantum());
    }

    public void liberaProcesso(BCP processo) {
        this.listaProcessos.remove(processo);
    }

    public int tamanhoFila() {
        return this.listaProcessos.size();
    }

    public void reduzirTempoEspera() {
        HashMap<BCP, Integer> novaListaBloqueados = new LinkedHashMap<BCP, Integer>();
        for (BCP bloqueado : this.listaProcessos.keySet()) {
            int tempoEspera = this.listaProcessos.get(bloqueado);
            novaListaBloqueados.put(bloqueado, tempoEspera - 1);
        }
        this.listaProcessos = novaListaBloqueados;
    }

    @Override
    public String toString() {
        String retorno = "";
        for (BCP processo : this.listaProcessos.keySet()) {
            retorno += "Processo: " + processo.nomeProcesso + " ";
            retorno += "Tempo espera: " + this.listaProcessos.get(processo) + " | ";
        }
        return retorno;
    }
}
