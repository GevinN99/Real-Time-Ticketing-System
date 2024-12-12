import axios from "axios";
import { Toaster } from "react-hot-toast";
import { Routes, Route } from 'react-router-dom'
import { BrowserRouter } from 'react-router-dom'

import Navbar from "./components/Navbar";
import Home from "./pages/Home.jsx";
import Configuration from "./pages/Configs.jsx";

function App() {
  axios.defaults.baseURL = "http://localhost:8070/api/config";
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/configuration" element={<Configuration />} />                                               
      </Routes>
      <Toaster toastOptions={{ duration: 3000 }} />          
    </BrowserRouter>
  );
}

export default App;
