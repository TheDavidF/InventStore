import type { FormEvent } from "react";
import type { ProductoForm as ProductoFormData, ProductoResponse } from "../../types/producto";
import type { CategoriaResponse } from "../../types/categoria";
type ProductoFormProps = {
  producto: ProductoFormData;
  categorias: CategoriaResponse[];
  productoEditando: ProductoResponse | null;
  cargando: boolean;
  error: string;
  onChange: (producto: ProductoFormData) => void;
  onSubmit: (event: FormEvent<HTMLFormElement>) => void;
  onCancelar: () => void;
};
const inputClass="w-full rounded-lg border border-brand-border bg-white px-3 py-2.5 text-sm text-brand-ink outline-none transition focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/15 disabled:bg-brand-bg/70";
const labelClass="mb-1.5 block text-sm font-semibold text-brand-ink";
function ProductoForm({producto,categorias,productoEditando,cargando,error,onChange,onSubmit,onCancelar}:ProductoFormProps){
  return (
    <form onSubmit={onSubmit} className="space-y-4 px-6 py-5">
      <div><label htmlFor="nombre-producto" className={labelClass}>Nombre</label>
        <input id="nombre-producto" className={inputClass} required maxLength={120} placeholder="Ej. Quesadillas" value={producto.nombre} onChange={e=>onChange({...producto,nombre:e.target.value})}/></div>
      <div className="grid gap-4 sm:grid-cols-2">
        <div><label htmlFor="precio-producto" className={labelClass}>Precio (Q)</label>
          <input id="precio-producto" type="number" min="0.01" step="0.01" required className={inputClass} placeholder="0.00" value={producto.precio} onChange={e=>onChange({...producto,precio:e.target.value})}/></div>
        <div><label htmlFor="tipo-producto" className={labelClass}>Tipo</label>
          <select id="tipo-producto" className={inputClass} disabled={productoEditando!==null} value={producto.tipo} onChange={e=>onChange({...producto,tipo:e.target.value})}>
            <option value="DIRECTO">Directo</option><option value="PREPARADO">Preparado</option>
          </select></div>
      </div>
      <div><label htmlFor="descripcion-producto" className={labelClass}>Descripción</label>
        <textarea id="descripcion-producto" rows={2} className={inputClass} placeholder="Descripción opcional" value={producto.descripcion} onChange={e=>onChange({...producto,descripcion:e.target.value})}/></div>
      <div><label htmlFor="categoria-producto" className={labelClass}>Categoría</label>
        <select id="categoria-producto" required={!productoEditando} disabled={productoEditando!==null} className={inputClass} value={producto.categoriaId??""} onChange={e=>onChange({...producto,categoriaId:e.target.value===""?null:Number(e.target.value)})}>
          <option value="">Seleccione una categoría</option>
          {categorias.filter(c=>c.activa || c.id===producto.categoriaId).map(c=><option key={c.id} value={c.id}>{c.nombre}</option>)}
        </select></div>
      {error && <p role="alert" className="rounded-lg bg-red-50 px-3 py-2 text-sm text-red-700">{error}</p>}
      <div className="flex justify-end gap-3 border-t border-brand-border pt-4">
        <button type="button" disabled={cargando} onClick={onCancelar} className="rounded-lg border border-brand-border bg-white px-4 py-2 text-sm font-semibold text-brand-ink hover:bg-brand-bg">Cancelar</button>
        <button type="submit" disabled={cargando} className="rounded-lg bg-brand-primary px-4 py-2 text-sm font-semibold text-white hover:bg-brand-primary-dark disabled:opacity-60">{cargando?"Guardando...":productoEditando?"Guardar cambios":"Crear producto"}</button>
      </div>
    </form>
  );
}
export default ProductoForm;
