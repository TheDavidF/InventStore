
import type { CategoriaResponse } from "../../types/categoria";

type CategoriasTableProps = {
  categorias: CategoriaResponse[];
  
};

function CategoriasTable({ categorias }: CategoriasTableProps) {
  return (
    <div className="overflow-hidden rounded-xl border border-gray-200 bg-white shadow-sm">
      <div className="border-b border-gray-200 px-6 py-4">
        <h2 className="text-lg font-semibold text-gray-800">
          Listado de categorías
        </h2>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full text-left text-sm">
          <thead className="bg-gray-50 text-xs uppercase text-gray-500">
            <tr>
              <th className="px-6 py-3">ID</th>
              <th className="px-6 py-3">Nombre</th>
              <th className="px-6 py-3">Estado</th>
            </tr>
          </thead>

          <tbody className="divide-y divide-gray-100">
            {categorias.length === 0 ? (
              <tr>
                <td
                  colSpan={3}
                  className="px-6 py-10 text-center text-gray-500"
                >
                  No hay categorías registradas.
                </td>
              </tr>
            ) : (
              categorias.map((categoria) => (
                <tr
                  key={categoria.id}
                  className="transition-colors hover:bg-gray-50"
                >
                  <td className="px-6 py-4 text-gray-500">
                    {categoria.id}
                  </td>

                  <td className="px-6 py-4 font-medium text-gray-900">
                    {categoria.nombre}
                  </td>

                  <td className="px-6 py-4">
                    <span
                      className={`inline-flex rounded-full px-3 py-1 text-xs font-medium ${
                        categoria.activa
                          ? "bg-green-100 text-green-700"
                          : "bg-red-100 text-red-700"
                      }`}
                    >
                      {categoria.activa ? "Activa" : "Inactiva"}
                    </span>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default CategoriasTable;
