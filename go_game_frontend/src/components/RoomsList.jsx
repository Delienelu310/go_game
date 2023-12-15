import { useEffect, useState } from "react";
import { retrieveRoomsList } from "../api/roomsApi";
import Room from "./Room";

export default function RoomsList(){

    const [showException, setException] = useState(false);

    const [rooms, setRooms] = useState([]);

    function getRoomsList(){
        retrieveRoomsList.then();
    }

    useEffect(() => {
        getRoomsList()
            .then(resopnse => {
                setRooms(resopnse.data);
                setException(false);
            })
            .catch(e => {
                console.log(e);
                setException(true);
            });
    }, []);

    return (
        <div>
            {rooms.map( (room, index) => {
                return (
                    <Room room={room} key={index}/>
                );
            })}
        </div>
    );
}