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

export function addWhitePlayer(roomId, clientId){
    return apiClient.put(`/rooms/${roomId}/set/white/${clientId}`);
}

export function removeWhitePlayer(roomId){
    return apiClient.put(`/rooms/${roomId}/remove/white`);
}

export function addBlackPlayer(roomId, clientId){
    return apiClient.put(`/rooms/${roomId}/set/black/${clientId}`);
}

export function removeBlackPlayer(roomId){
    return apiClient.put(`/rooms/${roomId}/remove/black`);
}

export function startGame(roomId){
    return apiClient.put(`/rooms/${roomId}/start`);
}