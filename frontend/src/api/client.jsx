import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1",
    headers: {
        "X-API-KEY": "test-api-key-123",
    },
});

export default api;