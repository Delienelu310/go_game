import { useState } from "react";
import { useAuth } from "../security/AuthContext";
import { useNavigate } from "react-router-dom";

export default function LoginPage(){

    const [showException, setException] = useState(false);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const {tryLogin} = useAuth();
    const navigate = useNavigate();

    function login(){
        tryLogin(username, password)
            .then(response => {
                navigate("/");
            })
            .catch(e => {
                setException(true);
            });
    }

    return (
        <div>
            {showException && <div className="m-3">Exception</div>}
    
            Username:
            <input className="m-2 form-control" value={username} onChange={e => setUsername(e.target.value)}/>
            Password:
            <input className="m-2 form-control" value={password} onChange={e => setPassword(e.target.value)}/>

            <button className="btn btn-success m-2" onClick={e => login()}>Log in</button>
        </div>
    );
}