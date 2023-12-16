import { useContext, createContext, useState } from "react"
import { login, register } from "../api/authenticationApi";


const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({children}){

    const [id, setId] = useState();


    async function tryLogin(username, password){
        return login(username, password)
            .then(response => {
                setId(response.data.id);
                return true;
            }) ;
        
    }

    async function tryRegister(username, password){
        return register(username, password)
            .then(response => {
                setId(response.data.id);
            });
    }
    async function logout(){
        setId(null);
        //TODO: add exiting from the room
    }

    return (
        <AuthContext.Provider value={{id, logout, tryLogin, tryRegister}}>
            {children}
        </AuthContext.Provider>
    );
}