import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { RouterProvider } from "react-router-dom"
import './assets/styles/global.css'
import router from "./router"
import {AuthProvider} from "./services/AuthProvider.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <AuthProvider>
        <RouterProvider router={router}/>
      </AuthProvider>
  </StrictMode>,
)
