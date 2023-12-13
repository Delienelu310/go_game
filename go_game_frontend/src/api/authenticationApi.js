
import apiClient from "./apiClient";

export function login(username, passsword){
    return apiClient.post("/login", {username, passsword});
}

export function register(username, passsword){
    return apiClient.post("/clients", {username, passsword});
}