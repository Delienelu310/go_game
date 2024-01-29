import { useParams } from "react-router-dom";
import { useAuth } from "../security/AuthContext";
import { useEffect, useState } from "react";
import { recreateGame, retrieveGameById } from "../api/gameApi";
import Board from "../components/Board";

export default function GameRecordPage(){

    const {id} = useAuth();
    const {gameId} = useParams();

    const [originalGame, setOriginalGame] = useState();
    const [gameRecreated, setGameRecreated] = useState();
    const [moveNumber, setMoveNumber] = useState(1);

    useEffect(() => {
        retrieveGameById(gameId)
            .then(response => {
                setOriginalGame(response.data);
            })
            .catch(e => {
                console.log(e);
            });


        recreateGame(gameId, moveNumber)
            .then(response => {
                setGameRecreated(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }, [moveNumber]);

    return (
        <div>
            {originalGame ? 
                <div>

                    {/* Board itself */}
                    {gameRecreated ? 
                        <div className="m-3" style={{display: "inline-block", position:"relative", left: "5%", top:"5%", width:"50%"}}>
                            <Board
                                size={gameRecreated.board.size}
                                cellSize={50}
                                boardMatrix={gameRecreated.board.board}
                                white={originalGame.players[0] ? originalGame.players[0].client.id : null}
                                black={originalGame.players[1] ? originalGame.players[1].client.id : null}
                                // unused:
                                deadStoneGroups={[]}
                                clientId={id}
                                sendMove={() => {}}
                            />
                        </div> 
                        :
                        <div>Loading...</div>
                    }

                    {/* Panel for managing */}
                    <div style={{width: "40%", display: "inline-block", position:"relative"}}>
                        <h5>Current move: {moveNumber}</h5>

                        {originalGame.moves.map( (move, index) => (
                            <div className="m-3"  style={{
                                color: index == moveNumber - 1 ? "red" : "black",
                                border: "1px solid black",
                                borderRadius: "10px",
                                display: "inline-block",
                                width: "40%"
                            }}>
                                <div>Moves made</div>
                                <div onClick={() => setMoveNumber(index + 1)} key={index}>
                                    {move.player.client.clientDetails ? 
                                        move.player.client.clientDetails.username : 
                                        "bot"
                                    } | {move.moveType} 
                                    {move.moveType == "NORMAL" && ` - ${move.x} -${move.y} `}
                                </div>
                            </div>
                        ))}

                        <div className="m-3">
                            <button className="btn" disabled={moveNumber == 1} onClick={() => {setMoveNumber(0)}}>{"<<"}</button>
                            <button className="btn" disabled={moveNumber == 1} onClick={() => {setMoveNumber(moveNumber - 1)}}>{"<"}</button>
                            <button className="btn" disabled={moveNumber == originalGame.moves.length} onClick={() => setMoveNumber(moveNumber + 1)}>{">"}</button>
                            <button className="btn" disabled={moveNumber == originalGame.moves.length} onClick={() => setMoveNumber(originalGame.moves.length)}>{">>"}</button>
                        </div>
                    </div>


                    
                    
                </div>
                :
                <div>
                    Game is not found
                </div>
            }
        </div>
    );
}