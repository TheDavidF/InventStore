import {useEffect, useState} from "react";
import type {ChangeEvent} from "react";
import {useNavigate} from "react-router-dom"
import "./ProductosPage.css"

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

function ProductosPage() {

    const [productos, setProductos] = useState<ProductoResponse[]>([]);
    const navigate = useNavigate();
    const [textoBusqueda, setTextoBusqueda] = useState("");

    const buscarProducto = (event: ChangeEvent<HTMLInputElement>) => {
        setTextoBusqueda(event.target.value);
    }

    const cargarProductos = async () => {
        const token = sessionStorage.getItem("token");


        if (token === null) {
            console.log("No hay token");
            return;
        }

        const respuesta = await fetch("http://localhost:8080/api/productos", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        if (respuesta.status === 401) {
            sessionStorage.removeItem("token");
            sessionStorage.removeItem("correo");
            sessionStorage.removeItem("rol");

            navigate("/login");
            return;
        }

        const datos: ProductoResponse[] = await respuesta.json();

        setProductos(datos);
    };

    const productosFiltrados: ProductoResponse[] = productos.filter((producto) =>
        producto.nombre
            .toLowerCase()
            .includes(textoBusqueda.toLowerCase()));

    useEffect(() => {
        cargarProductos();
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


            <div className="table-container">
                <table className="productos-table">
                    <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Categoria</th>
                        <th>Tipo</th>
                        <th>Precio</th>
                        <th>Estado</th>
                    </tr>
                    </thead>
                    <tbody>

                    {productosFiltrados.length === 0 &&
                        <tr>
                            <td colSpan={5} > Producto no encontrado</td>
                        </tr>

                    }

                    {productosFiltrados.map((producto) => (
                        <tr key={producto.id}>
                            <td>{producto.nombre}</td>
                            <td>{producto.categoriaNombre}</td>
                            <td>{producto.tipo}</td>
                            <td>Q{producto.precio.toFixed(2)}</td>
                            <td>{producto.activo ? "Activo" : "Inactivo"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

        </section>
    );
}

export default ProductosPage;