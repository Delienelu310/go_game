import apiClient from "./apiClient";

export function retrieveRoomsList(){
    return apiClient.get("/rooms");
}

export function retrieveRoom(id){
    return apiClient.get(`/rooms/${id}`);
}

export function createRoom(clientId, title, description, size){
    return apiClient.post(`/clients/${clientId}/rooms?size=${size}`, {
        title: title,
        description: description,
    });
}

export function enterRoom(roomId, clientId){
    return apiClient.put(`/rooms/${roomId}/add/${clientId}`);
}

export function leaveRoom(roomId, clientId){
    return apiClient.put(`/rooms/${roomId}/remove/${clientId}`);
}
