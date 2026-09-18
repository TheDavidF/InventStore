import { Navigate } from "react-router-dom";
import type { ReactNode } from "react";

type ProtectedRouteProps = {
    children: ReactNode;
};

function ProtectedRoute({ children }: ProtectedRouteProps) {
    const token = sessionStorage.getItem("token");

    if (token === null) {
        return <Navigate to="/login" replace />;
    }

    return children;
}

export default ProtectedRoute;