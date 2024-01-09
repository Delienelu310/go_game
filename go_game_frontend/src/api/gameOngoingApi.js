import apiClient from "./apiClient";


export function setPlayer(gameId, clientId, position){
    return apiClient.put(`/games/${gameId}/set/${clientId}/${position}`);
}

export function setPlayersCount(gameId, count){
    return apiClient.put(`/games/${gameId}/set/players_count/${count}`);
}

export function startGame(gameId){
    return apiClient.put(`/games/${gameId}/start`);
}

export function makeMove(gameId, move){
    return apiClient.put(`/games/${gameId}`, move);
}