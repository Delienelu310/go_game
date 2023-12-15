import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../security/AuthContext";

export default function Header(){

    const {id, logout} = useAuth();
    const navigate = useNavigate();

    return (
        <header className="m-5">
            <h1 style={{display: "inline"}}>Go game</h1>
            {!id && <>
                <Link to="/register" className="btn btn-success m-3">Register</Link>
                <Link to="/login" className="btn btn-success">Log in</Link>
            </>}
            {id && <>
                {id}
                <button onClick={e => {
                    logout();
                    navigate("/");
                }} className="btn btn-success">Log out</button>
            </>}
            
            <hr/>
        </header>
    );
}