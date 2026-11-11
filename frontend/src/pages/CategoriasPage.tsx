import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { categoriasService } from "../services/categoriaService";
import { ApiError } from "../api/client";
import type { CategoriaResponse } from "../types/categoria";
import CategoriasTable from "../components/categorias/CategoriasTable";
import CategoriaForm from "../components/categorias/CategoriaForm";
import PageHeader from "../components/ui/PageHeader";
import Modal from "../components/ui/Modal";

function CategoriasPage(){
  const navigate=useNavigate();
  const [categorias,setCategorias]=useState<CategoriaResponse[]>([]);
  const [nombre,setNombre]=useState("");
  const [modalAbierto,setModalAbierto]=useState(false);
  const [cargando,setCargando]=useState(false);
  const [cargandoLista,setCargandoLista]=useState(true);
  const [errorFormulario,setErrorFormulario]=useState("");
  const [errorLista,setErrorLista]=useState("");

  const manejarError=(error:unknown, destino:"lista"|"formulario")=>{
    if(error instanceof ApiError && error.status===401){
      sessionStorage.removeItem("token");sessionStorage.removeItem("correo");sessionStorage.removeItem("rol");
      navigate("/login");return;
    }
    const mensaje=error instanceof ApiError?error.message:"Ocurrió un error inesperado";
    if(destino==="lista")setErrorLista(mensaje);else setErrorFormulario(mensaje);
  };
  useEffect(()=>{
    let activo=true;
    categoriasService.obtenerTodos()
      .then(datos=>{if(activo)setCategorias(datos);})
      .catch(error=>{if(activo) manejarError(error,"lista");})
      .finally(()=>{if(activo)setCargandoLista(false);});
    return ()=>{activo=false;};
  },[]);
  const cerrarModal=()=>{if(cargando)return;setModalAbierto(false);setNombre("");setErrorFormulario("");};
  const guardarCategoria=async()=>{
    const nombreLimpio=nombre.trim();
    if(!nombreLimpio){setErrorFormulario("El nombre es obligatorio");return;}
    setCargando(true);setErrorFormulario("");
    try{
      const creada=await categoriasService.crear({nombre:nombreLimpio});
      setCategorias(anteriores=>[...anteriores,creada]);
      setNombre("");setModalAbierto(false);
    }catch(error){manejarError(error,"formulario");}
    finally{setCargando(false);}
  };
  return (
    <section>
      <PageHeader titulo="Categorías" descripcion="Organiza los productos de Sazón Hogareño." accion={
        <button type="button" onClick={()=>{setErrorFormulario("");setModalAbierto(true);}} className="rounded-lg bg-brand-primary px-5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-brand-primary-dark">+ Nueva categoría</button>
      }/>
      {errorLista && <p role="alert" className="mb-4 rounded-lg bg-red-50 p-3 text-sm text-red-700">{errorLista}</p>}
      {cargandoLista ? <p className="rounded-xl bg-brand-surface p-6 text-brand-muted">Cargando categorías...</p> : !errorLista && <CategoriasTable categorias={categorias}/>}
      {modalAbierto && <Modal titulo="Nueva categoría" descripcion="Registra una categoría para tus productos." onCerrar={cerrarModal}>
        <CategoriaForm nombre={nombre} cargando={cargando} error={errorFormulario} onNombreChange={setNombre} onGuardar={guardarCategoria} onCerrar={cerrarModal}/>
      </Modal>}
    </section>
  );
}
export default CategoriasPage;
