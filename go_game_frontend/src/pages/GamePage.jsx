import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { addBlackPlayer, addWhitePlayer, enterRoom, leaveRoom, removeBlackPlayer, removeWhitePlayer, retrieveRoom, startGame } from "../api/roomsApi"
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

    function sendMove({x, y, color, pass}){
        stompClient &&
        stompClient.send( `/room/${roomId}/move`,  {},  JSON.stringify({x ,y, color, pass }));
    };

    function refreshRoomForAll(){
        stompClient &&
        stompClient.send( `/room/${roomId}/refresh`,  {},  "{}");
    }

    function getRoomInfo(){
        retrieveRoom(roomId)
            .then(response => {
                console.log(response);
                setRoom(response.data);
            })
            .catch(e => {
                console.log(e);
                setException(true);
            });
    }

    useEffect(() => {
        enterRoom(roomId, id)
            .then(response => {
                initializeStompClient();
                getRoomInfo();
            })
            .catch(e => {
                console.log(e);
            });
        
    }, []);

    return (
        <div>
            {room && <div>
                <div>You are in the Room {room.title}</div>
                <div>
                    <div className="m-3">Game state: {room.game.state}</div>
                    <div className="m-3">White Player: {room.game.white ? 
                        <div>
                            {room.game.white.clientDetails.username}
                            {(room.game.white.id == id || room.admin.id == id) &&
                                <button className="btn btn-danger m-2" onClick={e => {
                                    removeWhitePlayer(room.id)
                                        .then(response => {
                                            refreshRoomForAll();
                                        })
                                        .catch(e =>  console.log(e));
                                }}>X</button>
                            }
                            
                        </div>
                        :
                        <button className="btn btn-success m-2" onClick={e => {
                            addWhitePlayer(room.id, id)
                                .then(response => {
                                    refreshRoomForAll();
                                })
                                .catch(e =>  console.log(e));
                        }}>Play white</button>
                    }</div>
                    <div className="m-3">Black player: {room.game.black ? 
                        <div>
                            {room.game.black.clientDetails.username}
                            {(room.game.black.id == id || room.admin.id == id) &&
                                <button className="btn btn-danger m-2" onClick={e => {
                                    removeBlackPlayer(room.id)
                                        .then(response => {
                                            refreshRoomForAll();
                                        })
                                        .catch(e =>  console.log(e));
                                }}>X</button>
                            }
                        </div>
                        :
                        <button className="btn btn-success m-2" onClick={e => {
                            addBlackPlayer(room.id, id)
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
                        startGame(room.id)
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
                    <button className="btn btn-danger m-2">Surrender</button>
                    <Board
                        size={room.game.size}
                        cellSize={50}
                        boardMatrix={room.game.field}
                        color={room.game.white.id == id ? 
                            "WHITE" :
                            room.game.black.id == id ? 
                            "BLACK"
                            : null
                        }
                        sendMove={sendMove}
                    /> 
                </div>}
                
            </div>}

            
        </div>
    );
}