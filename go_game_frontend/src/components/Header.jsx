import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../security/AuthContext";

export default function Header(){

    const {id, logout} = useAuth();
    const navigate = useNavigate();

    return (
        <header className="m-5">
            <h1 className="m-3" style={{display: "inline"}}><Link to="/">Go game</Link></h1>

            


            {!id && <>
                <Link to="/register" className="m-3">Register</Link>
                <Link to="/login" className="m-3">Log in</Link>
            </>}
            {id && <>
                <Link className="m-3" to="/rooms">Rooms</Link>
                <Link className="m-3" to="/create/game">Create game</Link>
                <Link className="m-3" to="/history">Your games</Link>
                <Link className="m-3" onClick={e => {
                    logout();
                    navigate("/");
                }}>Log out</Link>
            </>}
            
            <hr/>
        </header>
    );
}