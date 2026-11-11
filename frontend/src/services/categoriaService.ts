import { api } from "../api/client";
import type { CategoriaResponse, CrearCategoriaRequest } from "../types/categoria";
export const categoriasService = {
  obtenerTodos: async () => (await api.get<CategoriaResponse[]>("/categorias")).data,
  crear: async (datos: CrearCategoriaRequest) => (await api.post<CategoriaResponse>("/categorias", datos)).data
};
