import { useContext, createContext, useState } from "react"

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({children}){

    const [id, setId] = useState();

    return (
        <AuthContext.Provider value={{id, setId}}>
            {children}
        </AuthContext.Provider>
    );
}