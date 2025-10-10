package models;

import java.util.LinkedList;

public class ProcessosProntos {
    private LinkedList<BCP> listaProcessos;

    public ProcessosProntos() {
        this.listaProcessos = new LinkedList<BCP>();
    }

    public void adicionaProcesso(BCP processo) {
        this.listaProcessos.offer(processo);
    }

    public BCP removeProcesso() {
        return this.listaProcessos.poll();
    }

    public int tamanhoFila() {
        return this.listaProcessos.size();
    }

    public BCP verProximoProcesso() {
        return this.listaProcessos.peek();
    }

    public void listaProcessos() {
        System.out.print("Fila de processos prontos: ");
        for (BCP processo : this.listaProcessos) {
            System.out.print(processo.nomeProcesso + " ");
        }
        System.out.println();
    }
}
