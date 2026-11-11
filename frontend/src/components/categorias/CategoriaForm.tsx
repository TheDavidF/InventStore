type Props = {
  nombre: string;
  cargando: boolean;
  error: string;
  onNombreChange: (valor: string) => void;
  onGuardar: () => Promise<void>;
  onCerrar: () => void;
};
function CategoriaForm({nombre,cargando,error,onNombreChange,onGuardar,onCerrar}:Props){
  return (
    <form onSubmit={e=>{e.preventDefault();void onGuardar();}} className="space-y-5 px-6 py-5">
      <div>
        <label htmlFor="nombre-categoria" className="mb-2 block text-sm font-semibold text-brand-ink">Nombre de la categoría</label>
        <input id="nombre-categoria" autoFocus required maxLength={100} className="w-full rounded-lg border border-brand-border bg-white px-4 py-3 text-sm text-brand-ink outline-none focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/15" placeholder="Ej. Bebidas" value={nombre} onChange={e=>onNombreChange(e.target.value)}/>
      </div>
      {error && <p role="alert" className="rounded-lg bg-red-50 p-3 text-sm text-red-700">{error}</p>}
      <div className="flex justify-end gap-3 border-t border-brand-border pt-4">
        <button type="button" disabled={cargando} onClick={onCerrar} className="rounded-lg border border-brand-border bg-white px-4 py-2 text-sm font-semibold text-brand-ink hover:bg-brand-bg">Cancelar</button>
        <button type="submit" disabled={cargando} className="rounded-lg bg-brand-primary px-4 py-2 text-sm font-semibold text-white hover:bg-brand-primary-dark disabled:opacity-60">{cargando?"Guardando...":"Guardar categoría"}</button>
      </div>
    </form>
  );
}
export default CategoriaForm;
