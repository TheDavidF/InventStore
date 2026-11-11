import { useEffect, useState } from "react";
import type { ChangeEvent, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import PageHeader from "../components/ui/PageHeader";
import Modal from "../components/ui/Modal";
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
        <section>
            <PageHeader titulo="Productos" descripcion="Gestiona el menú y los productos de Sazón Hogareño." accion={
                <button type="button" onClick={abrirCreacion} className="rounded-lg bg-brand-primary px-5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-brand-primary-dark">+ Nuevo producto</button>
            }/>
            <div className="mb-5 rounded-xl border border-brand-border bg-brand-surface p-4 shadow-sm">
                <label htmlFor="buscar-producto" className="mb-2 block text-sm font-semibold text-brand-ink">Buscar productos</label>
                <input id="buscar-producto" type="search" className="w-full rounded-lg border border-brand-border bg-white px-4 py-2.5 text-sm text-brand-ink outline-none focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/15 sm:max-w-sm" placeholder="Buscar por nombre..." value={textoBusqueda} onChange={buscarProducto}/>
            </div>
            {!modalAbierto && errorFormulario && <p role="alert" className="mb-4 rounded-lg bg-red-50 p-3 text-sm text-red-700">{errorFormulario}</p>}
            <ProductosTable productos={productosFiltrados} onEditar={abrirEdicion} onDesactivar={desactivaProducto} onActivar={activarProducto}/>
            {modalAbierto && (
                <Modal titulo={productoEditando===null?"Nuevo producto":"Editar producto"} descripcion="Completa los datos del producto." onCerrar={()=>{if(!cargando){setModalAbierto(false);setProductoEditando(null);}}}>
                    <ProductoFormComponent producto={nuevoProducto} categorias={categorias} productoEditando={productoEditando} cargando={cargando} error={errorFormulario} onChange={setNuevoProducto} onSubmit={productoEditando===null?crearProducto:actualizarProducto} onCancelar={()=>{setModalAbierto(false);setProductoEditando(null);}}/>
                </Modal>
            )}
        </section>
    );
}
export default ProductosPage;
