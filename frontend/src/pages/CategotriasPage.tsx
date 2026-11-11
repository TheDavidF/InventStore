
import { useEffect, useState } from "react";
import type { CategoriaResponse } from "../types/categoria";
import { categoriasService } from "../services/categoriaService";
import CategoriasTable from "../components/categorias/CategoriasTable";
import CategoriaForm from "../components/categorias/CategoriaForm";

function CategoriasPage() {
  // Estados del componente
  const [categorias, setCategorias] = useState<CategoriaResponse[]>([]);
  const [nombre, setNombre] = useState("");
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState("");
  const [modalAbierto, setModalAbierto] = useState(false);

  // Obtener categorías desde la API
  const cargarCategorias = async () => {
    try {
      const datos = await categoriasService.obtenerTodos();
      setCategorias(datos);
    } catch (error) {
      console.error("Error al cargar categorías:", error);
      setError("No se pudieron cargar las categorías");
    }
  };


  const guardarCategoria = async () => {

    const nombreLimpio = nombre.trim();

    if (!nombreLimpio) {
      setError("El nombre de la categoria es obligatorio");
      return;
    }

    setCargando(true);
    setError("");

    try {
      const nuevaCategoria = await categoriasService.crear({
        nombre: nombreLimpio
      });

      setCategorias((anteriores) => [
        ...anteriores,
        nuevaCategoria
      ]);

      setNombre("");
      setModalAbierto(false);
    } catch (error) {
      console.error("Error al crear la categoria:", error);
      setError("No se pudo registrar la categoria");

    } finally {
      setCargando(false);
    }
  };


  const cerrarModal = () => {
    setModalAbierto(false);
    setNombre("");
    setError("");
  };


  useEffect(() => {
    cargarCategorias();
  }, []);

  return (
    <main className="min-h-screen bg-gray-50 p-6">
      <div className="mx-auto max-w-6xl">

        <div className="mb-6 flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">
              Categorías
            </h1>

            <p className="mt-1 text-sm text-gray-500">
              Administra las categorías de productos.
            </p>
          </div>

          <button
            type="button"
            onClick={() => setModalAbierto(true)}
            className="rounded-lg bg-indigo-600 px-4 py-2.5 text-sm font-medium text-white shadow-sm transition-colors hover:bg-indigo-700"
          >
            + Nueva categoría
          </button>
        </div>

        {error && (
          <p role="alert" className="mb-4 rounded-lg bg-red-50 p-3 text-sm text-red-700">
            {error}
          </p>
        )}

        <CategoriasTable categorias={categorias} />

      </div>
      {modalAbierto && (
        <CategoriaForm 
          cargando={cargando} 
          onGuardar={guardarCategoria} 
          nombre={nombre} 
          onNombreChange={setNombre} 
          onCerrar={cerrarModal} 
          error ={error}
          />
          )}
    </main>
  );
}

export default CategoriasPage;
