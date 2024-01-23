import { useEffect, useState } from "react";
import { useAuth } from "../security/AuthContext";
import { retrieveGamesByUser } from "../api/gameApi";
import { Link } from "react-router-dom";

export default function HistotyPage(){

    const {id} = useAuth();

    const [games, setGames] = useState([]);

    useEffect(() => {
        retrieveGamesByUser(id)
            .then(response => {
                console.log(response);
                setGames(response.data);
            }).catch(e => {
                console.log(e);
            });
    }, []);

    return (
        <div>
            {games && games.map( (game, index) => (
                <div key={index}>
                    <Link to={`/history/${index}`} className="btn m-3">Watch</Link>
                </div>
            ))}
        </div>
    );
}