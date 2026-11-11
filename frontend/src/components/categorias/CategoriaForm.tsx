
type CategoriaFormProps = {
    nombre: string;
    cargando: boolean;
    onGuardar: () => Promise<void>;
    onCerrar: () => void;
    onNombreChange: (valor: string) => void;
    error: string;
}

function CategoriaForm({
    cargando,
    onCerrar,
    nombre,
    onNombreChange,
    onGuardar,
    error
}: CategoriaFormProps) {
    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
            <div className="w-full max-w-md rounded-xl bg-white shadow-xl">

                {/* Encabezado */}
                <div className="flex items-start justify-between border-b border-gray-200 px-6 py-4">
                    <div>
                        <h2 className="text-lg font-semibold text-gray-900">
                            Nueva categoría
                        </h2>
                        <p className="mt-1 text-sm text-gray-500">
                            Registra una nueva categoría de productos.
                        </p>
                    </div>

                    <button
                        type="button"
                        aria-label="Cerrar formulario"
                        onClick={onCerrar}
                        className="rounded-lg px-2 text-xl text-gray-400 hover:bg-gray-100 hover:text-gray-700"
                    >
                        ×
                    </button>
                </div>

                {/* Formulario */}
                <form className="space-y-5 px-6 py-5"
                    onSubmit={(event) => {
                        event.preventDefault();
                        void onGuardar();
                    }}
                >
                    <div>
                        <label
                            htmlFor="nombreCategoria"
                            className="mb-2 block text-sm font-medium text-gray-700"
                        >
                            Nombre de la categoría
                        </label>

                        <input
                            id="nombreCategoria"
                            type="text"
                            placeholder="Ej. Bebidas"
                            required
                            value={nombre}
                            onChange={(event) => onNombreChange(event.target.value)}
                            className="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm outline-none transition focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100"
                        />

                        {error && (
                            <p role="alert" className="mt-2 text-sm text-red-600">
                                {error}
                            </p>
                        )}
                    </div>

                    {/* Botones */}
                    <div className="flex justify-end gap-3 border-t border-gray-100 pt-4">
                        <button
                            type="button"
                            onClick={onCerrar}
                            className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
                        >
                            Cancelar
                        </button>

                        <button
                            type="submit"
                            className="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700"
                            disabled={cargando}
                        >
                            Guardar categoría
                        </button>
                    </div>
                </form>

            </div>
        </div>
    );
}

export default CategoriaForm;
