import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { enterRoom, leaveRoom, retrieveRoom } from "../api/roomsApi"
import { setBot, setPlayer, makeMove, startGame, setPlayersCount, toggleDeadStoneGroup, toggleAgreedToFinalize, resumeGame } from "../api/gameOngoingApi";
import { useAuth } from "../security/AuthContext";
import Stomp from "stompjs"
import SockJS from "sockjs-client"

import Board from "../components/Board";

export default function GamePage(){

    const navigate = useNavigate();

    const {id} = useAuth();
    const {roomId} = useParams();

    const [showException, setException] = useState(false);

    const [room, setRoom] = useState();

    const [stompClient, setStompClient] = useState(null);
    const [connected, setConnected] = useState(false);
    
    function initializeStompClient(){

        const socket = new SockJS("http://localhost:8080/socket/room");
        const client = Stomp.over(socket);

        client.connect({},  () => {
            setConnected(true);

            console.log('Connected');
            client.subscribe(`/game/${roomId}`, (room) => {

                // if you are not in the participants list, then you were kicked:
                console.log("here");
                
                let roomObj = JSON.parse(room.body);
                if(roomObj && roomObj.participants){
                    if(! (roomObj.participants.map(participant => participant.id).includes(id) ) ){
                        console.log("here");
                        
                        disconnect();
                        navigate("/");
                        return;
                    }
                }
                

                console.log(roomObj);
         
                setRoom(roomObj);
            });

        });

        client.onWebSocketError = (error) => {
            console.error('Error with websocket', error);
        };

        client.onStompError = (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        };

        setStompClient(client);
        refreshRoomForAll();
    };

    function connect(){
        stompClient && stompClient.activate();
        setConnected(true);
        console.log("Connected");
    };

    function disconnect(){
        stompClient && stompClient.disconnect();
        setConnected(false);
        console.log('Disconnected');
    };

    function refreshRoomForAll(){
        stompClient &&
        stompClient.send( `/room/${roomId}/refresh`,  {},  "{}");
    }


    useEffect(() => {
        enterRoom(roomId, id)
            .then(response => {
                initializeStompClient();
                return retrieveRoom(roomId);
            }).then(response => {
                setRoom(response.data);
                return setPlayersCount(response.data.game.id, 2);
            }).then(response => {
                console.log(response);
            }).catch(e => {
                console.log(e);
            });
        
    }, []);

    return (
        <div>
            {room && room.game && <div>
                <button className="m-3 btn btn-danger" onClick={e => {
                        leaveRoom(room.id, id)
                            .then(response => {
                                refreshRoomForAll();
                            })
                            .catch(e => {
                                console.log(e);
                            });
                    }}>Leave</button>
                <div>
                    <div className="m-3"><b>Game state: </b>{room.game.state}</div>
                    <div className="m-3">
                        <b>White Player:</b> 
                        { (room.game.players[0] && room.game.players[0].client && room.game.players[0].client.clientDetails) ? 
                        <span>
                            {room.game.players[0].client.clientDetails.username}
                            {room.game.state == "FINISHED" && <span>
                                <b> | Final score:</b> {room.game.players[0].finalScore}    
                            </span>}
                            {(room.game.state == "ONGOING" || room.game.state == "NEGOTIATION") && <span>
                                <b>| Captives:</b> {room.game.players[0].captives ? room.game.players[0].captives.length : 0}
                            </span>}
                            {(room.game.players[0].client.id == id || room.admin.id == id) && room.game.state == "CREATED" &&
                                <button className="btn btn-danger m-2" onClick={e => {
                                    setPlayer(room.game.id, -1, 0)
                                        .then(response => {
                                            refreshRoomForAll();
                                        })
                                        .catch(e =>  console.log(e));
                                }}>X</button>
                            }
                            
                        </span>
                        :
                        <>
                            <button className="btn btn-success m-2" onClick={e => {
                                setPlayer(room.game.id, id, 0)
                                    .then(response => {
                                        refreshRoomForAll();
                                    })
                                    .catch(e =>  console.log(e));
                            }}>Play white</button>
                            {room.admin.id == id && <button onClick={e => {
                                setBot(room.game.id, 1, 0)
                                    .then(respone => {
                                        refreshRoomForAll();
                                    })
                                    .catch(e => console.log(e));
                            }} className="btn btn-primary m-2">
                                Add bot
                            </button>}
                        </>
                        
                        
                    }</div>
                    <div className="m-3">
                        <b>Black player: </b>
                        {room.game.players[1] && room.game.players[1].client && room.game.players[1].client.clientDetails  ? 
                        <span>
                            {room.game.players[1].client.clientDetails.username} 
                            {room.game.state == "FINISHED" && <span>
                                <b> | Final score:</b> {room.game.players[1].finalScore}    
                            </span>}
                            {(room.game.state == "ONGOING" || room.game.state == "NEGOTIATION") && <span>
                                <b>| Captives:</b> {room.game.players[1].captives ? room.game.players[1].captives.length : 0}
                            </span>}
                            {(room.game.players[1].client.id == id || room.admin.id == id)  && room.game.state == "CREATED" &&
                                <button className="btn btn-danger m-2" onClick={e => {
                                    setPlayer(room.game.id, -1, 1)
                                        .then(response => {
                                            refreshRoomForAll();
                                        })
                                        .catch(e =>  console.log(e));
                                }}>X</button>
                            }
                        </span>
                        :
                        <>
                            <button className="btn btn-success m-2" onClick={e => {
                                setPlayer(room.game.id, id, 1)
                                    .then(response => {
                                        refreshRoomForAll();
                                    })
                                    .catch(e =>  console.log(e));
                            }}>Play black</button>
                            {room.admin.id == id && <button onClick={e => {
                                setBot(room.game.id, 1, 1)
                                    .then(respone => {
                                        refreshRoomForAll();
                                    })
                                    .catch(e => console.log(e));
                            }} className="btn btn-primary m-2">
                                Add bot
                            </button>}
                        </>
                        
                        
                    }</div>
                    <div>
                        <h4>Viewers:</h4>
                        {room.participants
                            .filter(participant => !room.game.players
                                .map(player => player.client ? player.client.id : null)
                                .includes(participant.id)
                            ).map((client, index) => 
                                <div className="m-2">
                                    {index + 1}.{client.clientDetails.username}
                                    {room.admin.id == id && <button className="btn btn-danger m-2" onClick={e => {
                                        leaveRoom(room.id, id)
                                            .then(response => {
                                                refreshRoomForAll();
                                            })
                                            .catch(e =>  console.log(e));
                                    }}>X</button>}
                                </div>
                        )}
                    </div>
                    {room.admin.id == id && room.game.state == 'CREATED' && <button className="m-3 btn btn-primary" onClick={e => {
                        startGame(room.game.id)
                            .then(response => {
                                refreshRoomForAll();
                            })
                            .catch(e => {
                                console.log(e);
                            });
                    }}>Start game</button>}
                </div>

                <br/>
                {room.game.state != "CREATED" && <div>

                    {/* Pass button */}
                    {room.game.state == "ONGOING" && <button className="m-3 btn btn-primary" onClick={() => {
                        makeMove(room.game.id, {x: 0, y: 0, moveType: "PASS", player: {client: {id} } } )
                        .then(response => refreshRoomForAll())
                        .catch(e => console.log(e));
                    }}>Pass</button>}

                    {/* Surrender button */}
                    {room.game.state != "FINISHED" && <button className="m-3 btn btn-danger" onClick={() => {
                        makeMove(room.game.id, {x: 0, y: 0, moveType: "SURRENDER", player: {client: {id} } } )
                            .then(response => refreshRoomForAll())
                            .catch(e => console.log(e));
                    }}>Surrender</button>}


                    {/* Negotiation panel */}
                    {room.game.state == "NEGOTIATION" && <div>

                        {/* Resume game button */}
                        <button className="m-3 btn-primary" onClick={() => {
                            resumeGame(room.game.id, id)
                                .then(response => refreshRoomForAll())
                                .catch(e => console.log(e));
                        }}>Resume game</button>

                        {/* players agreed disagreed */}
                        <div>
                            {room.game.players.map(player => (player.client && player.client.clientDetails && 
                                <div>{player.client.clientDetails.username}: 
                                    {room.game.agreed.map(player => player.client.id).includes(player.client.id) ? "Ready" : "Not ready"}
                                </div>
                            ))}
                        </div>

                        {/* Agree/disagree to finalize button */}
                
                        <button className="m-3" onClick={() => {
                            toggleAgreedToFinalize(room.game.id, id)
                                .then(response => refreshRoomForAll())
                                .catch(e => console.log(e));
                        }}>
                            {room.game.agreed.map(player => player.client.id).includes(id) ? "Don`t agree" : "Agree"}
                        </button>
                    </div>}

                    {/* Game finished panel */}
                    {room.game.state == "FINISHED" && <div>
                        <h6>Game is finished</h6>
                        {room.game.moves[room.game.moves.length-1].moveType == 'SURRENDER' ? 
                            <div>
                                {room.game.moves[room.game.moves.length-1].player.client.clientDetails.username} gave up.
                                <br/>
                                {room.game.players
                                    .filter(player => player.client && player.client.id != room.game.moves[room.game.moves.length-1].player.client.id)[0]
                                    .client.clientDetails.username} won!
                            </div>
                            :
                            <div>
                                {room.game.players[0].finalScore == room.game.players[1].finalScore ?
                                    "Draw!":
                                    (room.game.players[0].finalScore > room.game.players[1].finalScore ? 
                                        "White" : 
                                        "Black" ) + " won!"
                                }
                            </div>
                        }

                    </div>}

                    

                    <Board
                        size={room.game.board.size}
                        cellSize={50}
                        boardMatrix={room.game.board.board}
                        deadStoneGroups={room.game.deadStoneGroups}
                        clientId={id}
                        sendMove={({x,y,clientId, moveType}) => {
                            switch(room.game.state){
                                case "ONGOING": 
                                    makeMove(room.game.id, {x,y,moveType, player: {client: {id:clientId}}})
                                        .then(response => refreshRoomForAll())
                                        .catch(e => console.log(e));
                                    break;
                                case "NEGOTIATION":
                                    toggleDeadStoneGroup(room.game.id, {x, y, player: {client: {id: clientId}}})
                                        .then(response => {
                                            refreshRoomForAll();
                                        })
                                        .catch(e => console.log(e));
                                    
                                    break;
                            }
                            
                        }}
                        white={room.game.players[0] ? room.game.players[0].client.id : null}
                        black={room.game.players[1] ? room.game.players[1].client.id : null}
                    /> 

                </div>}
                
            </div>}

            
        </div>
    );
}