import { ApiClient } from "../api/apiClient";
import { ApiError } from "../api/apiError";

export type BasicService = ReturnType<typeof createBasicService>;


export function createBasicService(api:ApiClient){
    return{
        getMethod: <T>(endpoint: string): Promise<T> =>api.get(endpoint).then(handleResponse<T>),
        postMethod: <T,B = unknown>(endpoint: string, body:B): Promise<T> =>api.post(endpoint, body).then(handleResponse<T>),
        putMethod: <T,B = unknown>(endpoint: string, body:B): Promise<T> =>api.put(endpoint, body).then(handleResponse<T>),
        deleteMethod: <T>(endpoint: string): Promise<T> =>api.delete(endpoint).then(handleResponse<T>)
    }
}

async function handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
        throw new ApiError(
            response.status,
            await response.text()
        );
    }

    if (response.status === 204) {
        return undefined as T;
    }

    return response.json() as Promise<T>;
}

