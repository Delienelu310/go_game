import apiClient from "./apiClient";

export function retrieveGamesByUser(userId){
    return apiClient.get(`/client/${userId}/games`);
}

export function retrieveGameById(gameId){
    return apiClient.get(`/games/${gameId}`);
}

export function recreateGame(gameId, moveNumber){
    return apiClient.get(`/games/${gameId}/move/${moveNumber}`);
}