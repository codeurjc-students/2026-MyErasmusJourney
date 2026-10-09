export type ApiClient = ReturnType<typeof createApiClient>;

/**
 * Creates an API client: an object with one method per HTTP verb used to talk
 * to the REST API. Every request sends cookies (credentials: "include") so the
 * session is kept.
 *
 * @param baseUrl base URL of the API, prepended to every path
 */
export function createApiClient(baseUrl: string) {
  return {
    /** GET request to `path`. */
    get: async (path: string) => fetch(
      `${baseUrl}${path}`, {
      credentials: "include",
      method: "GET",
    }),

    /**
     * POST request to `path`.
     * - FormData: sent as is, without a Content-Type header so the browser
     *   sets multipart/form-data together with its boundary.
     * - Anything else: serialized as JSON.
     */
    post: async (path: string, data: any) => {
      const isFormData = data instanceof FormData;

      return fetch(`${baseUrl}${path}`, {
        method: "POST",
        credentials: "include",
        headers: isFormData ? {} : { "Content-Type": "application/json" },
        body: isFormData ? data : JSON.stringify(data),
      });
    },

    /** PUT request to `path` with `body` serialized as JSON. */
    put: async (path: string, body: any) =>
      fetch(`${baseUrl}${path}`, {
        headers: { 'Content-Type': 'application/json' },
        credentials: "include",
        method: "PUT",
        body: JSON.stringify(body)
      }),

    /** DELETE request to `path`. */
    delete: async (path: string) =>
      fetch(`${baseUrl}${path}`, {
        credentials: "include",
        method: "DELETE"
      })
  };
}
