import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import ProductosPage from "./pages/ProductosPage";
import CategoriasPage from "./pages/CategoriasPage";
import ProtectedRoute from "./components/ProtectedRoute";
import AppLayout from "./components/AppLayout";
function App(){
  return <BrowserRouter><Routes>
    <Route path="/login" element={<LoginPage/>}/>
    <Route element={<ProtectedRoute><AppLayout/></ProtectedRoute>}>
      <Route path="/productos" element={<ProductosPage/>}/>
      <Route path="/categorias" element={<CategoriasPage/>}/>
    </Route>
    <Route path="/" element={<Navigate to="/productos" replace/>}/>
    <Route path="*" element={<Navigate to="/productos" replace/>}/>
  </Routes></BrowserRouter>;
}
export default App;
