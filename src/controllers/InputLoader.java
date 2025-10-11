package controllers;

import models.BCP;
import models.processos.SegmentoTexto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InputLoader {
    public TabelaDeProcessos tabelaProcessos;
    public int quantum;
    private HashMap<String, SegmentoTexto> processosNaoCarregados;

    public InputLoader() {
        this.tabelaProcessos = new TabelaDeProcessos();
        this.quantum = 0;
        this.processosNaoCarregados = new HashMap<String, SegmentoTexto>();
    }

    public int getQuantum() {
        return this.quantum;
    }

    private void carregaDados() throws IOException {
        try {
            this.processosNaoCarregados = FilesIO.carregaDadosDosProcessos();
        }
        catch (IOException e) {
            throw new IOException("Erro ao carregar dados dos processos: " + e.getMessage());
        }
    }

    private BCP criaProcesso(SegmentoTexto codigoProcesso) {
        String nomeProcesso = codigoProcesso.pegaInstrucaoAuxiliar();
        BCP processo = new BCP(nomeProcesso, codigoProcesso);
        return processo;
    }

    public TabelaDeProcessos constroiTabelaDeProcessos() throws IOException {
        this.carregaDados();
        for(String nomeArquivo : this.processosNaoCarregados.keySet()) {
            if(!nomeArquivo.startsWith("quantum")) {
                BCP processo = this.criaProcesso(this.processosNaoCarregados.get(nomeArquivo));
                this.tabelaProcessos.adicionaProcesso(processo);
            }
            else {
                String valor = this.processosNaoCarregados.get(nomeArquivo).pegaInstrucaoAuxiliar();
                this.quantum = Integer.parseInt(valor);
            }
        }
        return this.tabelaProcessos;
    }
}
