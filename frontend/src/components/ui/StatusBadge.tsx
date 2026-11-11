type StatusBadgeProps = { activo: boolean; femenino?: boolean };
function StatusBadge({activo, femenino=false}: StatusBadgeProps) {
  return <span className={`inline-flex rounded-full px-3 py-1 text-xs font-semibold ${activo ? "bg-emerald-100 text-emerald-800" : "bg-stone-200 text-stone-700"}`}>
    {activo ? (femenino ? "Activa" : "Activo") : (femenino ? "Inactiva" : "Inactivo")}
  </span>;
}
export default StatusBadge;
