import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Salida secundaria de respaldo; NO sustituye la persistencia MySQL. */
public final class ArchivoOrdenesCsv {
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private ArchivoOrdenesCsv() {}

    private static String escapar(String valor) {
        String s = valor == null ? "" : valor;
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    public static int exportar(Path archivo, List<OrdenServicio> ordenes) throws IOException {
        if (archivo == null || ordenes == null) throw new IllegalArgumentException("Ruta y órdenes obligatorias");
        Path absoluta = archivo.toAbsolutePath().normalize();
        if (absoluta.getParent() != null) Files.createDirectories(absoluta.getParent());
        try (BufferedWriter salida = Files.newBufferedWriter(absoluta, StandardCharsets.UTF_8)) {
            salida.write("id;cliente;equipo;estado;fecha_ingreso");
            salida.newLine();
            for (OrdenServicio o : ordenes) {
                salida.write(o.getId() + ";" + escapar(o.getCliente()) + ";" + escapar(o.getEquipo()) + ";"
                        + o.getEstado().name() + ";" + o.getFechaIngreso().format(FECHA));
                salida.newLine();
            }
        }
        return ordenes.size();
    }

    public static String previsualizar(Path archivo, int maximo) throws IOException {
        if (maximo < 1) throw new IllegalArgumentException("Máximo inválido");
        StringBuilder texto = new StringBuilder();
        try (BufferedReader entrada = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            String linea;
            int leidas = 0;
            while ((linea = entrada.readLine()) != null && leidas < maximo) {
                texto.append(linea).append('\n');
                leidas++;
            }
        }
        return texto.toString();
    }
}
