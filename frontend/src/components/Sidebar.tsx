import { NavLink } from "react-router-dom";
import { Link } from "react-router-dom";
import "./Sidebar.css";

function Sidebar() {
  return (
    <aside className="sidebar">
      <h2 className="sidebar-logo">InventStore</h2>

      <nav>
        <ul className="sidebar-menu">
          <li>
            <Link to="/dashboard">Dashboard</Link>
          </li>

          <li>
            <Link to="/ordenes">Órdenes</Link>
          </li>

          <li>
            <Link to="/caja">Caja</Link>
          </li>
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
          <li>
            <Link to="/categorias">Categorias</Link>
          </li>

          <li>
            <Link to="/inventario">Inventario</Link>
          </li>
          <li>
            <Link to="/Compras">Compras</Link>
          </li>
          <li>
            <Link to="/Proveedores">Proveedores</Link>
          </li>
          <li>
            <Link to="/Reportes">Reportes</Link>
          </li>
          <li>
            <Link to="/Usuarios">Usuarios</Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
}

export default Sidebar;
