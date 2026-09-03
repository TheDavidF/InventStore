import {useState} from "react";

function App() {

    const [correo, setCorreo] = useState("");
    const [password, setPassword] = useState("");
    const [productos, setProductos] = useState<ProductoResponse[]>([]);

    const cambiarPassword =(event) =>{
        setPassword(event.target.value)
    };

    const cambiarCorreo = (event) => {
        setCorreo(event.target.value);
    };

    const iniciarSesion = async (event) => {
        event.preventDefault();

        const respuesta = await fetch("http://localhost:8080/api/auth/login", {
           method: "POST",
            headers:{
               "Content-Type": "application/json"
            },
            body: JSON.stringify({
                correo: correo,
                contrasena: password
            })
        });

        const datos:LoginResponse = await respuesta.json();
        sessionStorage.setItem("token", datos.token);
        sessionStorage.setItem("correo", datos.correo);
        sessionStorage.setItem("rol", datos.rol);
        console.log(datos);
    };

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

        const datos: ProductoResponse[] = await respuesta.json();

        setProductos(datos);
    };

    return (
        <main>
            <h1>InventStore</h1>

            <form onSubmit={iniciarSesion}>
                <div>
                    <label>Correo</label>
                    <input type="email"
                           value={correo}
                           onChange={cambiarCorreo} />
                    <p>Correo actual: {correo}</p>
                </div>

                <div>
                    <label>Contraseña</label>
                    <input type="password"
                            value={password}
                            onChange={cambiarPassword}/>
                </div>

                <button type="submit">
                    Iniciar sesión

                </button>


                <button type="button" onClick={cargarProductos}>
                    Cargar productos
                </button>
            </form>
            <section>
                <h2>Productos</h2>

                {productos.map((producto) => (
                    <div key={producto.id}>
                        <h3>{producto.nombre}</h3>
                        <p>{producto.descripcion}</p>
                        <p>Precio: Q{producto.precio}</p>
                        <p>Categoría: {producto.categoriaNombre}</p>
                        <p>Tipo: {producto.tipo}</p>
                    </div>
                ))}
            </section>
        </main>
    );


}

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

type LoginResponse = {
    token: string;
    tipo: string;
    expiraEn: number;
    correo: string;
    rol: string;
};

export default App