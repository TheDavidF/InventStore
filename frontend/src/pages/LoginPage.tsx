import { useState } from "react";

import type { ChangeEvent, FormEvent } from "react";
import { useNavigate } from "react-router-dom";

type LoginResponse = {
    token: string;
    tipo: string;
    expiraEn: number;
    correo: string;
    rol: string;
};

function LoginPage() {
    const [correo, setCorreo] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const cambiarPassword = (event: ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value)
    };

    const cambiarCorreo = (event: ChangeEvent<HTMLInputElement>) => {
        setCorreo(event.target.value);
    };


    const iniciarSesion = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const respuesta = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                correo: correo,
                contrasena: password
            })
        });

        const datos: LoginResponse = await respuesta.json();
        sessionStorage.setItem("token", datos.token);
        sessionStorage.setItem("correo", datos.correo);
        sessionStorage.setItem("rol", datos.rol);
        navigate("/productos");
        console.log(datos);
    };


    return (
        <main>
            <div className="min-h-screen bg-[#EFE3D2] flex items-center justify-center p-4">
                <div className="grid w-full max-w-4xl overflow-hidden rounded-2xl bg-[#FAF6EF] shadow-lg md:grid-cols-2">

                    <div className="hidden md:block">
                        <img
                            src="/fondo-login.png"
                            alt="Café y quesadillas de Sazón Hogareño"
                            className="h-full w-full object-cover"
                        />
                    </div>

                    <div className="flex flex-col justify-center px-6 py-8 sm:p-10 md:p-12">
                        <img
                            src="/logo-sazonH.png"
                            alt="Sazón Hogareño"
                            className="mx-auto h-auto w-70 px-0 py-6 max-w-full"
                        />


                        <h1 className="text-center font-serif text-3xl font-normal text-[#3D2B24] md:text-4xl">
                            Bienvenido a casa
                        </h1>

                        <p className="mt-3 text-center text-sm text-[#8C4F3D]">
                            Ingresa tus datos para continuar
                        </p>

                        <div
                            aria-hidden="true"
                            className="mt-6 flex items-center gap-3"
                        >
                            <div className="h-px flex-1 bg-[#D8CBBB]" />

                            <span className="text-sm text-[#8C4F3D]">✦</span>

                            <div className="h-px flex-1 bg-[#D8CBBB]" />
                        </div>

                        <form onSubmit={iniciarSesion} className="mt-8 space-y-5">
                            <div>
                                <label
                                    htmlFor="correo"
                                    className="mb-2 block text-sm font-medium text-[#3D2B24]"
                                >
                                    Correo</label>

                                <input
                                    type="email"
                                    value={correo}
                                    onChange={cambiarCorreo}
                                    placeholder="Ingresa tu correo"
                                    className="w-full rounded-lg border border-[#D8CBBB] bg-transparent px-4 py-3 text-[#3D2B24] placeholder:text-[#8A7A70] focus:border-[#8C4F3D] focus:outline-none focus:ring-2 focus:ring-[#8C4F3D]/20"
                                />
                            </div>

                            <div>
                                <label
                                    htmlFor="password"
                                    className="mb-2 block text-sm font-medium text-[#3D2B24]"
                                >
                                    Contraseña</label>
                                <input
                                    type="password"
                                    value={password}
                                    onChange={cambiarPassword}
                                    placeholder="Ingresa tu contraseña"
                                    className="w-full rounded-lg border border-[#D8CBBB] bg-transparent px-4 py-3 text-[#3D2B24] placeholder:text-[#8A7A70] focus:border-[#8C4F3D] focus:outline-none focus:ring-2 focus:ring-[#8C4F3D]/20"
                                />

                            </div>

                            <button
                                type="submit"
                                className="w-full cursor-pointer rounded-lg bg-[#8C4F3D] px-4 py-3 font-medium text-white transition-colors hover:bg-[#713F30] focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-[#8C4F3D]"
                            >
                                Iniciar sesión

                            </button>


                        </form>

                    </div>


                </div>
            </div>


        </main>
    );
}

export default LoginPage;