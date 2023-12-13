import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../security/AuthContext";

export default function RegisterPage(){

    const [showException, setException] = useState(false);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [repeatPass, setRepeatPass] = useState("");

    const {tryRegister} = useAuth();
    
    const navigate = useNavigate();

    function register(){
        if(password != repeatPass){
            setException(true);
            return;
        }
        tryRegister(username, password)
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

            Username
            <input className="m-2 form-control" value={username} onChange={e => setUsername(e.target.value)}/>
            Password:
            <input className="m-2 form-control" value={password} onChange={e => setPassword(e.target.value)}/>
            Repeat password:
            <input className="m-2 form-control" value={repeatPass} onChange={e => setRepeatPass(e.target.value)}/>

            <button className="btn btn-success" onClick={e => register()}>Register</button>
        </div>
    );
}