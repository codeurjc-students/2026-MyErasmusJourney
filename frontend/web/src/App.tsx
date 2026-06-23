import { Outlet } from 'react-router-dom'
import './App.css'
import Header from './components/Header'


function App() {
  console.log("Entrando en App...")
  return (
    <>
      <Header/>
      <Outlet/>
    </>
  )
}

export default App
