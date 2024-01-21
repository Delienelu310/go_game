import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { enterRoom, leaveRoom, retrieveRoom } from "../api/roomsApi"
import { setPlayer, makeMove, startGame, setPlayersCount, toggleDeadStoneGroup, toggleAgreedToFinalize, resumeGame } from "../api/gameOngoingApi";
import { useAuth } from "../security/AuthContext";
import Stomp from "stompjs"
import SockJS from "sockjs-client"

import Board from "../components/Board";

export default function GamePage(){

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
                console.log(JSON.parse(room.body));
                if(room && room.game && room.game.players.length != 2){
                    setPlayersCount(room.game.id, 2)
                        .then(response => console.log(response))
                        .catch(e => console.log(e));
                }
                setRoom(JSON.parse(room.body));
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
            {room && <div>
                <div>You are in the Room {room.title}</div>
                <div>
                    <div className="m-3">Game state: {room.game.state}</div>
                    <div className="m-3">White Player: {room.game.players[0] ? 
                        <div>
                            {room.game.players[0].client.clientDetails.username}
                            {(room.game.players[0].client.id == id || room.admin.id == id) &&
                                <button className="btn btn-danger m-2" onClick={e => {
                                    setPlayer(room.game.id, -1, 0)
                                        .then(response => {
                                            refreshRoomForAll();
                                        })
                                        .catch(e =>  console.log(e));
                                }}>X</button>
                            }
                            
                        </div>
                        :
                        <button className="btn btn-success m-2" onClick={e => {
                            setPlayer(room.game.id, id, 0)
                                .then(response => {
                                    refreshRoomForAll();
                                })
                                .catch(e =>  console.log(e));
                        }}>Play white</button>
                    }</div>
                    <div className="m-3">Black player: {room.game.players[1] ? 
                        <div>
                            {room.game.players[1].client.clientDetails.username}
                            {(room.game.players[1].client.id == id || room.admin.id == id) &&
                                <button className="btn btn-danger m-2" onClick={e => {
                                    setPlayer(room.game.id, -1, 1)
                                        .then(response => {
                                            refreshRoomForAll();
                                        })
                                        .catch(e =>  console.log(e));
                                }}>X</button>
                            }
                        </div>
                        :
                        <button className="btn btn-success m-2" onClick={e => {
                            setPlayer(room.game.id, id, 1)
                                .then(response => {
                                    refreshRoomForAll();
                                })
                                .catch(e =>  console.log(e));
                        }}>Play black</button>
                        
                    }</div>
                    <div>
                        <h4>Participants:</h4>
                        {room.participants.map((client, index) => 
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
                    {room.admin.id == id && <button onClick={e => {
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
                            {room.game.players.map(player => (
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
                        Game is finished
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