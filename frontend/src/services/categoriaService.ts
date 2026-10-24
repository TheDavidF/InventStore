import {api} from "../api/client";

import type {
    CategoriaResponse,

} from "../types/categoria";

export const categoriasService ={
    obtenerTodos: async () =>
    (await api.get<CategoriaResponse[]>("/categorias")).data,
}