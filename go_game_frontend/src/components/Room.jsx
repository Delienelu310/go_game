import { useNavigate } from "react-router-dom";
import { useAuth } from "../security/AuthContext";
import { leaveRoom } from "../api/roomsApi";

export default function Room({room}){

    const {currentRoom, id, disconnect} = useAuth();

    const navigate = useNavigate();

    return (
        <div>
            {room ? 
                (
                    <div style={{
                        margin: "auto",
                        marginTop: "30px",
                        width: "50%",
                        border: "1px solid black",
                        borderRadius: "10px"
                    }}>
                        <h4>{room.roomDetails.title}</h4>
                        {room.admin && <div><b>Admin:</b> {room.admin.clientDetails.username}</div>}
                        <b>Patricipants:</b> {room.participants.length}
                        <div>{room.roomDetails.description}</div>
                        <button className="btn btn-primary m-2" onClick={ e => {
                            if(disconnect) disconnect();
                            if(currentRoom){ 
                                leaveRoom(currentRoom, id).then(response => {
                                    navigate(`/game/${room.id}`);
                                });
                            }else{
                                navigate(`/game/${room.id}`);
                            }
                        }}>Enter</button>
                    </div>
                )
                :
                (
                    <div>
                        Exception
                    </div>
                )
            }
        </div>
        
    );
}