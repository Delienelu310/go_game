import { Link } from "react-router-dom";

export default function MainPage(){
    return (
        <div>
            <Link className="btn btn-success m-2" to="/rooms">Games list</Link>
            <Link className="btn btn-success m-2" to="/create/game">Create game</Link>
        </div>
    );
}