import { useEffect, useState } from "react";
import type { ChangeEvent, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import "./ProductosPage.css";
import ProductosTable from "../components/productos/ProductosTable";
import ProductoFormComponent from "../components/productos/ProductoForm";
import { productosService } from "../services/productoService";
import { ApiError } from "../api/client";
import { categoriasService } from "../services/categoriaService";

import type {
    ProductoResponse,
    ProductoForm,
} from "../types/producto";

import type {
    CategoriaResponse,

} from "../types/categoria";



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


    const manejarError = (error: unknown) => {
        if (error instanceof ApiError) {
            if (error.status === 401) {
                sessionStorage.removeItem("token");
                sessionStorage.removeItem("correo");
                sessionStorage.removeItem("rol");

                navigate("/login");
                return;
            }

            setErrorFormulario(error.message);
            return;
        }

        setErrorFormulario("Ocurrió un error inesperado");
    };

    const abrirEdicion = (producto: ProductoResponse) => {
        setErrorFormulario("");
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
        try {
            const datos = await categoriasService.obtenerTodos();
            setCategorias(datos);
        } catch (error) {
            manejarError(error);
        }
    };

    const desactivaProducto = async (producto: ProductoResponse) => {
        setErrorFormulario("");

        try {
            const productoActualizado =
                await productosService.desactivar(producto.id);

            setProductos((anteriores) =>
                anteriores.map((actual) =>
                    actual.id === productoActualizado.id
                        ? productoActualizado
                        : actual
                )
            );
        } catch (error) {
            manejarError(error);
        }
    };

    const activarProducto = async (producto: ProductoResponse) => {
        setErrorFormulario("");

        try {
            const productoActualizado =
                await productosService.reactivar(producto.id);

            setProductos((anteriores) =>
                anteriores.map((actual) =>
                    actual.id === productoActualizado.id
                        ? productoActualizado
                        : actual
                )
            );
        } catch (error) {
            manejarError(error);
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

        const token = sessionStorage.getItem("token");

        if (token === null) {
            navigate("/login");
            return;
        }

        setCargando(true);

        try {

            const productoActualizado = await productosService.actualizar(
                productoEditando.id,
                {
                    nombre: nuevoProducto.nombre.trim(),
                    descripcion: nuevoProducto.descripcion.trim(),
                    precio: Number(nuevoProducto.precio)
                }
            );

            setProductos((anteriores) =>
                anteriores.map((producto) =>
                    producto.id === productoActualizado.id
                        ? productoActualizado
                        : producto
                )
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
            manejarError(error);
        } finally {
            setCargando(false);
        }
    };

    const cargarProductos = async () => {
        try {
            const datos = await productosService.obtenerTodos();

            console.log("Productos recibidos:", datos);
            console.log("¿Es un arreglo?", Array.isArray(datos));
            setProductos(datos);
        } catch (error) {
            manejarError(error);
        }
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

        const token = sessionStorage.getItem("token");

        if (token === null) {
            navigate("/login");
            return;
        }

        setCargando(true);

        try {
            const productoCreado = await productosService.crear({
                nombre: nuevoProducto.nombre.trim(),
                descripcion: nuevoProducto.descripcion.trim(),
                precio: Number(nuevoProducto.precio),
                tipo: nuevoProducto.tipo,
                categoriaId: nuevoProducto.categoriaId!
            });

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
            manejarError(error);
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

                        <ProductoFormComponent
                            producto={nuevoProducto}
                            categorias={categorias}
                            productoEditando={productoEditando}
                            cargando={cargando}
                            error={errorFormulario}
                            onChange={setNuevoProducto}
                            onSubmit={
                                productoEditando === null
                                    ? crearProducto
                                    : actualizarProducto
                            }
                            onCancelar={() => {
                                setModalAbierto(false);
                                setProductoEditando(null);
                            }}
                        />
                    </div>
                </div>
            )}

            <ProductosTable
                productos={productosFiltrados}
                onEditar={abrirEdicion}
                onDesactivar={desactivaProducto}
                onActivar={activarProducto}
            />
        </section>
    );
}

export default ProductosPage;
