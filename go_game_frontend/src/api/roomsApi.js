import apiClient from "./apiClient";

export function retrieveRoomsList(){
    apiClient.get("/rooms");
}