import apiClient from "./apiClient";


export function setPlayer(gameId, clientId, position){
    return apiClient.put(`/games/${gameId}/set/player/${clientId}/${position}`);
}

export function setBot(gameId, type, position){
    return apiClient.put(`/games/${gameId}/set/player/${type}/${position}`);
}

export function setPlayersCount(gameId, count){
    return apiClient.put(`/games/${gameId}/set/players_count/${count}`);
}

export function startGame(gameId){
    return apiClient.put(`/games/${gameId}/start`);
}

export function makeMove(gameId, move){
    return apiClient.put(`/games/${gameId}/add/move`, move);
}

export function toggleDeadStoneGroup(gameId, move){
    return apiClient.put(`/games/${gameId}/toggle/stonegroup`, move);
}

export function toggleAgreedToFinalize(gameId, clientId){
    return apiClient.put(`/games/${gameId}/toggle/agreed_to_finalize/${clientId}`);
}

export function resumeGame(gameId, clientId){
    return apiClient.put(`/games/${gameId}/resume_game/${clientId}`);

}