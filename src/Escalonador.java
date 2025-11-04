import controllers.*;
import models.*;
import models.processos.comandos.Comando;
import models.processos.comandos.TipoComando;
import views.*;

import java.io.IOException;
import java.util.ArrayList;

public class Escalonador {
    public static void main(String[] args) throws IOException {
        InputLoader loader = new InputLoader();
        TabelaDeProcessos tabela = loader.constroiTabelaDeProcessos();
        int quantum = loader.quantum;

        // loop de execução até que todos os processos sejam finalizados
        while(tabela.temProcessosNaoFinalizados()) {
            tabela.desbloqueiaProcessos();
            BCP atual = tabela.pegaProximoProcessoDaFila();
            if(atual == null) {
                while(!tabela.temProcessosProntos() && tabela.desbloqueiaProcessos()) {
                }
                continue;
            }
            System.out.println(atual);
            tabela.alteraProcessoParaExecutando(atual);

            int instrucoes = 0;
            Comando com = null;
            while(instrucoes < quantum && atual.temComandosParaExecutar()) {
                com = atual.executaProcesso();
                instrucoes++;

                if(com == null) {
                    break;
                }
                if(com.tipo == TipoComando.E_S || com.tipo == TipoComando.SAIDA) {
                    break;
                }
            }
            tabela.trocaProcessos(instrucoes, com);
            if(com == null) {
                tabela.completaProcesso(atual);
                continue;
            }
            if(com.tipo == TipoComando.E_S) {
                tabela.bloqueiaProcesso(atual);
            }
            else if(com.tipo == TipoComando.SAIDA) {
                tabela.completaProcesso(atual);
            }
            else {
                tabela.prontificaProcesso(atual);
            }
        }
        FilesIO.adicionaLog(new LogTodosTerminados(tabela, quantum));
        FilesIO.escreveLog();
    }
}