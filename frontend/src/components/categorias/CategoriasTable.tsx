import type { CategoriaResponse } from "../../types/categoria";
import StatusBadge from "../ui/StatusBadge";
type Props = { categorias: CategoriaResponse[]; };
function CategoriasTable({categorias}:Props){
  return (
    <div className="overflow-hidden rounded-2xl border border-brand-border bg-brand-surface shadow-sm">
      <div className="border-b border-brand-border px-5 py-4">
        <h2 className="font-serif text-xl text-brand-ink">Listado de categorías</h2>
        <p className="text-xs text-brand-muted">{categorias.length} categoría(s) registrada(s)</p>
      </div>
      <div className="overflow-x-auto">
        <table className="w-full text-left text-sm">
          <thead className="bg-brand-bg/75 text-xs uppercase tracking-wide text-brand-primary">
            <tr><th className="px-5 py-3">ID</th><th className="px-5 py-3">Nombre</th><th className="px-5 py-3">Estado</th></tr>
          </thead>
          <tbody className="divide-y divide-brand-border/70">
            {categorias.length===0 ? <tr><td colSpan={3} className="px-5 py-10 text-center text-brand-muted">No hay categorías registradas.</td></tr> : categorias.map(c=>(
              <tr key={c.id} className="hover:bg-brand-bg/40">
                <td className="px-5 py-4 text-brand-muted">{c.id}</td>
                <td className="px-5 py-4 font-semibold text-brand-ink">{c.nombre}</td>
                <td className="px-5 py-4"><StatusBadge activo={c.activa} femenino/></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
export default CategoriasTable;
