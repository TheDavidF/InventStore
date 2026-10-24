import axios, { AxiosError } from "axios";

export const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 10000
});

api.interceptors.request.use((config) => {
    const token = sessionStorage.getItem("token");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

api.interceptors.response.use(
    (response) => response,

    (error: AxiosError<{ message?: string }>) => {
        if (error.response) {
            const mensaje =
                error.response.data?.message ??
                `Error HTTP ${error.response.status}`;

            return Promise.reject(
                new ApiError(error.response.status, mensaje)
            );
        }

        return Promise.reject(
            new ApiError(
                0,
                "No se pudo establecer conexión con el servidor"
            )
        );
    }
);

export class ApiError extends Error {
    public status: number;

    constructor(status: number, message: string) {
        super(message);

        this.name = "ApiError";
        this.status = status;
    }
}
