import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { retrieveRoom } from "../api/roomsApi";
import Stomp from "stompjs"
import SockJS from "sockjs-client"

import Board from "../components/Board";

export default function GamePage(){

    const {roomId} = useParams();

    const [showException, setException] = useState(false);

    const [room, setRoom] = useState();

    const [stompClient, setStompClient] = useState(null);
    const [connected, setConnected] = useState(false);
    
    function initializeStompClient(){

        const socket = new SockJS("http://localhost:8080/socket/room");
        // const client =  Stomp.client(`ws://localhost:8080/socket/room`);/=
        const client = Stomp.over(socket);

        client.connect({},  () => {
            setConnected(true);

            console.log('Connected');
            client.subscribe(`/game/${roomId}/move`, (room) => {
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

    function sendMove({x, y, color}){
        stompClient &&
        stompClient.send( `/room/${roomId}/move`,  {},  JSON.stringify({x ,y, color }));
    };

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
        initializeStompClient();
        // connect();
        getRoomInfo();
    }, []);

    return (
        <div>
            You are in the Room {room && room.title}

            <br/>
            {room && <Board
                size={room.game.size}
                cellSize={50}
                boardMatrix={room.game.field}
                color={"WHITE"}
                sendMove={sendMove}
            />}
        </div>
    );
}