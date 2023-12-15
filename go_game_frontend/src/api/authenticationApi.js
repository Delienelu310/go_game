
import apiClient from "./apiClient";

export function login(username, password){
    return apiClient.post("/login", {username, password});
}

export function register(username, password){
    return apiClient.post("/clients", {username, password});
}