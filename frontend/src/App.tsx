import {useState} from "react";



function App() {
    const [productos, setProductos] = useState([
        { id: 1, nombre: "Hamburguesa", precio: 25 },
        { id: 2, nombre: "Papas", precio: 15 },
        { id: 3, nombre: "CocaCola", precio: 6 }
    ]);

    const nuevoProducto ={
        id:4,
        nombre:"Hot Dog",
        precio:20
    };

    setProductos((anteriores) =>[
        ...anteriores,
        nuevoProducto
    ]);

    const eliminarProducto = (id: number) => {
        setProductos((anteriores) =>
            anteriores.filter((producto) => producto.id !== id)
        );
    };

    return (
        <div>
            <h1>InventStore</h1>
            {
                productos.map(
                    (producto) => {
                        return  (
                            <Producto
                                key={producto.id}
                                nombre={producto.nombre}
                                precio={producto.precio}
                            />
                        );
                    }
                )
            }
        </div>
    );
}

function Producto({nombre, precio}: ProductoProps){
    const [cantidad, setCantidad] = useState(1);

    const agregar = () => {
        setCantidad((anterior) => anterior + 1);
    };

    const restar = () => {
        setCantidad((anterior) => {
            if (anterior > 0) {
                return anterior - 1;
            }

            return anterior;
        });
    };

  return(
      <div>
        <h2>{nombre}</h2>
        <h2>precio:{precio}</h2>
        <h2>cantidad:{cantidad}</h2>
        <h2>Subtotal:{cantidad*precio}</h2>
        <button onClick={restar}>-</button>
        <button onClick={agregar}>+</button>
      </div>
  )


}


type ProductoProps = {
  nombre: string
  precio: number
}
export default App