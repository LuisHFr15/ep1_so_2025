package models;

import java.util.HashMap;

public class ProcessosBloqueados {
    // processo : tempo de espera
    private HashMap<BCP, Integer> listaProcessos;
    private static int quantum;

    public int getQuantum() {
        return quantum;
    }

    public ProcessosBloqueados() {
        this.listaProcessos = new HashMap<BCP, Integer>();
        this.quantum = 10;
    }

    public void adicionaProcesso(BCP processo) {
        HashMap<BCP, Integer> processoBloqueado = new HashMap<BCP, Integer>();
        processoBloqueado.put(processo, this.getQuantum());
    }

    public void liberaProcesso(BCP processo) {
        this.listaProcessos.remove(processo);
    }

    public int tamanhoFila() {
        return this.listaProcessos.size();
    }

    public void listaProcessos() {
        System.out.print("Fila de processos bloqueados: ");
        for (BCP bloqueado : this.listaProcessos.keySet()) {
            System.out.print(bloqueado.nomeProcesso + " ");
        }
        System.out.println();
    }

    public void reduzirTempoEspera() {
        for (BCP bloqueado : this.listaProcessos.keySet()) {
            int tempoEspera = this.listaProcessos.get(bloqueado);
            this.listaProcessos.put(bloqueado, tempoEspera - 1);
        }
    }

    public HashMap<BCP, Integer> getListaProcessos() {
        return this.listaProcessos;
    }
}
