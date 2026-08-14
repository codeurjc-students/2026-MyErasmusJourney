import { Outlet } from 'react-router-dom'
import './App.css'
import Header from './components/Header/Header.tsx';
import { useEffect } from 'react';
import { useUserStore } from '@shared/stores/userStore.ts';
import { createUserService } from '@shared/services/user.service.ts';
import { API } from './api/client.ts';
import type { userServiceProps } from '@shared/interfaces/userServiceProps.ts';


function App({userService = createUserService(API)}: userServiceProps) {

  const {setUser } = useUserStore();

  useEffect(() => {
    const fetchUser = async () => {
      
      try {
        console.log("Fetching user info in app");
        const data = await userService.getUserInfo();
        setUser(data);
      } catch {
        setUser(null);
      }
    }

    fetchUser();
  },[])

  return (
    <>
      <Header/>
      <Outlet/>
    </>
  )
}

export default App
