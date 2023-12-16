import { useNavigate } from "react-router-dom";

export default function Room({room}){

    const navigate = useNavigate();

    return (
        <div>
            {room ? 
                (
                    <div>
                        <h4>{room.roomDetails.title}</h4>
                        {room.admin && <div>Admin: {room.admin.clientDetails.username}</div>}
                        Patricipants: {room.participants.length}
                        <div>{room.roomDetails.description}</div>
                        <button className="btn btn-success m-2" onClick={ e => {
                            navigate(`/game/${room.id}`);
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