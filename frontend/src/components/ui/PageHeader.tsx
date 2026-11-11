import type { ReactNode } from "react";

type PageHeaderProps = { titulo: string; descripcion: string; accion?: ReactNode };
function PageHeader({titulo, descripcion, accion}: PageHeaderProps) {
  return (
    <div className="mb-6 flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
      <div>
        <h1 className="font-serif text-3xl font-medium text-brand-ink">{titulo}</h1>
        <p className="mt-1 text-sm text-brand-muted">{descripcion}</p>
      </div>
      {accion}
    </div>
  );
}
export default PageHeader;
