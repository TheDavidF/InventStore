import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();
  const correo = sessionStorage.getItem("correo");
  const rol = sessionStorage.getItem("rol");
  const cerrarSesion = () => {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("correo");
    sessionStorage.removeItem("rol");
    navigate("/login");
  };
  return (
    <header className="flex flex-wrap items-center justify-between gap-3 border-b border-brand-border bg-brand-surface px-4 py-3 sm:px-6 lg:px-8">
      <div>
        <p className="font-serif text-lg text-brand-ink">Panel de administración</p>
        <p className="text-xs text-brand-muted">Sazón Hogareño</p>
      </div>
      <div className="flex items-center gap-3">
        <div className="max-w-48 text-right sm:max-w-none">
          <p className="truncate text-sm font-medium text-brand-ink">{correo || "Usuario"}</p>
          <p className="text-xs text-brand-muted">{rol || "Cuenta"}</p>
        </div>
        <button type="button" onClick={cerrarSesion} className="rounded-lg border border-brand-border bg-white px-3 py-2 text-xs font-semibold text-brand-primary transition hover:bg-brand-bg focus-visible:outline-2 focus-visible:outline-brand-primary sm:text-sm">
          Salir
        </button>
      </div>
    </header>
  );
}
export default Navbar;
