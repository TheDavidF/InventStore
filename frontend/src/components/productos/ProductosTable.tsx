import type { ProductoResponse } from "../../types/producto";
import StatusBadge from "../ui/StatusBadge";
type ProductosTableProps = {
  productos: ProductoResponse[];
  onEditar: (producto: ProductoResponse) => void;
  onDesactivar: (producto: ProductoResponse) => void;
  onActivar: (producto: ProductoResponse) => void;
};
function ProductosTable({productos, onEditar, onDesactivar, onActivar}: ProductosTableProps) {
  return (
    <div className="overflow-hidden rounded-2xl border border-brand-border bg-brand-surface shadow-sm">
      <div className="border-b border-brand-border px-5 py-4">
        <h2 className="font-serif text-xl text-brand-ink">Listado de productos</h2>
        <p className="text-xs text-brand-muted">{productos.length} registro(s) en la vista</p>
      </div>
      <div className="overflow-x-auto">
        <table className="w-full min-w-[720px] text-left text-sm">
          <thead className="bg-brand-bg/75 text-xs uppercase tracking-wide text-brand-primary">
            <tr>{["Producto","Categoría","Tipo","Precio","Estado","Acciones"].map(t=><th key={t} scope="col" className="px-5 py-3 font-semibold">{t}</th>)}</tr>
          </thead>
          <tbody className="divide-y divide-brand-border/70">
            {productos.length===0 && <tr><td colSpan={6} className="px-5 py-10 text-center text-brand-muted">No se encontraron productos.</td></tr>}
            {productos.map(producto=>(
              <tr key={producto.id} className="transition-colors hover:bg-brand-bg/40">
                <td className="px-5 py-4 font-semibold text-brand-ink">{producto.nombre}</td>
                <td className="px-5 py-4 text-brand-muted">{producto.categoriaNombre}</td>
                <td className="px-5 py-4 text-brand-muted">{producto.tipo==="PREPARADO"?"Preparado":"Directo"}</td>
                <td className="px-5 py-4 font-medium text-brand-ink">Q{producto.precio.toFixed(2)}</td>
                <td className="px-5 py-4"><StatusBadge activo={producto.activo}/></td>
                <td className="px-5 py-4">
                  <div className="flex gap-2">
                    <button type="button" onClick={()=>onEditar(producto)} className="rounded-lg border border-brand-border px-3 py-1.5 text-xs font-medium text-brand-primary hover:bg-brand-bg">Editar</button>
                    <button type="button" onClick={()=>producto.activo?onDesactivar(producto):onActivar(producto)} className="rounded-lg bg-brand-primary px-3 py-1.5 text-xs font-medium text-white hover:bg-brand-primary-dark">{producto.activo?"Desactivar":"Activar"}</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
export default ProductosTable;
