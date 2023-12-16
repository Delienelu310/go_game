import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { retrieveRoom } from "../api/roomsApi";


import Board from "../components/Board";

export default function GamePage(){

    const {roomId} = useParams();

    const [showException, setException] = useState(false);

    const [room, setRoom] = useState();

    const [stompClient, setStompClient] = useState(null);

    const [connected, setConnected] = useState(false);
    const [name, setName] = useState('');
    const [greetings, setGreetings] = useState([]);



    const initializeStompClient = () => {

        const client = useStomp(
            {
                brokerURL: `ws://localhost:8080/rooms/sockets/${roomId}`,
            },
            () => {
                setConnected(true);
                console.log('Connected: ');
                client.subscribe(`/game/${roomId}/move`, (room) => {
                    setRoom(JSON.parse(room));
                });
            }
        );


        // client.onWebSocketError = (error) => {
        //     console.error('Error with websocket', error);
        // };

        // client.onStompError = (frame) => {
        //     console.error('Broker reported error: ' + frame.headers['message']);
        //     console.error('Additional details: ' + frame.body);
        // };

        setStompClient(client);
    };

    // const connect = () => {
    //     stompClient && stompClient.activate();
    //     setConnected(true);
    //     console.log("Connected");
    // };

    const disconnect = () => {
        stompClient && stompClient.disconnect();
        setConnected(false);
        console.log('Disconnected');
    };

    const sendMove = (x, y, color) => {
        stompClient &&
        stompClient.send( `/${roomId}/move`, JSON.stringify({x ,y, color }));
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
        // activate();
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