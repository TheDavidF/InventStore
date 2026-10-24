import type { FormEvent } from "react";
import type {
    ProductoForm as ProductoFormData,
    ProductoResponse
} from "../../types/producto";

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

function ProductoForm({
    producto,
    onChange,
    onSubmit,
    onCancelar,
    productoEditando,
    categorias,
    cargando,
    error
}: ProductoFormProps) {

    return (
        <form onSubmit={onSubmit}>

            <div className="form-group">
                <label htmlFor="nombre-producto">
                    Nombre
                </label>

                <input
                    id="nombre-producto"
                    type="text"
                    placeholder="Ingrese el nombre del producto"
                    value={producto.nombre}
                    onChange={(event) => {
                        onChange({
                            ...producto,
                            nombre: event.target.value
                        });
                    }}
                />
            </div>

            <div className="form-group">
                <label htmlFor="precio-producto">
                    Precio
                </label>

                <input
                    min="0.01"
                    step="0.01"
                    required
                    id="precio-producto"
                    type="number"
                    placeholder="Ingrese el precio del producto"
                    value={producto.precio}
                    onChange={(event) => {
                        onChange({
                            ...producto,
                            precio: event.target.value
                        });
                    }}
                />
            </div>

            <div className="form-group">
                <label htmlFor="tipo-producto">
                    Tipo de producto
                </label>

                <select
                    id="tipo-producto"
                    value={producto.tipo}
                    disabled={productoEditando !== null}
                    onChange={(event) => {
                        onChange({
                            ...producto,
                            tipo: event.target.value
                        });
                    }}
                >
                    <option value="DIRECTO">Directo</option>
                    <option value="PREPARADO">Preparado</option>
                </select>
            </div>

            <div className="form-group">
                <label htmlFor="descripcion-producto">
                    Descripción
                </label>

                <input
                    id="descripcion-producto"
                    type="text"
                    placeholder="Ingrese la descripción del producto"
                    value={producto.descripcion}
                    onChange={(event) => {
                        onChange({
                            ...producto,
                            descripcion: event.target.value
                        });
                    }}
                />
            </div>

            <div className="form-group">
                <label htmlFor="categoria-producto">
                    Categoría
                </label>

                <select
                    id="categoria-producto"
                    value={producto.categoriaId ?? ""}
                    disabled={productoEditando !== null}
                    onChange={(event) => {
                        onChange({
                            ...producto,
                            categoriaId:
                                event.target.value === ""
                                    ? null
                                    : Number(event.target.value)
                        });
                    }}
                >
                    <option value="">
                        Seleccione una categoría
                    </option>

                    {categorias.map((categoria) => (
                        <option
                            key={categoria.id}
                            value={categoria.id}
                        >
                            {categoria.nombre}
                        </option>
                    ))}
                </select>
            </div>

            <div className="form-actions">
                <button
                    type="submit"
                    disabled={cargando}
                >
                    {cargando
                        ? "Guardando..."
                        : productoEditando === null
                            ? "Crear Producto"
                            : "Guardar Cambios"}
                </button>

                <button
                    type="button"
                    onClick={onCancelar}
                    disabled={cargando}
                >
                    Cancelar
                </button>

                {error !== "" && (
                    <p className="form-error">
                        {error}
                    </p>
                )}
            </div>

        </form>
    );
}

export default ProductoForm;