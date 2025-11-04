package controllers;

import models.BCP;
import models.processos.SegmentoTexto;
import views.LogLoad;

import java.io.IOException;
import java.util.*;

public class InputLoader {
    public TabelaDeProcessos tabelaProcessos;
    public int quantum;
    private HashMap<String, SegmentoTexto> processosNaoCarregados;

    public InputLoader() {
        this.tabelaProcessos = new TabelaDeProcessos();
        this.quantum = 0;
        this.processosNaoCarregados = new HashMap<String, SegmentoTexto>();
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
        List<String> chavesOrdenadas = new ArrayList<>(this.processosNaoCarregados.keySet());
        Collections.sort(chavesOrdenadas);
        for(String nomeArquivo : chavesOrdenadas) {
            if(!nomeArquivo.startsWith("quantum")) {
                BCP processo = this.criaProcesso(this.processosNaoCarregados.get(nomeArquivo));
                FilesIO.adicionaLog(new LogLoad(processo));
                this.tabelaProcessos.adicionaProcesso(processo);
            }
            else {
                String valor = this.processosNaoCarregados.get(nomeArquivo).pegaInstrucaoAuxiliar();
                int valorQuantum = Integer.parseInt(valor);
                this.quantum = valorQuantum;
                if(valorQuantum < 10) {
                    valor = "0" + String.valueOf(valorQuantum);
                }
                else {
                    valor = String.valueOf(valorQuantum);
                }
                FilesIO.setQuantum(valor);
            }
        }
        return this.tabelaProcessos;
    }
}
