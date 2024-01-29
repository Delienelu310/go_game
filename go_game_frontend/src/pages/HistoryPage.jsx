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
                <div key={index} className="m-5" style={{
                    border: "1px black solid",
                    borderRadius: "10px",
                    padding: "15px"
                }}>
                    <b>Id:</b> {game.id}
                    <br/>
                    <b>White:</b> {game.players[0].client.clientDetails ? game.players[0].client.clientDetails.username : "bot"}
                    <br/>
                    <b>Black:</b> {game.players[1].client.clientDetails ? game.players[1].client.clientDetails.username : "bot"}
                    {game.moves[game.moves.length - 1].moveType == "SURRENDER" ?
                        <div>
                            {game.moves[game.moves.length-1].player.client.id == game.players[0].client.id ? 
                                "white gave up, black won!" : "black gave up, white won!"}
                        </div>
                        :
                        <div>
                            {game.players[0].finalScore == game.players[1].finalScore ?
                                "Draw!":
                                (game.players[0].finalScore > game.players[1].finalScore ? 
                                    "White" : 
                                    "Black" ) + " won!"
                            }
                        </div>
                    }
                    <Link to={`/history/${game.id}`} className="btn btn-primary m-3">Watch</Link>
                </div>
            ))}
        </div>
    );
}