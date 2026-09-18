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

    const cambiarPassword =(event: ChangeEvent<HTMLInputElement>) =>{
        setPassword(event.target.value)
    };

    const cambiarCorreo = (event: ChangeEvent<HTMLInputElement>) => {
        setCorreo(event.target.value);
    };


    const iniciarSesion = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const respuesta = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers:{
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                correo: correo,
                contrasena: password
            })
        });

        const datos:LoginResponse = await respuesta.json();
        sessionStorage.setItem("token", datos.token);
        sessionStorage.setItem("correo", datos.correo);
        sessionStorage.setItem("rol", datos.rol);
        navigate("/productos");
        console.log(datos);
    };


    return (
        <main>
            <h1>InventStore</h1>

            <form onSubmit={iniciarSesion}>
                <div>
                    <label>Correo</label>
                    <input type="email"
                           value={correo}
                           onChange={cambiarCorreo} />
                </div>

                <div>
                    <label>Contraseña</label>
                    <input type="password"
                           value={password}
                           onChange={cambiarPassword}/>
                </div>

                <button type="submit">
                    Iniciar sesión

                </button>


            </form>
        </main>
    );
}

export default LoginPage;