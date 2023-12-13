import { Link } from "react-router-dom";

export default function Header(){
    return (
        <header className="m-5">
            <h1 style={{display: "inline"}}>Go game</h1>
            <Link to="/register" className="btn btn-success m-3">Register</Link>
            <Link to="/login" className="btn btn-success">Log in</Link>
            <hr/>
        </header>
    );
}