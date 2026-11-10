import java.sql.SQLException;
import java.util.List;

/** Contrato del repositorio de órdenes: separa la aplicación del acceso JDBC. */
public interface OrdenRepositorio {
    List<OrdenServicio> listarOrdenes() throws SQLException;
    OrdenServicio buscarPorId(int id) throws SQLException;
    List<OrdenServicio> buscarPorEstado(EstadoOrden estado) throws SQLException;
}
