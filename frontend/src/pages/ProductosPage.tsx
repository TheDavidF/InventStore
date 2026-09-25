import { useEffect, useState } from "react";
import type { ChangeEvent, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import "./ProductosPage.css";

type ProductoResponse = {
    id: number;
    nombre: string;
    descripcion: string;
    precio: number;
    tipo: string;
    activo: boolean;
    categoriaId: number;
    categoriaNombre: string;
};

type ProductoForm = {
    nombre: string;
    descripcion: string;
    precio: string;
    tipo: string;
    categoriaId: number | null;
};

type CategoriaResponse = {
    activa: boolean;
    id: number;
    nombre: string;
};

type ErrorResponse = {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    path: string;
};

function ProductosPage() {
    const [productos, setProductos] = useState<ProductoResponse[]>([]);
    const navigate = useNavigate();
    const [textoBusqueda, setTextoBusqueda] = useState("");
    const [modalAbierto, setModalAbierto] = useState(false);
    const [categorias, setCategorias] = useState<CategoriaResponse[]>([]);
    const [errorFormulario, setErrorFormulario] = useState("");
    const [cargando, setCargando] = useState(false);
    const [productoEditando, setProductoEditando] =
        useState<ProductoResponse | null>(null);
    const [nuevoProducto, setNuevoProducto] = useState<ProductoForm>({
        nombre: "",
        descripcion: "",
        precio: "",
        tipo: "DIRECTO",
        categoriaId: null,
    });

    const buscarProducto = (event: ChangeEvent<HTMLInputElement>) => {
        setTextoBusqueda(event.target.value);
    };

    const abrirEdicion = (producto: ProductoResponse) => {
        setProductoEditando(producto);
        setNuevoProducto({
            nombre: producto.nombre,
            descripcion: producto.descripcion,
            precio: String(producto.precio),
            tipo: producto.tipo,
            categoriaId: producto.categoriaId,
        });

        setModalAbierto(true);
    };

    const abrirCreacion = () => {
        setProductoEditando(null);

        setNuevoProducto({
            nombre: "",
            descripcion: "",
            precio: "",
            tipo: "DIRECTO",
            categoriaId: null,
        });

        setErrorFormulario("");
        setModalAbierto(true);
    };

    const cargarCategorias = async () => {
        const token = sessionStorage.getItem("token");

        if (token === null) {
            console.log("No hay token");
            return;
        }

        const respuesta = await fetch("http://localhost:8080/api/categorias", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        if (respuesta.status === 401) {
            sessionStorage.removeItem("token");
            sessionStorage.removeItem("correo");
            sessionStorage.removeItem("rol");
            navigate("/login");
            return;
        }

        const datos: CategoriaResponse[] = await respuesta.json();
        setCategorias(datos);
    };

    const desactivaProducto = async (producto: ProductoResponse) => {
        const token = sessionStorage.getItem("token");
        setErrorFormulario("");
        if (token === null) {
            navigate("/login");
            return;
        }

        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/productos/${producto.id}/desactivar`,
                {
                    method: "PATCH",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                },
            );

            if (respuesta.status === 401) {
                sessionStorage.removeItem("token");
                sessionStorage.removeItem("correo");
                sessionStorage.removeItem("rol");

                navigate("/login");
                return;
            }

            if (!respuesta.ok) {
                const error: ErrorResponse = await respuesta.json();

                setErrorFormulario(error.message);
                return;
            }

            const productoActualizado: ProductoResponse =
                await respuesta.json();

            setProductos((anteriores) =>
                anteriores.map((producto) =>
                    producto.id === productoActualizado.id
                        ? productoActualizado
                        : producto
                )
            );
        } catch {
            setErrorFormulario("No se pudo conectar con el servidor");
        }
    };

    const activarProducto = async (producto: ProductoResponse) => {
        const token = sessionStorage.getItem("token");
        setErrorFormulario("");

        if (token === null) {
            navigate("/login");
            return;
        }

        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/productos/${producto.id}/reactivar`,
                {
                    method: "PATCH",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                },
            );

            if (respuesta.status === 401) {
                sessionStorage.removeItem("token");
                sessionStorage.removeItem("correo");
                sessionStorage.removeItem("rol");

                navigate("/login");
                return;
            }

            if (!respuesta.ok) {
                const error: ErrorResponse = await respuesta.json();
                setErrorFormulario(error.message);
                return;
            }

            const productoActualizado: ProductoResponse =
                await respuesta.json();

            setProductos((anteriores) =>
                anteriores.map((producto) =>
                    producto.id === productoActualizado.id
                        ? productoActualizado
                        : producto
                )
            );
        } catch {
            
            setErrorFormulario("No se pudo conectar con el servidor");
        }
    };

    const actualizarProducto = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setErrorFormulario("");

        if (productoEditando === null) {
            return;
        }

        if (nuevoProducto.nombre.trim() === "") {
            setErrorFormulario("El nombre es obligatorio");
            return;
        }

        if (nuevoProducto.precio === "") {
            setErrorFormulario("El precio es obligatorio");
            return;
        }

        const precio = Number(nuevoProducto.precio);

        if (Number.isNaN(precio) || precio <= 0) {
            setErrorFormulario("El precio debe ser mayor a cero");
            return;
        }

        const request = {
            nombre: nuevoProducto.nombre.trim(),
            descripcion: nuevoProducto.descripcion.trim(),
            precio: precio,
        };

        const token = sessionStorage.getItem("token");

        if (token === null) {
            navigate("/login");
            return;
        }

        setCargando(true);

        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/productos/${productoEditando.id}`,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(request),
                },
            );

            if (respuesta.status === 401) {
                sessionStorage.removeItem("token");
                sessionStorage.removeItem("correo");
                sessionStorage.removeItem("rol");

                navigate("/login");
                return;
            }

            if (!respuesta.ok) {
                const error: ErrorResponse = await respuesta.json();

                setErrorFormulario(error.message);
                return;
            }

            const productoActualizado: ProductoResponse = await respuesta.json();

            setProductos((anteriores) =>
                anteriores.map((producto) =>
                    producto.id === productoActualizado.id
                        ? productoActualizado
                        : producto,
                ),
            );

            setProductoEditando(null);

            setNuevoProducto({
                nombre: "",
                descripcion: "",
                precio: "",
                tipo: "DIRECTO",
                categoriaId: null,
            });

            setModalAbierto(false);
        } catch (error) {
            setErrorFormulario("No se pudo conectar con el servidor");
        } finally {
            setCargando(false);
        }
    };

    const cargarProductos = async () => {
        const token = sessionStorage.getItem("token");

        if (token === null) {
            console.log("No hay token");
            return;
        }

        const respuesta = await fetch("http://localhost:8080/api/productos", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        if (respuesta.status === 401) {
            sessionStorage.removeItem("token");
            sessionStorage.removeItem("correo");
            sessionStorage.removeItem("rol");

            navigate("/login");
            return;
        }

        if (!respuesta.ok) {
            const error: ErrorResponse = await respuesta.json();
            setErrorFormulario(error.message);
            return;
        }

        const datos: ProductoResponse[] = await respuesta.json();
        setProductos(datos);
    };

    const productosFiltrados: ProductoResponse[] = productos.filter((producto) =>
        producto.nombre.toLowerCase().includes(textoBusqueda.toLowerCase()),
    );

    const crearProducto = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setErrorFormulario("");

        if (nuevoProducto.nombre.trim() === "") {
            setErrorFormulario("El nombre es obligatorio");
            return;
        }

        if (nuevoProducto.precio === "") {
            setErrorFormulario("El precio es obligatorio");
            return;
        }

        if (nuevoProducto.categoriaId === null) {
            setErrorFormulario("Debe seleccionar una categoria");
            return;
        }

        const precio = Number(nuevoProducto.precio);

        if (Number.isNaN(precio) || precio <= 0) {
            setErrorFormulario("El precio debe ser mayor a cero");
            return;
        }

        const request = {
            nombre: nuevoProducto.nombre.trim(),
            descripcion: nuevoProducto.descripcion.trim(),
            precio: precio,
            tipo: nuevoProducto.tipo,
            categoriaId: nuevoProducto.categoriaId,
        };

        const token = sessionStorage.getItem("token");

        if (token === null) {
            navigate("/login");
            return;
        }

        setCargando(true);

        try {
            const respuesta = await fetch("http://localhost:8080/api/productos", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(request),
            });

            if (respuesta.status === 401) {
                sessionStorage.removeItem("token");
                sessionStorage.removeItem("correo");
                sessionStorage.removeItem("rol");

                navigate("/login");
                return;
            }

            if (!respuesta.ok) {
                const error: ErrorResponse = await respuesta.json();

                setErrorFormulario(error.message);
                return;
            }

            const productoCreado: ProductoResponse = await respuesta.json();

            setProductos((anteriores) => [...anteriores, productoCreado]);

            setNuevoProducto({
                nombre: "",
                descripcion: "",
                precio: "",
                tipo: "DIRECTO",
                categoriaId: null,
            });

            setModalAbierto(false);
        } catch (error) {
            setErrorFormulario("No se pudo conectar con el servidor");
        } finally {
            setCargando(false);
        }
    };

    useEffect(() => {
        cargarProductos();
        cargarCategorias();
    }, []);

    return (
        <section className="productos-page">
            <h1>Productos</h1>

            <div className="busqueda-input">
                <label htmlFor="buscar-producto">Buscar Producto</label>
                <input
                    id="buscar-producto"
                    type="text"
                    placeholder="Buscar por nombre..."
                    value={textoBusqueda}
                    onChange={buscarProducto}
                />
            </div>

            <div className="button-modal">
                <button type="button" onClick={abrirCreacion}>
                    Nuevo producto
                </button>
            </div>

            {modalAbierto && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h2>
                            {productoEditando === null ? "Nuevo Producto" : "Editar Producto"}
                        </h2>

                        <form
                            onSubmit={
                                productoEditando === null ? crearProducto : actualizarProducto
                            }
                        >
                            <div className="form-group">
                                <label htmlFor="nombre-producto">Nombre</label>
                                <input
                                    id="nombre-producto"
                                    type="text"
                                    placeholder="Ingrese el nombre del producto"
                                    value={nuevoProducto.nombre}
                                    onChange={(event) => {
                                        setNuevoProducto({
                                            ...nuevoProducto,
                                            nombre: event.target.value,
                                        });
                                    }}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="precio-producto">Precio</label>
                                <input
                                    id="precio-producto"
                                    type="number"
                                    placeholder="Ingrese el precio del producto"
                                    value={nuevoProducto.precio}
                                    onChange={(event) => {
                                        setNuevoProducto({
                                            ...nuevoProducto,
                                            precio: event.target.value,
                                        });
                                    }}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="tipo-producto">
                                    Seleccione el tipo de producto
                                </label>
                                <select
                                    id="tipo-producto"
                                    value={nuevoProducto.tipo}
                                    disabled={productoEditando !== null}
                                    onChange={(event) => {
                                        setNuevoProducto({
                                            ...nuevoProducto,
                                            tipo: event.target.value,
                                        });
                                    }}
                                >
                                    <option value="DIRECTO">Directo</option>
                                    <option value="PREPARADO">Preparado</option>
                                </select>
                            </div>

                            <div className="form-group">
                                <label htmlFor="descripcion-producto">
                                    Ingrese la desripcion
                                </label>
                                <input
                                    id="descripcion-producto"
                                    type="text"
                                    placeholder="Ingrese la descripcion del producto"
                                    value={nuevoProducto.descripcion}
                                    onChange={(event) => {
                                        setNuevoProducto({
                                            ...nuevoProducto,
                                            descripcion: event.target.value,
                                        });
                                    }}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="categoria-producto">Categoría</label>
                                <select
                                    id="categoria-producto"
                                    value={nuevoProducto.categoriaId ?? ""}
                                    disabled={productoEditando !== null}
                                    onChange={(event) => {
                                        setNuevoProducto({
                                            ...nuevoProducto,
                                            categoriaId:
                                                event.target.value === ""
                                                    ? null
                                                    : Number(event.target.value),
                                        });
                                    }}
                                >
                                    <option value="">Seleccione una Categoria</option>
                                    {categorias.map((categoria) => (
                                        <option key={categoria.id} value={categoria.id}>
                                            {categoria.nombre}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="form-actions">
                                <button type="submit" disabled={cargando}>
                                    {cargando
                                        ? "Guardando..."
                                        : productoEditando === null
                                            ? "Crear Producto"
                                            : "Guardar Cambios"}
                                </button>

                                <button
                                    type="button"
                                    onClick={() => {
                                        setModalAbierto(false);
                                        setProductoEditando(null);
                                    }}
                                >
                                    Cancelar
                                </button>
                                {errorFormulario !== "" && (
                                    <p className="form-error"> {errorFormulario}</p>
                                )}
                            </div>
                        </form>
                    </div>
                </div>
            )}

            <div className="table-container">
                <table className="productos-table">
                    <thead>
                        <tr>
                            <th>Producto</th>
                            <th>Categoria</th>
                            <th>Tipo</th>
                            <th>Precio</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {productosFiltrados.length === 0 && (
                            <tr>
                                <td colSpan={6}> Producto no encontrado</td>
                            </tr>
                        )}

                        {productosFiltrados.map((producto) => (
                            <tr key={producto.id}>
                                <td>{producto.nombre}</td>
                                <td>{producto.categoriaNombre}</td>
                                <td>{producto.tipo}</td>
                                <td>Q{producto.precio.toFixed(2)}</td>
                                <td>{producto.activo ? "Activo" : "Inactivo"}</td>
                                <td>
                                    <button
                                        type="button"
                                        onClick={() => {
                                            abrirEdicion(producto);
                                        }}
                                    >
                                        Editar
                                    </button>

                                    {producto.activo ? (
                                        <button
                                            type="button"
                                            onClick={() => desactivaProducto(producto)}
                                        >
                                            Desactivar
                                        </button>
                                    ) : (
                                        <button
                                            type="button"
                                            onClick={() => activarProducto(producto)}
                                        >
                                            Activar
                                        </button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </section>
    );
}

export default ProductosPage;
