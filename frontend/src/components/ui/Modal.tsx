import type { ReactNode } from "react";
type ModalProps = { titulo: string; descripcion?: string; children: ReactNode; onCerrar: () => void; };
function Modal({titulo, descripcion, children, onCerrar}: ModalProps) {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center overflow-y-auto bg-brand-ink/65 p-4" role="presentation">
      <div role="dialog" aria-modal="true" aria-labelledby="modal-titulo" className="my-auto w-full max-w-lg overflow-hidden rounded-2xl border border-brand-border bg-brand-surface shadow-2xl">
        <div className="flex items-start justify-between gap-4 border-b border-brand-border px-6 py-5">
          <div>
            <h2 id="modal-titulo" className="font-serif text-2xl text-brand-ink">{titulo}</h2>
            {descripcion && <p className="mt-1 text-sm text-brand-muted">{descripcion}</p>}
          </div>
          <button type="button" onClick={onCerrar} aria-label="Cerrar" className="rounded-lg px-2 py-1 text-xl text-brand-muted hover:bg-brand-bg hover:text-brand-ink">×</button>
        </div>
        {children}
      </div>
    </div>
  );
}
export default Modal;
