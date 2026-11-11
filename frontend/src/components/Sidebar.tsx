import { NavLink } from "react-router-dom";

const enlaces = [
  { to: "/productos", label: "Productos" },
  { to: "/categorias", label: "Categorías" }
];

function Sidebar() {
  return (
    <aside className="border-b border-brand-border bg-brand-ink text-brand-surface lg:min-h-screen lg:w-64 lg:shrink-0 lg:border-b-0 lg:border-r">
      <div className="flex items-center gap-3 border-b border-white/15 px-5 py-5">
        <img src="/logo-sazonH.png" alt="Sazón Hogareño" className="h-12 w-12 rounded-lg bg-brand-surface object-contain p-1" />
        <div>
          <p className="font-serif text-lg leading-tight">Sazón Hogareño</p>
          <p className="text-xs text-white/60">InventStore · Administración</p>
        </div>
      </div>
      <nav aria-label="Navegación principal" className="flex gap-2 overflow-x-auto px-3 py-3 lg:flex-col lg:px-4 lg:py-6">
        {enlaces.map(({to,label}) => (
          <NavLink
            key={to}
            to={to}
            className={({isActive}) =>
              `whitespace-nowrap rounded-lg px-4 py-3 text-sm font-medium transition-colors ${isActive ? "bg-brand-primary text-white shadow-sm" : "text-white/75 hover:bg-white/10 hover:text-white"}`
            }
          >
            {label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}
export default Sidebar;
