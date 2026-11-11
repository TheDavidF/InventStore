import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";
import Navbar from "./Navbar";

function AppLayout() {
  return (
    <div className="min-h-screen bg-brand-bg lg:flex">
      <Sidebar />
      <div className="min-w-0 flex-1">
        <Navbar />
        <main className="mx-auto w-full max-w-7xl px-4 py-6 sm:px-6 lg:px-8 lg:py-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
export default AppLayout;
