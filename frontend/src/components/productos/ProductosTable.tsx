import type { ProductoResponse } from "../../types/producto";

type ProductosTableProps = {
    productos: ProductoResponse[];
    onEditar: (producto: ProductoResponse) => void;
    onDesactivar: (producto: ProductoResponse) => void;
    onActivar: (producto: ProductoResponse) => void;
};

function ProductosTable({
    productos,
    onEditar,
    onDesactivar,
    onActivar
}: ProductosTableProps) {
    return (
        <div className="table-container">
            <table className="productos-table">
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Categoría</th>
                        <th>Tipo</th>
                        <th>Precio</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>
                    {productos.length === 0 && (
                        <tr>
                            <td colSpan={6}>Producto no encontrado</td>
                        </tr>
                    )}

                    {productos.map((producto) => (
                        <tr key={producto.id}>
                            <td>{producto.nombre}</td>
                            <td>{producto.categoriaNombre}</td>
                            <td>{producto.tipo}</td>
                            <td>Q{producto.precio.toFixed(2)}</td>
                            <td>
                                {producto.activo ? "Activo" : "Inactivo"}
                            </td>

                            <td className="p-2">
                                <div className="flex items-center gap-2">
                                    <button
                                        className="h-8 rounded-lg border bg-[#22ce5b] px-3 text-sm text-white transition-colors hover:bg-[#1eb952]"
                                        type="button"
                                        onClick={() => onEditar(producto)}
                                    >
                                        Editar
                                    </button>

                                    {producto.activo ? (
                                        <button
                                            className="h-8 rounded-lg border bg-[#0f7dfa] px-3 text-sm text-white transition-colors hover:bg-[#0c6cd8]"
                                            type="button"
                                            onClick={() => onDesactivar(producto)}
                                        >
                                            Desactivar
                                        </button>
                                    ) : (
                                        <button
                                            className="h-8 rounded-lg border bg-[#0f7dfa] px-3 text-sm text-white transition-colors hover:bg-[#0c6cd8]"
                                            type="button"
                                            onClick={() => onActivar(producto)}
                                        >
                                            Activar
                                        </button>
                                    )}
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ProductosTable;