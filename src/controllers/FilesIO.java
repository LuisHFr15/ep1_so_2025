package controllers;

import models.processos.SegmentoTexto;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class FilesIO {
    private static SegmentoTexto leArquivo(Path filePath) throws IOException {
        SegmentoTexto codigoProcesso = new SegmentoTexto();
        try(BufferedReader leitor = Files.newBufferedReader(filePath)) {
            leitor.lines().forEach(line -> {
                codigoProcesso.adicionaInstrucao(line);
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            return codigoProcesso;
        }
    }

    public static HashMap<String, SegmentoTexto> carregaDadosDosProcessos() throws IOException {
        Path path = Paths.get("programas");
        HashMap<String, SegmentoTexto> processos = new HashMap<String, SegmentoTexto>();
        try(Stream<Path> paths = Files.list(path)) {
            paths.sorted().forEach(filePath -> {
                try {
                    SegmentoTexto processo = leArquivo(filePath);
                    processos.put(filePath.getFileName().toString(), processo);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            return processos;
        }
    }
}
