// web/api/client.ts
import { createApiClient } from "../../../shared/src/apiClient";
import { APIURL } from "../config/env";

export const API = createApiClient(APIURL);