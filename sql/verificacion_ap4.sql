USE servicio_tecnico;
-- Consultas read-only para comprobar la persistencia de AP4 después de la prueba de menú.
SELECT o.id_orden, c.nombre AS cliente, e.tipo, e.marca, o.estado, o.fecha_ingreso
FROM orden_servicio o
JOIN equipo e ON e.id_equipo = o.id_equipo
JOIN cliente c ON c.id_cliente = e.id_cliente
ORDER BY o.id_orden DESC;
SELECT estado, COUNT(*) AS cantidad FROM orden_servicio GROUP BY estado ORDER BY estado;
SELECT h.id_orden, h.estado_anterior, h.estado_nuevo, h.fecha
FROM historial_estado h ORDER BY h.fecha DESC, h.id_evento DESC;
SELECT p.id_orden, p.monto, p.estado FROM presupuesto p ORDER BY p.id_orden DESC;
SELECT r.id_repuesto, r.nombre, r.stock FROM repuesto r ORDER BY r.id_repuesto;
