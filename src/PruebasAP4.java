import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Pruebas deterministas, sin requerir una instancia MySQL. */
public final class PruebasAP4 {
    private static int correctas = 0;
    private PruebasAP4() {}
    private static void comprobar(boolean condicion, String prueba) {
        if (!condicion) throw new AssertionError("FALLO: " + prueba);
        System.out.println("OK: " + prueba);
        correctas++;
    }

    private static final class RepositorioSimulado implements OrdenRepositorio {
        private final ArrayList<OrdenServicio> datos = new ArrayList<>();
        RepositorioSimulado() {
            LocalDateTime ahora = LocalDateTime.of(2026, 11, 10, 11, 0);
            datos.add(new OrdenServicio(1, "Ana", "Notebook Lenovo", EstadoOrden.RECIBIDO, ahora));
            datos.add(new OrdenServicio(2, "Luis", "PC escritorio", EstadoOrden.LISTO, ahora));
            datos.add(new OrdenServicio(3, "Marta", "Tablet", EstadoOrden.RECIBIDO, ahora));
        }
        public List<OrdenServicio> listarOrdenes() { return new ArrayList<>(datos); }
        public OrdenServicio buscarPorId(int id) { return datos.stream().filter(o -> o.getId() == id).findFirst().orElse(null); }
        public List<OrdenServicio> buscarPorEstado(EstadoOrden estado) {
            return datos.stream().filter(o -> o.getEstado() == estado).toList();
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        RepositorioSimulado fake = new RepositorioSimulado();
        ReporteOrdenes reporte = new ReporteOrdenes(fake);
        comprobar(reporte.obtenerOrdenes() instanceof ArrayList, "ArrayList explícito en servicio");
        comprobar(reporte.resumenEstados().contains("RECIBIDO: 2"), "Conteo por estado en arreglo");
        comprobar(reporte.resumenEstados().contains("LISTO: 1"), "Conteo de otro estado");
        comprobar(reporte.resumenEstados().contains("TOTAL: 3"), "Total de órdenes");
        comprobar(fake.buscarPorId(2).getEstado() == EstadoOrden.LISTO, "Consulta mediante interfaz");
        comprobar(fake.buscarPorEstado(EstadoOrden.RECIBIDO).size() == 2, "Filtro mediante interfaz");
        Path temporal = Files.createTempFile("servicio_ap4_", ".csv");
        try {
            comprobar(ArchivoOrdenesCsv.exportar(temporal, reporte.obtenerOrdenes()) == 3, "Exportación CSV UTF-8");
            String contenido = ArchivoOrdenesCsv.previsualizar(temporal, 10);
            comprobar(contenido.contains("Notebook Lenovo") && contenido.contains("RECIBIDO"), "Lectura CSV");
            comprobar(contenido.split("\n").length == 4, "CSV con encabezado y tres órdenes");
        } finally {
            Files.deleteIfExists(temporal);
        }
        try { new ReporteOrdenes(null); throw new AssertionError("faltó validar interfaz nula"); }
        catch (NullPointerException correcto) { comprobar(true, "Dependencia obligatoria"); }
        try { ArchivoOrdenesCsv.previsualizar(Path.of("inexistente.csv"), 0); throw new AssertionError("faltó validar límite"); }
        catch (IllegalArgumentException correcto) { comprobar(true, "Validación de cantidad positiva"); }
        System.out.println("RESULTADO: " + correctas + "/" + correctas + " verificaciones aprobadas.");
    }
}
