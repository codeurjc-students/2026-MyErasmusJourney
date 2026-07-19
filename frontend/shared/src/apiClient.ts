
export type ApiClient = ReturnType<typeof createApiClient>;
//creates apiclient, an instance with all the methods to speaks with the API
export function createApiClient(baseUrl: string) {
  console.log(baseUrl)
  return {
    get: async (path: string) => fetch(`${baseUrl}${path}`),
    post: async (path: string, body: any) =>
      fetch(`${baseUrl}${path}`, {
        headers: { 'Content-Type': 'application/json' },
        method: "POST",
        body: JSON.stringify(body)
      })
  };
}