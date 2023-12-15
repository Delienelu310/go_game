export default function Room({room}){
    return (
        <div>
            {room ? 
                (
                    <div>
                        <h4>{room.roomDetails.title}</h4>
                        {room.admin && <div>Admin: {room.admin.clientDetails.username}</div>}
                        Patricipants: {room.participants.length}
                        <div>{room.roomDetails.description}</div>
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