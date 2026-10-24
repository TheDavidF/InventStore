import { api } from "../api/client";

import type {
    ProductoResponse,
    CrearProductoRequest,
    ActualizarProductoRequest
} from "../types/producto";

export const productosService = {
    obtenerTodos: async () =>
        (await api.get<ProductoResponse[]>("/productos")).data,

    crear: async (datos: CrearProductoRequest) =>
        (await api.post<ProductoResponse>("/productos", datos)).data,

    actualizar: async (id: number, datos: ActualizarProductoRequest) =>
        (await api.put<ProductoResponse>(`/productos/${id}`, datos)).data,

    desactivar: async (id: number) =>
        (await api.patch<ProductoResponse>(`/productos/${id}/desactivar`)).data,

    reactivar: async (id: number) =>
        (await api.patch<ProductoResponse>(`/productos/${id}/reactivar`)).data
};