export type ProductoResponse = {
    id: number;
    nombre: string;
    descripcion: string;
    precio: number;
    tipo: string;
    activo: boolean;
    categoriaId: number;
    categoriaNombre: string;
};

export type ProductoForm = {
    nombre: string;
    descripcion: string;
    precio: string;
    tipo: string;
    categoriaId: number | null;
};

export type CrearProductoRequest = {
    nombre: string;
    descripcion: string;
    precio: number;
    tipo: string;
    categoriaId: number;
};

export type ActualizarProductoRequest = {
    nombre: string;
    descripcion: string;
    precio: number;
};