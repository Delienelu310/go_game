import { useEffect, useState } from "react";
import { retrieveRoomsList } from "../api/roomsApi";
import Room from "./Room";

export default function RoomsList(){

    const [showException, setException] = useState(false);

    const [rooms, setRooms] = useState([]);


    useEffect(() => {
        retrieveRoomsList()
            .then(response => {
                console.log(response);
                setRooms(response.data);
                setException(false);
            })
            .catch(e => {
                console.log(e);
                setException(true);
            });
    }, []);

    return (
        <div>
            {showException && <div>Error</div>}
            {rooms.map( (room, index) => {
                return (
                    <Room room={room} key={index}/>
                );
            })}
        </div>
    );
}  