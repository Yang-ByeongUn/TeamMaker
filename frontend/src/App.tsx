// src/App.tsx

import { useEffect, useState } from "react";
import Login from "./components/login/Login";
import Home from "./Home";

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    setIsLoggedIn(!!token);
  }, []);

  return isLoggedIn ? (
      <Home />
  ) : (
      <Login onLogin={() => setIsLoggedIn(true)} />
  );
}
