
export type ApiClient = ReturnType<typeof createApiClient>;
//creates apiclient, an instance with all the methods to speaks with the API
export function createApiClient(baseUrl: string) {
  console.log(baseUrl)
  return {
    get: async (path: string) => fetch(
      `${baseUrl}${path}`, {
        credentials: "include",
        method: "GET",
      }),
    post: async (path: string, body: any) =>
      fetch(`${baseUrl}${path}`, {
        headers: { 'Content-Type': 'application/json' },
        credentials: "include",
        method: "POST",
        body: JSON.stringify(body)
      })
  };
}