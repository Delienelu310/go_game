import { useState } from "react";
import { createRoom, leaveRoom } from "../api/roomsApi";
import { useAuth } from "../security/AuthContext";
import { useNavigate } from "react-router-dom";

export default function CreateGamePage(){
    
    const {id, disconnect, currentRoom} = useAuth();

    const [showException, setException] = useState(false);

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [size, setSize] = useState(9);

    const navigate = useNavigate();

    function addRoom(){
        createRoom(id, title, description, size)
            .then(response => {
                console.log(response);
                if(disconnect) disconnect();
                if(currentRoom){
                    leaveRoom(currentRoom, id)
                        .then(resp => {
                            navigate(`/game/${response.data.id}`);
                        });
                }else{
                    navigate(`/game/${response.data.id}`);
                }
            })
            .catch(e => {
                console.log(e);
                setException(true);
            });
    }

    return (
        <div>
            {showException && <div>Exception</div>}
            Title:
            <input className="form-control" style={{width: "50%", margin: "10px auto"}} value={title} onChange={e => setTitle(e.target.value)}/>

            <br/>
            Description:
            <input className="form-control" style={{width: "50%", margin: "10px auto"}} value={description} onChange={e => setDescription(e.target.value)}/>
            <br/>
            
            Field size:
            <input className="form-control" style={{width: "50%", margin: "10px auto"}} type="number" value={size} onChange={e => setSize(e.target.value)}/>
            <br/>

            <button className="btn btn-success" onClick={addRoom}>Create game</button>
        </div>
    );
}