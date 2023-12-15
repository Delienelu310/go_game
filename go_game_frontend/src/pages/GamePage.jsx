import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { retrieveRoom } from "../api/roomsApi";
import Board from "../components/Board";

export default function GamePage(){

    const {roomId} = useParams();

    const [showException, setException] = useState(false);

    const [room, setRoom] = useState();

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
        getRoomInfo();
    }, []);

    return (
        <div>
            You are in the Room {room && room.title}
            {room && <Board
                size={room.game.size}
                cellSize={50}
                boardMatrix={room.game.field}
                color={"WHITE"}
            />}
        </div>
    );
}