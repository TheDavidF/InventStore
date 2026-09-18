import {NavLink} from "react-router-dom";
import "./Sidebar.css";



function Sidebar() {
    return (
        <aside className="sidebar">
            <h2 className="sidebar-logo">InventStore</h2>

            <nav>
                <ul className="sidebar-menu">
                    <li href="#">Dashboard</li>
                    <li href="#">Órdenes</li>
                    <li href="#">Caja</li>
                    <li>
                        <NavLink
                            to="/productos"
                            className={({ isActive }) =>
                                isActive ? "nav-link active" : "nav-link"
                            }
                        >
                            Productos
                        </NavLink>
                    </li>
                    <li href="#">Inventario</li>
                    <li href="#">Compras</li>
                    <li href="#">Proveedores</li>
                    <li href="#">Reportes</li>
                    <li href="#">Usuarios</li>
                </ul>
            </nav>
        </aside>
    );
}

export default Sidebar;