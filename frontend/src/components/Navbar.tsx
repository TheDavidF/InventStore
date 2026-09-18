import { useNavigate } from "react-router-dom";
import "./Navbar.css";


function Navbar() {
    const correo = sessionStorage.getItem("correo");
    const rol = sessionStorage.getItem("rol");
    const navigate = useNavigate();

    const logout = ()=>{
        sessionStorage.clear();
        navigate("/login")
    }


    return (
        <header className="navbar">
            <div className="navbar-user">
                <span>{correo}</span>
                <span className="navbar-role">{rol}</span>
            </div>

            <button type="button" className="logout-button" onClick={logout}>
                Cerrar sesión
            </button>
        </header>
    );
}

export default Navbar;